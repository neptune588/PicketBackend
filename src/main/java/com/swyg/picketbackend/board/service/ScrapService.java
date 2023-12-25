package com.swyg.picketbackend.board.service;


import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.board.Entity.Board;
import com.swyg.picketbackend.board.Entity.Scrap;
import com.swyg.picketbackend.board.repository.BoardRepository;
import com.swyg.picketbackend.board.repository.ScrapRepository;
import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;

    private final BoardRepository boardRepository;

    public boolean toggleScrap(Long boardId, Long currentLoginId) {
        Board target = boardRepository.findById(boardId)
                .orElseThrow(()-> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        Optional<Scrap> existingScrapOpt = scrapRepository.findByBoardAndMemberId(target,currentLoginId);

        Member member = Member.setId(currentLoginId);

        if (existingScrapOpt.isPresent()) {
            scrapRepository.delete(existingScrapOpt.get());
            return false; // Scrap removed
        } else {
            Scrap newScrap = Scrap.addScrap(member,target);
            scrapRepository.save(newScrap);
            return true; // Scrap added
        }


    }
}
