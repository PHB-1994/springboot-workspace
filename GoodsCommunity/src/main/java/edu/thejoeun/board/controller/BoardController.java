package edu.thejoeun.board.controller;

import edu.thejoeun.board.model.dto.Board;
import edu.thejoeun.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j                             // 로그 기록
@RequestMapping("/api/board")   // 모든 (post, get, put, delete ..) mapping 앞에 /api/board 를 공통으로 붙여주겠다.
@RestController                    // 백엔드 데이터 작업 / react 프론트 사용 시 주로 활용
@RequiredArgsConstructor           // @Autowired 대신 사용
public class BoardController {

    // serviceImpl 에서 재 사용된 기능을 활용할 수 있다.
    private final BoardService boardService;

    // 전체 게시물 조회
    @GetMapping("/all")
    public List<Board> getAllBoard() {
        // 전체 게시물 수 조회
        // 페이지네이션 정보 추가
        return boardService.getAllBoard();
    }

    // 게시물 상세 조회
    @GetMapping("/{id}")
    public Board getBoardById(@PathVariable int id) {
        return boardService.getBoardById(id);
    }

}
