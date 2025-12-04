package edu.thejoeun.board.model.service;

import edu.thejoeun.board.model.dto.Board;
import edu.thejoeun.board.model.mapper.BoardMapper;
import edu.thejoeun.common.util.FileUploadService;
import edu.thejoeun.product.model.dto.Product;
import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    // @Autowired
    // Autowired 보다 RequiredArgsConstructor 처리해주는 것이
    // 상수화하여 Mapper 를 사용할 수 있으므로 안전 -> 내부 메서드나 데이터 변경 불가
    private final BoardMapper boardMapper;
    private final FileUploadService fileUploadService;
    private final SimpMessagingTemplate messagingTemplate;  // WebSocket 메세지 전송

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

    /*
    TODO: 게시물 메인 이미지, 게시물 상세 이미지 전달받는 매개변수 두가지 추가
     */
    @Transactional
    @Override
    public void createBoard(Board board, MultipartFile mainImage, List<MultipartFile> detailImage) {
        // 1. try - catch 를 생성한다.
        try {
            // 2. 게시물 저장을 먼저한다 (ID 생성을 위하여!)
            boardMapper.insertBoard(board);
            log.info("게시물 저장 완료 : {}", board.getId());

            boolean 이미지존재유무 = false;

            // 3. 생성된 게시물 id 를 기반으로 메인 이미지 업로드 처리
            // 게시물을 등록하는 클라이언트가 메인, 상세이미지를 필수로 업로드한다는 보장이 없기 때문에
            // 유저가 이미지를 등록했는지, 안했는지의 유무에 따라 폴더를 생성하고, 이미지를 폴더 내에 추가하는 작업 진행
            if(mainImage != null && mainImage.isEmpty()){
                // board = 데이터베이스와 상호 작용할 변수 명칭
                String mainImagePath = uploadMainImage(board.getId(), mainImage);
                // 메인 이미지 저장할 때, fileUploadService 에서 폴더에 저장한 다음에 DB 에 저장
                board.setBoardMainImage(mainImagePath);
                이미지존재유무 = true;
            }

            // 4. 생성된 게시물 id 를 기반으로 상세 이미지 업로드 처리
            if(detailImage != null && detailImage.isEmpty()){
                String detailImagePath = uploadDetailImage(board.getId(), detailImage);
                board.setBoardDetailImage(detailImagePath);
                이미지존재유무 = true;
            }

            // 5. 이미지 경로 DB 업데이트 - updateBoardImages(board) 메서드 생성하기
            // 무조건 실행하는 것이 아니라 main 과 detail 에서 수정 작업이 일어난게 맞으면 업데이트
            if(이미지존재유무) {
                boardMapper.updateBoardImages(board);
                log.info("게시물 이미지 경로 DB 업데이트 완료");
            }

            // 6. websocket을 활용하여 실시간 알림 전송
            sendBoardNotification(board);

        } catch(Exception e) {
            log.error("게시물 이미지 업로드 중 오류 발생", e.getMessage());
            throw new RuntimeException("게시물 이미지 업로드에 실패했습니다.");

        }

    }

    /**
     * 게시물 메인 이미지 업로드
     * @param boardId       게시물 ID
     * @param mainImage     메인 이미지 파일
     * @return              업로드 된 이미지 경로
     * @throws IOException  파일 업로드 실패 시
     */
    private String uploadMainImage(int boardId,MultipartFile mainImage) throws IOException {
        String mianImagePath = fileUploadService.uploadBoardImage(mainImage, boardId, "main");
        log.info("메인 이미지 업로드 완료");
        return mianImagePath;
    }

    /**
     * 상세 이미지 여러 개 업로드 (최대 5장)
     * @param 게시물번호
     * @param 상세이미지들
     * @return
     * @throws IOException
     */
    private String uploadDetailImage(int 게시물번호, List<MultipartFile> 상세이미지들) throws IOException {
        List<String> DB에_저장하기위해_클라이언트한테전달받은_상세이미지명칭을담은공간 = new ArrayList<>();

        // 최대 5개 까지만 처리
        int 저장할수있는최대개수 = Math.min(상세이미지들.size(), 5);

        // for(int i = 0; i < 5; i++) 이렇게 쓸 경우
        // for 문에서 0 ~ 4 까지 총 5개를 무조건 반복하라 하면 배열 에러 뜸
        // 저장할 수 있는 최대 개수를 설정하여 최소 0 장부터 5장 까지 허용가능! 이라 설정한 다음
        // 클라이언트가 전달한 이미지 개수를 기반으로 for 문이 최대로 돌아야하는 숫자상태 설정
        for(int i = 0; i < 저장할수있는최대개수; i++){
            MultipartFile for문으로꺼재온상세이미지한장 = 상세이미지들.get(i);

            // 빈 파일 잘못된 파일은 스킵
            if(for문으로꺼재온상세이미지한장 == null && for문으로꺼재온상세이미지한장.isEmpty()){
                continue; // 다음으로 넘어가기
            }

            // 이미지를 폴더에 저장 작업할 때 detail_번호순번 형태로 저장됨
            String 컴퓨터에서저장완료한상세이미지명칭한장 = fileUploadService.uploadBoardImage(for문으로꺼재온상세이미지한장, 게시물번호, "detail_" + (i+1));
            DB에_저장하기위해_클라이언트한테전달받은_상세이미지명칭을담은공간.add(컴퓨터에서저장완료한상세이미지명칭한장);
            log.info("상세 이미지 {} 업로드 완료 : {}", (i+1), 컴퓨터에서저장완료한상세이미지명칭한장);
        }

        String result = String.join(",",DB에_저장하기위해_클라이언트한테전달받은_상세이미지명칭을담은공간);
        log.info("총 {} 개의 상세 이미지 업로드 완료", DB에_저장하기위해_클라이언트한테전달받은_상세이미지명칭을담은공간.size());
        return result;
    }

    /**
     * 게시물 작성 알림 전송
     * @param board 작성된 게시물 정보
     */
    private void sendBoardNotification(Board board) {
        // WebSocket 을 통해 실시간 알림 전송
        Map<String, Object> notification = new HashMap<>();
        notification.put("msg", "새로운 게시글이 작성되었습니다.");
        notification.put("boardId", board.getId());
        log.info("boardId : {}", board.getId());
        notification.put("title", board.getTitle());
        notification.put("writer", board.getWriter());
        notification.put("timestamp", System.currentTimeMillis());

        // /topic/notifications 을 구독한 모든 클라이언트에게 전송
        messagingTemplate.convertAndSend("/topic/notifications", notification);
        log.info("새 게시글 작성 및 WebSocket 알림 전송 완료 : {}", board.getTitle()); // 개발자 회사 로그용
    }
}

