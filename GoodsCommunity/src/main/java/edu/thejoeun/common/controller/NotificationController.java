package edu.thejoeun.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * WebSocket 를 통한 알림 컨트롤러
 * STOMP 프로토콜을 사용한 실시간 양방향 통신
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 클라이언트가 /app/notify 로 메세지를 보내면
     * /topic/notifications 를 구독한 모든 클라이언트에게 브로드캐스트
     * 브로드캐스트란 한 개의 송신자가 네트워크 내 모든 장체에게 데이터를 동시에 전송하는 방식
     * 아파트 공지
     * @param msg
     * @return
     */
    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public Map<String, Object> sendNotification(Map<String, Object> msg) {
        log.info("알림 메세지 수신 및 브로드캐스트 : {}", msg);
        return msg;
    }

    /**
     * 배송 시작, 커피 픽업 준비 완료, 1:1 문의, 메세지, 채팅 등
     * 특정 사용자에게만 알림 전송
     * @param username      사용자 이름
     * @param notification  알림 내용
     */
    public void sendToUser(String username, Map<String, Object> notification){
        simpMessagingTemplate.convertAndSendToUser(username,
                "/topic/notifications",
                notification);

        log.info("사용자 {}에게 개인 알림 전송 : {}", username, notification);
    }

    /**
     * 모든 사용자에게 알림 전송
     * @param notification
     */
    public void broadcastNotification(Map<String, Object> notification){
        simpMessagingTemplate.convertAndSend("/topic/notifications", notification);
        log.info("모든 사용자에게 알림 브로드캐스트 : {}", notification);
    }
}
