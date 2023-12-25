package com.swyg.picketbackend.board.repository.querydsl.commnet;

import com.swyg.picketbackend.board.dto.util.CommentNicknameDTO;

import java.util.List;

public interface CustomCommentRepository {

    List<CommentNicknameDTO> findCommentsNickname(Long boardId);
}
