package edu.thejoeun.board.controller;

import edu.thejoeun.board.model.dto.Board;
import edu.thejoeun.board.model.service.BoardService;
import edu.thejoeun.common.scheduling.Service.SchedulingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j                             // 로그 기록
@RequestMapping("/api/board")   // 모든 (post, get, put, delete ..) mapping 앞에 /api/board 를 공통으로 붙여주겠다.
@RestController                    // 백엔드 데이터 작업 / react 프론트 사용 시 주로 활용
@RequiredArgsConstructor           // @Autowired 대신 사용
public class BoardController {

    // serviceImpl 에서 재 사용된 기능을 활용할 수 있다.
    private final BoardService boardService;
    private final SchedulingService schedulingService;

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

    // 인기글 목록 조회
    @GetMapping("/popular") // /api/board/popular
    public List<Board> getPopularBoard() {
        return schedulingService.getPopularBoards();
    }

    /*
    인기글을 23시 59분까지 기다리지 않고, 개발자가
    인기글 업데이트가 무사히 잘 되는지 확인하는 방법 1탄
    @PostMapping("/popular/update")
    public int 인기글수동업데이트기능(){
        log.info("현재 23시 59분이 아니므로 인기글 수동으로 업데이트해서 확인");
        int result = schedulingService.updatePopularBoards();
        return result;
    }
    */

    @PostMapping // api endpoint = /api/board 맨 위에 작성한 RequestMapping 에 해당
    public void createBoard(@RequestBody Board board) {
        boardService.createBoard(board);
    }

}
