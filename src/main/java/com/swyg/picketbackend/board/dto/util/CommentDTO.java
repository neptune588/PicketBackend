package com.swyg.picketbackend.board.dto.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentDTO { // 버킷의 댓글 및 댓글을 단 회원의 닉네임을 가져오는 dto

    private Long id;

    private String nickname;

    private String content;

    private LocalDateTime createDate;


}
