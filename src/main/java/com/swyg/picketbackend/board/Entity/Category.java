package com.swyg.picketbackend.board.Entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;    // 카테고리 ID
    
    private String name; // 카테고리 이름


    @ManyToMany
    @JoinTable(
            name = "board_category", // 중간 테이블명
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "board_id")
    )
    private List<Board> boardList = new ArrayList<>();


}
