package com.swyg.picketbackend.board.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.Entity.QBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@RequiredArgsConstructor
@Repository
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Board> findByKeywordList(String searchKeyword) {
        QBoard board = QBoard.board;

        return jpaQueryFactory.selectFrom(board)
                .where(board.title.eq(searchKeyword)
                        .or(board.content.eq(searchKeyword)))
                .fetch();
    }
}
