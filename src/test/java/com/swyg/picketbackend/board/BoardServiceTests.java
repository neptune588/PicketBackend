package com.swyg.picketbackend.board;


import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.Entity.BoardCategory;
import com.swyg.picketbackend.board.Entity.Category;
import com.swyg.picketbackend.board.dto.req.PostBoardRequestDTO;
import com.swyg.picketbackend.board.repository.BoardCategoryRepository;
import com.swyg.picketbackend.board.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardCategoryRepository boardCategoryRepository;


    @Test
    @DisplayName("첨부파일 없는 버킷 등록 테스트")
    public void addBoardTests() {
        // given
        Member member = Member.setId(1L); // 회원번호 set

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1L));
        categoryList.add(new Category(2L));

        // dto -> Entity
        Board board = Board.builder()
                .member(member)
                .title("버킷 등록 제목 테스트")
                .content("버킷 등록 내용")
                .deadline(LocalDate.of(2023, 12, 30))
                .scrap(0L)   // scrap default :0
                .heart(0L)  // heart default :0
                .build();

        Board saveBoard = boardRepository.save(board);

        for (Category category : categoryList) {
            BoardCategory boardCategory = new BoardCategory(saveBoard, category);
            boardCategoryRepository.save(boardCategory);
        }
        // then

    }


}
