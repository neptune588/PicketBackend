package com.swyg.picketbackend.board.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.swyg.picketbackend.auth.util.SecurityUtil;
import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.auth.repository.MemberRepository;
import com.swyg.picketbackend.board.dto.BoardRequestDTO;
import com.swyg.picketbackend.board.dto.BoardResponseDTO;
import com.swyg.picketbackend.board.dto.PatchBoardDTO;
import com.swyg.picketbackend.board.repository.BoardRepository;
import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    @Transactional
    public List<BoardResponseDTO> getMyBoardList(Long memberId) { // 나의 버킷리스트 조회

        Long currentMemberId = SecurityUtil.getCurrentMemberId(); // 현재 인증 memberID

        if (!memberId.equals(currentMemberId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        List<Board> myBoardList = boardRepository.findAllByMemberId(currentMemberId);

        return BoardResponseDTO.toDTOList(myBoardList);
    }

    @Transactional
    public List<BoardResponseDTO> getBoardList() { // TODO : 모든 버킷리스트 조회(무한 스크롤 구현 필요)
        List<Board> boardList = boardRepository.findAll();
        return BoardResponseDTO.toDTOList(boardList);
    }

    @Transactional
    public List<BoardResponseDTO> getSearchedBoardList(String searchKeyWord) { // TODO : 검색 조건 버킷리스트 조회(무한 스크롤 구현 필요)
        List<Board> boardSearchList = boardRepository.findAllByTitleContainingOrContentContaining(searchKeyWord, searchKeyWord);
        return BoardResponseDTO.toDTOList(boardSearchList);
    }

    @Transactional
    public BoardResponseDTO getBoardDetail(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        return BoardResponseDTO.from(board); // entity -> dto
    }

    @Transactional
    public void writeWithFile(BoardRequestDTO boardRequestDTO, @RequestParam("file") MultipartFile file) throws IOException { //  첨부파일 있는 게시글 작성

        Long currentMemberId = SecurityUtil.getCurrentMemberId(); // 현재 로그인한 회원 ID

        Member member = Member.setId(currentMemberId); // 회원번호 set

        UUID uuid = UUID.randomUUID();

        String filename = uuid + "_" + file.getOriginalFilename();

        String fileUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + filename;

        // dto -> Entity
        Board board = Board.builder()
                .member(member)
                .title(boardRequestDTO.getTitle())
                .content(boardRequestDTO.getContent())
                .deadline(boardRequestDTO.getDeadline())
                .scrap(0L)   // scrap default :0
                .heart(0L)  // heart default :0
                .filename(filename)
                .filepath(fileUrl)
                .build();

        // Amazon S3 파일 저장
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, filename, file.getInputStream(), metadata);

        boardRepository.save(board);
    }

    @Transactional
    public void write(BoardRequestDTO boardRequestDTO) { //  첨부파일 없는 게시글 작성

        Long currentMemberId = SecurityUtil.getCurrentMemberId(); // 현재 로그인한 회원 ID

        Member member = Member.setId(currentMemberId); // 회원번호 set

        // dto -> Entity
        Board board = Board.builder()
                .member(member)
                .title(boardRequestDTO.getTitle())
                .content(boardRequestDTO.getContent())
                .deadline(boardRequestDTO.getDeadline())
                .scrap(0L)   // scrap default :0
                .heart(0L)  // heart default :0
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public BoardResponseDTO update(Long boardId, PatchBoardDTO patchBoardDTO) {
        // 1. 대상 찾기
        Board target = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        // 2. 버킷 작성 회원 인증 확인
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Long targetMemberId = target.getMember().getId();

        log.info("targetMemberId {}", targetMemberId);

        if (!currentMemberId.equals(targetMemberId)) { // 로그인한 회원의 작성 버킷인지 확인
            throw new CustomException(ErrorCode.UNAUTHORIZED_UPDATE);
        }

        target.update(patchBoardDTO.getTitle(),patchBoardDTO.getContent(),
                patchBoardDTO.getDeadline(),patchBoardDTO.getFilename(), patchBoardDTO.getFilepath()); // 버킷 수정 정보 set

        Board updateEntity =boardRepository.save(target); // dirty checking

        // entity -> dto
        return  BoardResponseDTO.from(updateEntity);
    }


    public void delete(Long boardId) {
        //1. 대상 찾기
        Board target = boardRepository.findById(boardId)
                .orElseThrow(()-> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        // 2. 버킷 작성 회원 인증 확인
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Long targetMemberId = target.getMember().getId();

        if (!currentMemberId.equals(targetMemberId)) { // 로그인한 회원의 작성 버킷인지 확인
            throw new CustomException(ErrorCode.UNAUTHORIZED_UPDATE);
        }
        //3. 대상 삭제 후 정상응답
        boardRepository.delete(target);

    }


}
