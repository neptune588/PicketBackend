package com.swyg.picketbackend.board.dto.req;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentRequestDTO {

    private String content; // 댓글 내용
}
