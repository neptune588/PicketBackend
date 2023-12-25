package com.swyg.picketbackend.board.service;

import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.auth.util.SecurityUtil;
import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.Entity.Heart;
import com.swyg.picketbackend.board.dto.res.heart.PostLikeResponseDTO;
import com.swyg.picketbackend.board.repository.BoardRepository;
import com.swyg.picketbackend.board.repository.HeartRepository;
import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;

    private final BoardRepository boardRepository;

    @Transactional
    public boolean toggleHeart(Long boardId, Long currentLoginId) {
        // 1. 좋아요 누를 버킷 확인
        Board target = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        Member member = Member.setId(currentLoginId);  // 회원번호 set

        Heart existingHeart = heartRepository.findByMemberAndBoard(member,target);

        if (existingHeart != null) {
            heartRepository.delete(existingHeart);
            return false; // 하트 제거
        } else {
            Heart newHeart = Heart.addlike(member,target);
            heartRepository.save(newHeart);
            return true; // 하트 추가
        }

    }



}
