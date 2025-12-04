package edu.thejoeun.board.model.service;

import edu.thejoeun.board.model.dto.Board;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    List<Board> getAllBoard();
    Board getBoardById(int id);
    /*
    TODO: 게시물 메인 이미지, 게시물 상세 이미지 전달받는 매개변수 두가지 추가
     */
    void createBoard(Board board, MultipartFile file);
}
