package com.swyg.picketbackend.board.service;

import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.Entity.Comment;
import com.swyg.picketbackend.board.dto.res.CommentResponseDTO;
import com.swyg.picketbackend.board.repository.BoardRepository;
import com.swyg.picketbackend.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    /*public List<CommentResponseDTO> findComments(Long boardId) {

        commentRepository.findAllByBoardId(boardId);



        return commentRepository.findByBoardId(boardId);
    }*/

    /*public Comment write(Long boardId, Comment comment) {

        //1. 게시글 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        //2. 댓글 엔티티 생성
        Comment writed = Comment.writeComment(comment, board);

        //3. 엔티티 DB에 저장 후 반환
        Comment result = commentRepository.save(writed);
        return result;
    }*/

    /*public Comment delete(Long id, String nickname) {
        //1. 대상 조회
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

        //2. 본인 확인
        if(target.getNickname() != nickname)
            return null;

        //3. 댓글 삭제
        commentRepository.delete(target);

        return target;
    }*/
}
