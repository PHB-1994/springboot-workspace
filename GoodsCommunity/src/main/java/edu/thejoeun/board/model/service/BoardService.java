package edu.thejoeun.board.model.service;

import edu.thejoeun.board.model.dto.Board;

import java.util.List;

public interface BoardService {

    List<Board> getAllBoard();
    Board getBoardById(int id);
}
