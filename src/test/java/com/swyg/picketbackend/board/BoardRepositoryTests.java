package com.swyg.picketbackend.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyg.picketbackend.board.Entity.*;

import com.swyg.picketbackend.board.repository.BoardRepository;
import com.swyg.picketbackend.board.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


import static org.assertj.core.api.Assertions.assertThat;


@Log4j2
@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("게시글 상세 조회 테스트")
    @Transactional
    @Rollback
    void findBoardWithDetails() {
        // given
        // 게시글이 존재한다고 가정하고 ID를 지정
        Long boardId = 1L;

        // when
        Board board = boardRepository.findBoardWithDetails(boardId);

        // then
        assertThat(board).isNotNull();
        log.info("Board ID: {}", board.getId());
        assertThat(board.getCommentList()).isNotEmpty();
        for (Comment comment : board.getCommentList()) {
            log.info("Comment List: {}", comment.toString());
        }
        assertThat(board.getBoardCategoryList()).isNotEmpty();
        for (BoardCategory boardCategory : board.getBoardCategoryList()) {
            log.info("Board Category List: {}", boardCategory.toString());
        }
        assertThat(board.getHeart()).isNotEmpty();
        for (Heart heart : board.getHeart()) {
            log.info("Heart List: {}", heart.toString());
        }
        assertThat(board.getScrap()).isNotEmpty();
        for (Scrap scrap : board.getScrap()) {
            log.info("Scrap List: {}", scrap.toString());
        }

    }




}
