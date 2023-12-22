package com.swyg.picketbackend.board.controller;

import com.swyg.picketbackend.board.Entity.Comment;
import com.swyg.picketbackend.board.dto.res.CommentResponseDTO;
import com.swyg.picketbackend.board.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Tag(name = "BoardController", description = "게시글 관련 api")
@RestController
@RequestMapping("/board/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /*@GetMapping("/{boardId}") // 특정 게시물 댓글 조회
    public ResponseEntity<List<CommentResponseDTO>> commentsList(@PathVariable Long boardId){
       return commentService.findComments(boardId);
    }*/

 /*   @PostMapping("/board/list/{boardId}/comments")
    public ResponseEntity<Comment> write(@PathVariable Long boardId,@RequestBody Comment comment){
        Comment result = commentService.write(boardId, comment);
        return (result != null) ?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }*/

    /*@DeleteMapping("/board/list/comments/{id}")
    public ResponseEntity<Comment> delete(@PathVariable Long id,String nickname){

        Comment result = commentService.delete(id, nickname);
        return (result != null) ?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }*/
}
