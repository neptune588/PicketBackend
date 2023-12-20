package com.swyg.picketbackend.repository;

import com.swyg.picketbackend.Entity.Board;
import com.swyg.picketbackend.Entity.Heart;
import com.swyg.picketbackend.auth.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    Heart findByMemberAndBoard(Member member, Board board);
}

