package com.swyg.picketbackend.board.repository.querydsl;

import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.dto.req.GetBoardListRequestDTO;
import org.springframework.data.domain.Slice;

public interface CustomBoardRepository {

    Slice<Board> findByList(GetBoardListRequestDTO getBoardListRequestDTO);
}
