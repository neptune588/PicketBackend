package com.swyg.picketbackend.controller;

import com.swyg.picketbackend.Entity.Comment;
import com.swyg.picketbackend.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/board/list/{boardId}/comments") // 특정 게시물 댓글 조회
    public ResponseEntity<List<Comment>> comments(@PathVariable Long boardId){
        List<Comment> result = commentService.comments(boardId);
        return (result != null) ?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/board/list/{boardId}/comments")
    public ResponseEntity<Comment> write(@PathVariable Long boardId,@RequestBody Comment comment){
        Comment result = commentService.write(boardId, comment);
        return (result != null) ?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/board/list/comments/{id}")
    public ResponseEntity<Comment> delete(@PathVariable Long id,String nickname){

        Comment result = commentService.delete(id, nickname);
        return (result != null) ?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
