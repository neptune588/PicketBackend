package com.swyg.picketbackend.repository;

import com.swyg.picketbackend.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT * FROM board WHERE writer = :writer", nativeQuery = true)
    List<Board> findAllByWriter(@Param("writer") String writer);

    @Transactional // 일련의 작업 하나로 묶어 처리
    @Modifying // 영속성 가지지 않고 바로 DB에 저장
    @Query(value = "UPDATE board SET good = good + 1 where board_id = :boardId", nativeQuery = true)
    void addGood(@Param("boardId") Long boardId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE board SET good = good - 1 where board_id = :boardId", nativeQuery = true)
    void subGood(Long boardId);

    List<Board> findAllByTitleContainingOrContentContaining(String title, String content);
}