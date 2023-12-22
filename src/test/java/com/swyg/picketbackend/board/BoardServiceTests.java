package com.swyg.picketbackend.board;


import com.swyg.picketbackend.board.dto.res.BoardResponseDTO;
import com.swyg.picketbackend.board.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Log4j2
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;


    /*@Test
    public void getSearchedBoardListTests() {

        // given
        String searchKeyword = "querydsl tes";

        // when
        List<BoardResponseDTO> resultList = boardService.getSearchedBoardList(searchKeyword);

        // then
        for(BoardResponseDTO boardResponseDTO:resultList){
            log.info(boardResponseDTO.toString());
        }
    }*/


}
