package edu.thejoeun.common.scheduling.Service;

import edu.thejoeun.board.model.dto.Board;
import edu.thejoeun.common.scheduling.Mapper.SchedulingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulingServiceImpl implements SchedulingService {

    private final SchedulingMapper schedulingMapper;

    @Override
    public int updatePopularBoards() {
        // 지난 인기글 내역들을 삭제하지 않고, 보존하고 싶다면
        // 보존하기 ~ 삭제 메서드 주석 처리
        // 1. 기존 인기글 테이블 초기화
        schedulingMapper.deleteAllPopularBoards();

        // 2. 조회수 기준 상위 10개 게시글을 인기글로 등록
        return schedulingMapper.insertPopularBoards();
    }

    @Override
    public List<Board> getPopularBoards() {
        return schedulingMapper.selectPopularBoards();
    }
}
