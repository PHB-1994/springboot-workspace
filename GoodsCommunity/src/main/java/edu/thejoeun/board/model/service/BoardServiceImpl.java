package edu.thejoeun.board.model.service;

import edu.thejoeun.board.model.dto.Board;
import edu.thejoeun.board.model.mapper.BoardMapper;
import edu.thejoeun.common.util.FileUploadService;
import edu.thejoeun.product.model.dto.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    // @Autowired
    // Autowired 보다 RequiredArgsConstructor 처리해주는 것이
    // 상수화하여 Mapper 를 사용할 수 있으므로 안전 -> 내부 메서드나 데이터 변경 불가
    private final BoardMapper boardMapper;
    private final FileUploadService fileUploadService;

    @Override
    public List<Board> getAllBoard() {
        return boardMapper.getAllBoard();
    }

    @Override
    public Board getBoardById(int id) {
        // 게시물 상세조회를 선택했을 때 해당 게시물의 조회수 증가
        boardMapper.updateViewCount(id);

        Board b = boardMapper.getBoardById(id);
        // 게시물 상세조회를 위해 id 를 입력하고, 입력한 id 에 해당하는 게시물이
        // 존재할 경우에는 조회된 데이터 전달
        // 존재하지 않을 경우에는 null 전달
        return b != null ? b : null;
    }

    @Transactional
    @Override
    public void createBoard(Board board, MultipartFile file) {

        if (file != null && !file.isEmpty()) {

            try {
                int result = boardMapper.insertBoard(board);

                if(result > 0 ){
                    String imageUrl = fileUploadService.uploadBoardImage(file, board.getId(), "main");

                    board.setBoardImage(imageUrl);
                    boardMapper.updateBoard(board);

                    log.info("게시물 등록 완료 - ID : {}, Title : {}",
                            board.getId(), board.getTitle());
                    
                } else {
                    log.info("게시물 등록 실패 -  Title : {}", board.getTitle());
                    throw new RuntimeException("게시물 등록에 실패했습니다.");
                }
                
            } catch (Exception e) {
                log.error("게시물 업로드 실패", e);
                throw new RuntimeException("게시물 이미지 업로드에 실패했습니다.");
            }
        } else {
            int result = boardMapper.insertBoard(board);
            
            if(result > 0 ){
                log.info("게시물 등록 완료 - ID : {}, Title : {}",
                        board.getId(), board.getTitle());
                
            } else {
                log.error("게시물 등록 실패 - {}", board.getTitle());
                throw new RuntimeException("게시물 등록에 실패했습니다.");
            }
        }
    }
}

