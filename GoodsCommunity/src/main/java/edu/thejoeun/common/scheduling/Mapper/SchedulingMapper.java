package edu.thejoeun.common.scheduling.Mapper;

import edu.thejoeun.board.model.dto.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SchedulingMapper {

    /*
    인기 테이블 전체 삭제
    void = 단순히 삭제 유무만 확인할 때
    int  = 몇 개가 삭제되었는지 삭제된 개수를 반환하여
    클라이언트 / 개발자가 확인해야할 때 사용
     */
    void deleteAllPopularBoards();

    /*
    조회수 기준 상위 게시글을 인기글로 등록

    void = 단순히 저장 유무만 확인하고, 저장이 잘 되면
    가입완료되었습니다. 게시물 등록되었습니다.

    int = 몇 개의 데이터가 저장되었는지 DB  row 에 작성된 insert 데이터 개수를
    클라이언트한테 반환하여 / 개발자에게 반환하여 몇개가 저장되었다. 표기
    @return 등록된 게시글 수
    */
    int insertPopularBoards();

    /*
    인기글 목록 조회
     */
    List<Board> selectPopularBoards();
}
