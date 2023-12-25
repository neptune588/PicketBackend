package com.swyg.picketbackend.board.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardDetailsResponseDTO {

    @Schema(description = "게시글 번호", example = "1")
    private Long boardId;  // 게시글 번호

    @Schema(description = "버킷 제목", example = "버킷 제목")
    private String title; // 게시글 제목

    @Schema(description = "버킷 내용", example = "버킷 내용")
    private String content; // 게시글 내용

    @Schema(description = "버킷 작성자 닉네임", example = "닉네임")
    private String nickname; // 작성자 닉네임


    @Schema(description = "마감기한", example = "2023-12-30")
    private LocalDate deadline; // 마감 기한

    @Schema(description = "버킷 이미지 파일", example = "Amazon s3에 저장된 이미지 주소(https://swyg-picket.s3.ap-northeast-2.amazonaws.com" +
            "/87702a0e-299b-473a-b4a1-82663819c9d7_%EC%8A%A4%ED%94%84%EB%A7%81%EC%A7%84%EC%8A%A4.png)")
    private String filepath; // 파일 경로

    @Schema(description = "버킷 좋아요 개수", example = "5")
    private Long heartCount; // 좋아요 개수

    @Schema(description = "버킷 스크랩 개수", example = "10")
    private Long scrapCount; // 스크랩 개수

    @Schema(description = "버킷 댓글 목록", example = "댓글 목록")
    private List<?> commentList; // 게시글 댓글 리스트



}
