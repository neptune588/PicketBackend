package com.swyg.picketbackend.board.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.swyg.picketbackend.auth.util.SecurityUtil;
import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.board.Entity.BoardCategory;
import com.swyg.picketbackend.board.Entity.Category;
import com.swyg.picketbackend.board.dto.req.board.GetBoardListRequestDTO;
import com.swyg.picketbackend.board.dto.req.board.PostBoardRequestDTO;
import com.swyg.picketbackend.board.dto.res.board.GetBoardDetailsResponseDTO;
import com.swyg.picketbackend.board.dto.res.board.GetBoardListResponseDTO;
import com.swyg.picketbackend.board.dto.res.board.GetMyBoardListResponseDTO;
import com.swyg.picketbackend.board.dto.req.board.PatchBoardRequestDTO;
import com.swyg.picketbackend.board.repository.BoardCategoryRepository;
import com.swyg.picketbackend.board.repository.BoardRepository;
import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardCategoryRepository boardCategoryRepository;

    private final AmazonS3Client amazonS3Client;

    private final S3Service s3Service;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 나의 버킷 리스트 조회
    @Transactional
    public List<GetMyBoardListResponseDTO> findMyBoardList() { // 나의 버킷리스트 조회

        Long currentMemberId = SecurityUtil.getCurrentMemberId(); // 현재 로그인한 회원 ID

        List<Board> myBoardList = boardRepository.findAllByMemberId(currentMemberId);

        return GetMyBoardListResponseDTO.toDTOList(myBoardList);
    }

    @Transactional
    public Slice<GetBoardListResponseDTO> searchBoardList(String keyword, List<Long> categoryList, Pageable pageable) {
        Slice<Board> resultList = boardRepository.boardSearchList(keyword, categoryList, pageable);
        return GetBoardListResponseDTO.toDTOList(resultList);
    }


    @Transactional
    public GetBoardDetailsResponseDTO detailBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        Board target = boardRepository.findBoardWithDetails(boardId);

        return GetBoardDetailsResponseDTO.toDTOList(target);

    }

    //  첨부파일 있을 시 게시글 작성 서비스
    @Transactional
    public void addBoardWithFile(PostBoardRequestDTO postBoardRequestDTO, MultipartFile file) throws IOException {

        Long currentMemberId = SecurityUtil.getCurrentMemberId(); // 현재 로그인한 회원 ID

        Member member = Member.setId(currentMemberId); // 회원번호 set

        List<Category> categoryList = postBoardRequestDTO.toCategoryList(); // 게시물 소속 카테고리 ID 수집


        UUID uuid = UUID.randomUUID();


        String filename = uuid + "_" + file.getOriginalFilename();

        String fileUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + filename;

        // dto -> Entity
        Board board = Board.toEntity(postBoardRequestDTO, member, filename, fileUrl);

        Board boardEntity = boardRepository.save(board);

        for (Category category : categoryList) {
            BoardCategory boardCategory = new BoardCategory(boardEntity, category);
            boardCategoryRepository.save(boardCategory);
        }

        // Amazon S3 파일 저장
        s3Service.uploadFile(file, filename);

    }

    //  첨부파일 없을 시 게시글 작성 서비스
    public void addBoard(PostBoardRequestDTO postBoardRequestDTO) {

        Long currentMemberId = SecurityUtil.getCurrentMemberId(); // 현재 로그인한 회원 ID

        Member member = Member.setId(currentMemberId); // 회원번호 set

        List<Category> categoryList = postBoardRequestDTO.toCategoryList(); // 게시물 소속 카테고리 ID 수집

        // dto -> Entity
        Board board = Board.toEntity(postBoardRequestDTO, member, null, null);

        Board boardEntity = boardRepository.save(board);

        for (Category category : categoryList) {
            BoardCategory boardCategory = new BoardCategory(boardEntity, category);
            boardCategoryRepository.save(boardCategory);
        }
    }

    // 첨부파일이 있는 버킷 수정
    @Transactional
    public void modifyBoardWithFile(Long boardId,
                                    @RequestPart PatchBoardRequestDTO patchBoardRequestDTO,
                                    @RequestPart MultipartFile file) throws IOException {

        // 1. 대상 찾기
        Board target = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        // 2. 버킷 작성 회원 인증 확인
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Long targetMemberId = target.getMember().getId();

        log.info("targetMemberId {}", targetMemberId);

        if (!currentMemberId.equals(targetMemberId)) { // 로그인한 회원의 작성 버킷인지 확인
            throw new CustomException(ErrorCode.UNAUTHORIZED_BOARD_UPDATE);
        }

        String currentFileName = target.getFilename(); // 현재 파일 이름

        String currentFileUrl = target.getFilepath(); // 현재 파일 경로


        // 파일을 수정하지 않는 경우(기존 파일일 경우)
        if (s3Service.areS3AndLocalFilesEqual(currentFileName, file)) {
            log.info("기존 파일일 경우 버킷 수정...");
            target.updateBoard(patchBoardRequestDTO, currentFileName, currentFileUrl);
            try {// dirty checking
                boardRepository.save(target);
            } catch (IllegalArgumentException e) {
                throw new CustomException(ErrorCode.UNAUTHORIZED_BOARD_DELETE);
            }
            return;
        }

        //  새로운 파일을 등록하는 경우
        log.info("새로운 파일일 경우 버킷 수정...");
        UUID uuid = UUID.randomUUID();

        String newFilename = uuid + "_" + file.getOriginalFilename(); // 새로운 파일 이름

        String newFileUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + newFilename; // 새로운 파일 url

        // 새로운 파일을 포함한 버킷 수정
        target.updateBoard(patchBoardRequestDTO, newFilename, newFileUrl);
        boardRepository.save(target); // dirty checking

        // Amazon S3 새로운 이미지 파일 저장
        s3Service.uploadFile(file, newFilename);

        // Amazon s3에서 기존 파일 삭제
        s3Service.deleteFile(currentFileName);
    }

    // 첨부파일이 없는 버킷 수정
    public void modifyBoard(Long boardId, PatchBoardRequestDTO patchBoardRequestDTO) {

        // 1. 대상 찾기
        Board target = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        // 2. 버킷 작성 회원 인증 확인
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Long targetMemberId = target.getMember().getId();

        if (!currentMemberId.equals(targetMemberId)) { // 로그인한 회원의 작성 버킷인지 확인
            throw new CustomException(ErrorCode.UNAUTHORIZED_BOARD_UPDATE);
        }

        target.updateBoard(patchBoardRequestDTO, null, null);

        try {// dirty checking
            boardRepository.save(target);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_BOARD_DELETE);
        }
    }

    // 버킷 삭제
    public void removeBoard(Long boardId) {
        //1. 대상 찾기
        Board target = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        // 2. 버킷 작성 회원 인증 확인
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Long targetMemberId = target.getMember().getId();

        String targetFileName = target.getFilename();

        if (!currentMemberId.equals(targetMemberId)) { // 로그인한 회원의 작성 버킷인지 확인
            throw new CustomException(ErrorCode.UNAUTHORIZED_BOARD_DELETE);
        }

        //3. 대상 삭제 후 정상응답
        try {
            boardRepository.delete(target);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_BOARD_DELETE);
        }

        // 4. Amazon s3 이미지 삭제
        s3Service.deleteFile(targetFileName);

    }


}
