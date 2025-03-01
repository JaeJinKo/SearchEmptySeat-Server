package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.Board.BoardRequest;
import com.BubbleWrap.SearchEmptySeat.dto.Board.BoardResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BoardResponse>> create(@RequestBody BoardRequest request) {
        return boardService.createBoard(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardResponse>>> getAll() {
        return boardService.getAllBoards();
    }
}
