package com.swyg.picketbackend.board;


import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.Entity.QBoard;
import com.swyg.picketbackend.board.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Log4j2
@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private BoardRepository boardRepository;

    private Board board;

    @Test
    @DisplayName("키워드 검색 결과 게시글 목록 출력 테스트")
    public void findKeywordListTests() {

        // given
        QBoard board = QBoard.board;
        String searchKeyword = "querydsl test";

        // when
        JPAQuery<Board> query = jpaQueryFactory
                .selectFrom(board)
                .where(board.title.eq(searchKeyword)
                        .or(board.content.eq(searchKeyword)))
                .orderBy(board.createDate.desc());

        List<Board> searchBoardList = query.fetch();

        // then
        log.info("boardList: {}",searchBoardList);

        for(Board result : searchBoardList){
            log.info("Board ID: {}, Title: {}, Content: {}", result.getId(), result.getTitle(), result.getContent());
        }
    }
}
