package com.swyg.picketbackend.board.repository.querydsl.board;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyg.picketbackend.auth.domain.QMember;
import com.swyg.picketbackend.board.Entity.*;
import com.swyg.picketbackend.board.dto.req.board.GetBoardListRequestDTO;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Board findBoardWithDetails(Long boardId) {
        QBoard boardDetails = QBoard.board;

        EntityGraph<?> entityGraph = entityManager.createEntityGraph(Board.class);
        entityGraph.addSubgraph("commentList");
        entityGraph.addSubgraph("boardCategoryList");
        entityGraph.addSubgraph("heart");
        entityGraph.addSubgraph("scrap");
        entityGraph.addSubgraph("member");

        return jpaQueryFactory
                .selectFrom(boardDetails)
                .where(boardDetails.id.eq(boardId))
                .fetchOne();
    }

    @Override
    public Slice<Board> boardSearchList(String keyword, List<Long> categoryList, Pageable pageable) {
        QBoard board = QBoard.board;
        QBoardCategory boardCategory = QBoardCategory.boardCategory;
        QCategory category = QCategory.category;
        QMember member = QMember.member;
        QScrap scrap = QScrap.scrap;
        QHeart heart = QHeart.heart;

        EntityGraph<?> entityGraph = entityManager.createEntityGraph(Board.class);
        entityGraph.addSubgraph("commentList");
        entityGraph.addSubgraph("boardCategoryList");
        entityGraph.addSubgraph("heart");
        entityGraph.addSubgraph("scrap");
        entityGraph.addSubgraph("member");

        // 카테고리별 Board ID 조회
        List<Long> boardIds = jpaQueryFactory
                .select(boardCategory.board.id)
                .from(boardCategory)
                .where(boardCategory.category.id.in(categoryList))
                .distinct()
                .fetch();


        // 게시글 검색 조건
        BooleanExpression searchCondition;
        if (StringUtils.hasText(keyword)) {
            searchCondition = board.id.in(boardIds)
                    .and(board.title.containsIgnoreCase(keyword)
                            .or(board.content.containsIgnoreCase(keyword)));
        } else {
            // 키워드가 주어지지 않은 경우 모든 게시글을 대상으로 함
            searchCondition = board.id.in(boardIds);
        }


        // 페이징 및 무한 스크롤
        List<Board> boardList = jpaQueryFactory
                .selectFrom(board)
                .where(searchCondition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // 1개 더 가져와서 hasNext 여부 확인
                .fetch();

        boolean hasNext = boardList.size() > pageable.getPageSize();

        if (hasNext) {
            boardList.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(boardList, pageable, hasNext);
    }


}

