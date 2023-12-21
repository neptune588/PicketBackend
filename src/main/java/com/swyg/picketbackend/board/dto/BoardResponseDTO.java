package com.swyg.picketbackend.board.dto;

import com.swyg.picketbackend.board.Entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDTO {

    private Long id;  // 게시글 번호

    private String title; // 게시글 제목

    private String nickname; // 작성자 닉네임

    private String content; // 게시글 내용

    private String deadline; // 마감 기한

    private Long heart; // 좋아요 수

    private Long scrap; // 스크랩 수

    private String filename; // 파일 이름

    private String filepath; // 파일 경로


    // entity -> dto
    public static BoardResponseDTO from(Board board){
        return BoardResponseDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .nickname(board.getMember().getNickname())
                .deadline(board.getDeadline())
                .heart(board.getHeart())
                .scrap(board.getScrap())
                .filename(board.getFilename())
                .filepath(board.getFilepath())
                .build();
    }

    // entityList -> dtoList
    public static List<BoardResponseDTO> toDTOList(List<Board> boardList){
        return boardList.stream()
                .map(BoardResponseDTO::from)
                .collect(Collectors.toList());
    }

}