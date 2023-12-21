package com.swyg.picketbackend.board.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDTO { // 게시글 작성 DTO

    @Schema(description = "게시글 제목",example = "title")
    private String title; // 게시글 제목

    @Schema(description = "게시글 내용",example = "content")
    private String content; // 게시글 내용

    @Schema(description = "로그인 종료날짜",example = "deadline")
    private String deadline; // 종료 날짜

    @Schema(description = "좋아요 수",example = "0")
    private Long heart; // 좋아요 수

    @Schema(description = "스크랩 수",example = "0")
    private Long scrap; // 스크랩 수

    @Schema(description = "",example = "filename")
    private String filename; // 파일 이름

    @Schema(description = "로그인 이메일",example = "filepath")
    private String filepath; // 파일 경로

}
