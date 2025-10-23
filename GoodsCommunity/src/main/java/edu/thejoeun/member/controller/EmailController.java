package edu.thejoeun.member.controller;

import edu.thejoeun.member.model.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("{authKey}")
@RestController
@RequestMapping("email")
@RequiredArgsConstructor // @Autowired 생성자 방식을 모두 자동으로 완성
// 하나하나 @Autowired 작성 안해도 됨
public class EmailController {

    // @Autowired -> @RequiredArgsConstructor 작성했기 때문에 생략 가능
    private final EmailService emailService; // 다른 사람이 조작하지 못하도록 final 설정

}
