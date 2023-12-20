package com.swyg.picketbackend.controller;

import com.swyg.picketbackend.Entity.Board;
import com.swyg.picketbackend.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/mylist/{memberId}")
    public List<Board> getMyBoardList(@PathVariable Long memberId){
        return boardService.getMyBoardList(memberId);
    }
    @GetMapping("/board/list") // 전체 리스트 가져오기
    public List<Board> getBoardList(String searchKeyWord){
        if(searchKeyWord == null) { // 검색 키워드 없을 때
            return boardService.getBoardList();
        }
        else { // 검색 키워드 있을 때
            return boardService.getSearchedBoardList(searchKeyWord);
        }
    }

    @GetMapping("/board/list/{id}") // 상세보기 페이지 요청
    public Board GetBoardDetail(@PathVariable Long id){
        return boardService.getBoardDetail(id);
    }

    @PostMapping("/board/write") // 버킷리스트 작성
    public ResponseEntity<Board> write(@RequestBody Board board, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        Board result;
        if(file != null) {
            result = boardService.writeWithFile(board, file);
        }
        else{
            result = boardService.write(board);
        }
        return (result != null) ?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/board/list/{id}") // 버킷리스트 수정
    public ResponseEntity<Board> update(@PathVariable Long id, @RequestBody Board board){
        Board result = boardService.update(id, board);
        return (result != null) ?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/board/list/{id}") // 버킷리스트 삭제
    public ResponseEntity<Board> delete(@PathVariable Long id){
        Board result = boardService.delete(id);
        return (result != null) ?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}

