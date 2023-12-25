package com.swyg.picketbackend.board.controller;

import com.swyg.picketbackend.auth.util.SecurityUtil;
import com.swyg.picketbackend.board.Entity.Heart;
import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.board.dto.res.heart.PostLikeResponseDTO;
import com.swyg.picketbackend.board.service.HeartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "BoardController", description = "버킷 관련 api")
@RequestMapping("/board")
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/{boardId}/like") // 버킷 좋아요 클릭
    public ResponseEntity<?> clickLike(@PathVariable Long boardId) {
        Long currentLoginId = SecurityUtil.getCurrentMemberId();
        boolean isLiked = heartService.toggleHeart(boardId, currentLoginId);

        return ResponseEntity.ok(isLiked ? 1 : 0);
    }


}