package com.swyg.picketbackend.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String writer;

    private String content;

    private String deadline;

    private Long good;

    private Long scrap;

    private String filename;

    private String filepath;

    public void patch(Board board) {
        if(board.title != null)
            this.title = board.title;
        if(board.writer != null)
            this.writer = board.writer;
        if(board.content != null)
            this.content = board.content;
    }
}
