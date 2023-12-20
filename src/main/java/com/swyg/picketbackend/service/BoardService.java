package com.swyg.picketbackend.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.swyg.picketbackend.Entity.Board;
import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.auth.repository.MemberRepository;
import com.swyg.picketbackend.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public List<Board> getMyBoardList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if(member == null) {
            return null; // 본인 게시물 비어있을 때 null 반환
        }
        String writer = member.getNickname();
        return boardRepository.findAllByWriter(writer);
    }
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }


    public List<Board> getSearchedBoardList(String searchKeyWord) {
        return boardRepository.findAllByTitleContainingOrContentContaining(searchKeyWord, searchKeyWord);
    }

    public Board getBoardDetail(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public Board writeWithFile(Board board, @RequestParam("file") MultipartFile file) throws IOException {
        if(board.getId() != null){
            return null;
        }

        UUID uuid = UUID.randomUUID();

        String filename = uuid + "_" + file.getOriginalFilename();

        String fileUrl= "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" +filename;

        board.setFilename(filename);
        board.setFilepath(fileUrl);

        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket,filename,file.getInputStream(),metadata);

        return boardRepository.save(board);
    }

    public Board write(Board board) {
        if(board.getId() != null){
            return null;
        }
        return boardRepository.save(board);
    }

    public Board update(Long id, Board board) {
        //1. 대상 찾기
        Board target = boardRepository.findById(id).orElse(null);

        //2. 잘못된 요청 처리
        if(target == null){
            log.info("잘못된 요청 id : {}, board :{}", id, board.toString());
            return null;
        }
        //3. 업데이트 및 정상응답
        target.patch(board);
        Board updated = boardRepository.save(target);
        return updated;


    }

    public Board delete(Long id) {
        //1. 대상 찾기
        Board target = boardRepository.findById(id).orElse(null);

        //2. 잘못된 요청 처리
        if(target == null){
            return null;
        }

        //3. 대상 삭제 후 정상응답
        boardRepository.delete(target);
        return target;
    }



}
