package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.Board.BoardRequest;
import com.BubbleWrap.SearchEmptySeat.dto.Board.BoardResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.model.Board;
import com.BubbleWrap.SearchEmptySeat.repository.BoardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse<BoardResponse>> createBoard(BoardRequest request) {
        Board b = new Board();
        b.setTitle(request.getTitle());
        b.setContent(request.getContent());
        b.setUserId(request.getUserId());
        b.setCreatedDate(LocalDateTime.now());
        b.setUpdatedDate(LocalDateTime.now());
        b.setNotice(request.isNotice());

        boardRepository.save(b);

        return ResponseEntity.ok(ApiResponse.success(new BoardResponse(b), "Board created"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<BoardResponse>>> getAllBoards() {
        List<Board> list = boardRepository.findAll();
        List<BoardResponse> resp = list.stream()
                .map(BoardResponse::new)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(resp, "All boards"));
    }
}
