package com.swyg.picketbackend.board.repository.querydsl.board;



import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyg.picketbackend.board.Entity.*;
import com.swyg.picketbackend.board.dto.req.board.GetBoardListRequestDTO;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

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
    public Slice<Board> findByList(GetBoardListRequestDTO getBoardListRequestDTO) {
       /* QBoard board = QBoard.board;

        // parameter setting
        String keyword = boardListRequestDTO.getKeyword(); // 키워드
        List<String> categoryList = boardListRequestDTO.getCategoryList();  // 해당 카테고리 목록
        int page = boardListRequestDTO.getPage(); // 페이지 번호
        int size = boardListRequestDTO.getSize(); // 페이지 사이즈

        // 카테고리 조건
        BooleanExpression categoryExpression = board.categoryList.any().name.in(boardListRequestDTO.getCategoryList());

        // 검색어 조건
        BooleanExpression keywordExpression = board.title.containsIgnoreCase(boardListRequestDTO.getKeyword())
                .or(board.content.containsIgnoreCase(boardListRequestDTO.getKeyword()));

        // 카테고리 + 검색어 조건
        BooleanExpression condition = categoryExpression.and(keywordExpression);

        // 카테고리 선택 안했으면 카테고리 조건 무시
        if (categoryList == null || categoryList.isEmpty()) {
            condition = keywordExpression;
        }

        List<Board> boardList = jpaQueryFactory
                .selectFrom(board)
                .where(condition)
                .offset((long) page * size)
                .limit(size + 1) // 1개 더 가져와서 hasNext 여부 확인
                .fetch();

        boolean hasNext = boardList.size() > size;

        if(hasNext){
            boardList.remove(size);
        }

        return new SliceImpl<>(boardList, PageRequest.of(page,size),hasNext);*/
        return null;
    }


}

