package com.swyg.picketbackend.board.controller;


import com.swyg.picketbackend.auth.util.SecurityUtil;
import com.swyg.picketbackend.board.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class ScrapController {

    private final ScrapService scrapService;


    @PostMapping("/{boardId}/scrap")
    public ResponseEntity<?> toggleScrap(@PathVariable Long boardId) {
        Long currentLoginId = SecurityUtil.getCurrentMemberId();
        boolean isScrapped = scrapService.toggleScrap(boardId, currentLoginId);
        return ResponseEntity.ok(isScrapped ? 1 : 0);
    }
}
