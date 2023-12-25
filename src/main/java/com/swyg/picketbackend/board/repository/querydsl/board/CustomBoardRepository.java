package com.swyg.picketbackend.board.repository.querydsl.board;

import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.dto.req.board.GetBoardListRequestDTO;
import org.springframework.data.domain.Slice;

public interface CustomBoardRepository {

    Board findBoardWithDetails(Long boardId);

    Slice<Board> findByList(GetBoardListRequestDTO getBoardListRequestDTO);



}
