package com.swyg.picketbackend.board.dto.res;

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
public class GetBoardResponseDTO {

    private Long boardId;  // 게시글 번호

    private String title; // 게시글 제목

    private String content; // 게시글 내용

    private String nickname; // 작성자 닉네임

    private LocalDate deadline; // 마감 기한

    private String filename; // 파일 이름

    private String filepath; // 파일 경로

    private Long heartCount; // 좋아요 개수
    
    private Long scrapCount; // 스크랩 개수

    private List<?> commentList; // 게시글 댓글 리스트


}
