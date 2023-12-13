package com.swyg.picketbackend.user.domain;

import com.swyg.picketbackend.global.dto.BaseEntity;
import com.swyg.picketbackend.user.util.Role;
import com.swyg.picketbackend.user.util.SocialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "USERS")
@Builder
@AllArgsConstructor
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 유저번호(PK)

    private String email; // 이메일

    private String password; // 비밀번호

    private String nickname; // 닉네임

    private String imageUrl; // 프로필 이미지

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId; // 로그인한 소셜 타입의 식별자 값 일반 로그인의 경우 null

    private String refreshToken; // 리프레시 토큰


    public User() {

    }

    // 유저 권한 설정 메서드
    public void authorizeUser(){
        this.role= Role.USER;
    }
    
    // 리프레시 토큰 갱신 메서드
    public void updateRefreshToken(String updateRefreshToken){
        this.refreshToken = updateRefreshToken;
    }
    
    



}
