package com.swyg.picketbackend.auth.domain;

import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.Entity.Comment;
import com.swyg.picketbackend.global.dto.BaseEntity;
import com.swyg.picketbackend.auth.util.Role;
import com.swyg.picketbackend.auth.util.SocialType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // 유저번호(PK)

    private String email; // 이메일

    private String password; // 비밀번호

    private String nickname; // 닉네임

    private String imageUrl; // 프로필 이미지

    private String providerId; // 소셜 로그인 구분 아이디

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true) // 회원이 삭제되면 삭제 회원이 작성한 게시물도 삭제
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true) // 회원이 삭제되면 삭제 회원이 작성한 댓글도 삭제
    private List<Comment> commentList = new ArrayList<>();


    @Builder
    public Member(String email, String password, Role role, String nickname, String imageUrl, SocialType socialType, String providerId
            , List<Board> boardList) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.socialType = socialType;
        this.providerId = providerId;
        this.boardList = boardList;
    }


    // 게시글 등록을 위한 회원 번호 set
    public static Member setId(Long id) {
        Member member = new Member();
        member.id = id;
        return member;
    }

    // 비밀번호 변경 메서드
    public void modifyPassword(String password) {
        this.password = password;
    }

    // 닉네임 설정 메서드
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
