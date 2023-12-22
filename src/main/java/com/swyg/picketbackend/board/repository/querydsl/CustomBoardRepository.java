package com.swyg.picketbackend.board.repository.querydsl;

import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.dto.req.BoardListRequestDTO;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CustomBoardRepository {

    Slice<Board> findByList(BoardListRequestDTO boardListRequestDTO);
}
