package com.swyg.picketbackend.board.Entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;    // 카테고리 ID

    private String name; // 카테고리 이름


    @OneToMany(mappedBy = "category")
    private List<BoardCategory> boardCategoryList = new ArrayList<>();

    // 카테고리 setting 생성자
    public Category(Long categoryId) {
        this.id = categoryId;
    }

}
