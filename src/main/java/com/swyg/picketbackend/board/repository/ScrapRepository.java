package com.swyg.picketbackend.board.repository;


import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.Entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {


    Optional<Scrap>  findByBoardAndMemberId(Board board,Long boardId);
}
