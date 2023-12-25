package com.swyg.picketbackend.board.service;

import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.auth.util.SecurityUtil;
import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.Entity.Comment;
import com.swyg.picketbackend.board.dto.req.comment.PostCommentRequestDTO;
import com.swyg.picketbackend.board.repository.BoardRepository;
import com.swyg.picketbackend.board.repository.CommentRepository;
import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;


    // 버킷 댓글 등록
    public void addComment(Long boardId, PostCommentRequestDTO postCommentRequestDTO) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();

        if(currentMemberId==null || currentMemberId.equals(0L)){
            throw new CustomException(ErrorCode.UNAUTHORIZED_NEED_LOGIN);
        }


        Member member = Member.setId(currentMemberId); // 작성 회원 번호 set
        Board board = Board.setId(boardId); // 버킷 번호 set

        boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND)); // 게시물 존재하는 지 확인

        Comment comment = Comment.toComment(member, board, postCommentRequestDTO.getContent()); // dto -> entity

        commentRepository.save(comment);
    }

    // 버킷 댓글 삭제
    public void removeComment(Long boardId, Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();

        Board findParent = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        Comment target = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (target.getMember().getId().equals(currentMemberId)) { // 로그인한 회원이 쓴 댓글인지 확인
            commentRepository.delete(target);
        } else {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REPLY_DELETE);
        }
    }

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
