package com.swyg.picketbackend.board.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String nickname;

    private String comment;

    public static Comment writeComment(Comment comment, Board board) {
        //예외 처리
        if(comment.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야합니다.");

        return new Comment(
                null,
                board,
                comment.getNickname(),
                comment.getComment()
        );
    }
}
