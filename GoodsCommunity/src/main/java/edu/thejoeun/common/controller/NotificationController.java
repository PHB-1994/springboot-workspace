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
@RequestMapping
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public Map<String, Object> sendNotification(Map<String, Object> msg) {
        log.info("알림 메세지 수신 및 브로드캐스트 : {}", msg);
        return msg;
    }
}
