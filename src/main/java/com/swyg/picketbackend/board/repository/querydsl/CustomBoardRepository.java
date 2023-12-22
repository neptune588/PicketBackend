package com.swyg.picketbackend.board.repository.querydsl;

import com.swyg.picketbackend.board.Entity.Board;

import java.util.List;

public interface CustomBoardRepository {

    List<Board> findByKeywordList(String searchKeyword);
}
