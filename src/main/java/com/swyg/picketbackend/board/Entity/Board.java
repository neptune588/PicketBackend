package com.swyg.picketbackend.board.Entity;

import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.global.dto.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {  // 생성날짜,수정날짜 자동 생성

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;  // 게시글 번호

    private String title; // 게시글 제목
    
    private String content; // 게시글 내용

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline; // 종료 날짜

    private Long heart; // 좋아요 수

    private Long scrap; // 스크랩 수

    private String filename; // 파일 이름

    private String filepath; // 파일 경로

    @ManyToOne(optional = false) // member_id가 반드시 존재해야 함
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @ManyToMany(mappedBy = "boardList")
    private List<Category> categoryList = new ArrayList<>();




    // dto -> entity
    @Builder
    public Board(Member member,String title,String content,LocalDate deadline,Long heart,Long scrap,String filename,String filepath){
        this.member = member;
        this.title = title;
        this.content = content;
        this.heart =heart;
        this.scrap = scrap;
        this.deadline = deadline;
        this.filename = filename;
        this.filepath = filepath;
    }
    
    
    // 버킷 업데이트 메서드
    public void update(String title,String content,LocalDate deadline,String filename,String filepath){
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.filepath = filepath;
        this.filename = filename;
    }
}
