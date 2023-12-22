package com.swyg.picketbackend.board.controller;

import com.swyg.picketbackend.board.dto.req.BoardRequestDTO;
import com.swyg.picketbackend.board.dto.res.BoardResponseDTO;
import com.swyg.picketbackend.board.dto.req.PatchBoardDTO;
import com.swyg.picketbackend.board.service.BoardService;
import com.swyg.picketbackend.global.dto.SuccessResponse;
import com.swyg.picketbackend.global.util.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Log4j2
@Tag(name = "BoardController", description = "게시글 관련 api")
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "나의 버킷리스트 조회", description = "로그인한 회원의 버킷리스트 조회 url에 memberId 필요")
    @GetMapping("/myposts/{memberId}") // 나의 버킷 리스트 조회
    public List<BoardResponseDTO> getMyBoardList(@PathVariable Long memberId) {
        return boardService.getMyBoardList(memberId);
    }

    @Operation(summary = "전체 버킷리스트 조회", description = "전체 버킷리스트 조회 api")
    @GetMapping("/list") // 전체 버킷 리스트 조회
    public List<BoardResponseDTO> getBoardList(String searchKeyword) {
        if (searchKeyword == null) { // 검색 키워드 없을 때
            return boardService.getBoardList();
        } else { // 검색 키워드 있을 때
            return boardService.getSearchedBoardList(searchKeyword);
        }
    }

    @Operation(summary = "버킷리스트 상세보기", description = "버킷리스트 상세보기 url에 boardId 필요") 
    @GetMapping("/{boardId}") // TODO : 버킷 리스트 상세보기 댓글까지 가져오기
    public BoardResponseDTO GetBoardDetail(@PathVariable Long boardId) {
        return boardService.getBoardDetail(boardId);
    }


    @Operation(summary = "버킷리스트 작성", description = "버킷리스트 작성 API")
    @PostMapping() // 버킷리스트 작성
    public ResponseEntity<SuccessResponse> write(
            @RequestPart BoardRequestDTO boardRequestDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            log.info("첨부파일이 있을 경우 insert...");
            boardService.writeWithFile(boardRequestDTO, file);
        } else {
            log.info("첨부파일 null 일경우 insert...");
            boardService.write(boardRequestDTO);
        }
        return SuccessResponse.success(SuccessCode.INSERT_SUCCESS);
    }

    @Operation(summary = "버킷리스트 수정", description = "버킷리스트 수정 API")
    @PatchMapping("/{boardId}") // 버킷리스트 수정
    public ResponseEntity<SuccessResponse> update(@PathVariable Long boardId, @RequestBody PatchBoardDTO patchBoardDTO) {
        boardService.update(boardId,patchBoardDTO);
        return SuccessResponse.success(SuccessCode.BOARD_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{boardId}") // 버킷리스트 삭제
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return SuccessResponse.success(SuccessCode.BOARD_DELETE_SUCCESS);
    }

}

