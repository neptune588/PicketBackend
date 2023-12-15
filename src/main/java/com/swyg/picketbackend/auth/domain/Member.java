package com.swyg.picketbackend.auth.domain;

import com.swyg.picketbackend.global.dto.BaseEntity;
import com.swyg.picketbackend.auth.dto.Role;
import com.swyg.picketbackend.auth.dto.SocialType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;


    @Builder
    public Member(String email, String password, Role role,String nickname,String imageUrl,SocialType socialType) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.nickname =nickname;
        this.imageUrl = imageUrl;
        this.socialType = socialType;
    }


}
