package com.swyg.picketbackend.auth.repository;

import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.auth.dto.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    Member findByEmailAndProviderId(String email, String providerId); // 이메일&소셜 타입으로 기존 로그인 소셜 회원인지 확인

}

