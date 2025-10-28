package edu.thejoeun.common.scheduling.Service;

import edu.thejoeun.board.model.dto.Board;

import java.util.List;

public interface SchedulingService {

    /**
     * 게시글 목록 업데이트
     * @return 업데이트된 게시글 수
     */
    int updatePopularBoards();

    /**
     * 인기글 목록 조회
     * @return 인기글 목록 반환
     */
    List<Board> getPopularBoards();

    /**
     * 
     */
}
