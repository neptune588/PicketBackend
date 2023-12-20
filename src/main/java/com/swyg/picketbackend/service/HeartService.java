package com.swyg.picketbackend.service;

import com.swyg.picketbackend.Entity.Board;
import com.swyg.picketbackend.Entity.Heart;
import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.repository.BoardRepository;
import com.swyg.picketbackend.repository.HeartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeartService {

    @Autowired
    private HeartRepository heartRepository;
    @Autowired
    private BoardRepository boardRepository;

    public Heart clickLike(Long boardId, Member member) {
        //1. 게시글 찾기
        Board board = boardRepository.findById(boardId).orElse(null);

        //2. 좋아요를 눌렀는지 여부 검사
        Heart heart = heartRepository.findByMemberAndBoard(member, board);

        if(heart == null) {
            boardRepository.addGood(boardId); // 좋아요 + 1

            heart = heartRepository.save(Heart.builder().member(member).board(board).build()); // heart 테이블에 행 추가

            return heart;
        }
        else {
            boardRepository.subGood(boardId); // 좋아요 - 1

            heartRepository.delete(heart); // heart 테이블에서 행 삭제

            return heart;
        }

    }
}