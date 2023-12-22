package com.swyg.picketbackend.board.dto.req;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDTO { // 게시글 작성 DTO

    @Schema(description = "버킷 제목",example = "title")
    private String title; // 게시글 제목

    @Schema(description = "버킷 내용",example = "content")
    private String content; // 게시글 내용

    @Schema(description = "버킷 종료일",example = "2023-12-30")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone = "Asia/Seoul")
    private LocalDate deadline; // 종료 날짜

    @Schema(description = "좋아요 수",example = "0")
    private Long heart; // 좋아요 수

    @Schema(description = "스크랩 수",example = "0")
    private Long scrap; // 스크랩 수

    @Schema(description = "버킷이 속한 카테고리 목록", example = "[\"category1\", \"category2\"]")
    private List<String> categories; // 게시글이 속한 카테고리 목록
}
