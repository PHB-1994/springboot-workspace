package edu.thejoeun.member.model.service;

import edu.thejoeun.member.model.mapper.EmailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@Transactional // 예외 발생하면 롤백할게 (기본값으로 커밋)
public class EmailServiceImpl implements EmailService{

    @Autowired // EmailConfig 에 설정된 메일보내기 기능과 관련 환경설정 사용
    private JavaMailSender javaMailSender;

    @Autowired
    private MailSender mailSender;

    @Autowired // 템플릿 엔진 이용해서 auth/signup.html 에 있는 html 코드를 java 로 변환
    private SpringTemplateEngine templateEngine;
    @Autowired
    private EmailMapper emailMapper;

    // 이메일 보내기
    @Override
    public String sendMail(String htmlName, String email) {
        return "";
    }

    // HTML 파일을 읽어와 String 으로 변환(타임리프 템플릿 사용해서 html 가져오기)
    public String loadHtml(String authKey, String htmlName){
        Context context = new Context();

        // 타임리프가 적용된 html 에서 사용할 값 추가
        context.setVariable("authKey", authKey);

        // templates/pages/auth 폴더에서 htmlName 과 같은
        // .html 파일 내용을 읽어와 String 으로 변환
        return templateEngine.process("pages/auth" + htmlName, context);
    }

    // 이메일, 인증번호 확인
    @Override
    public int checkAuthKey(Map<String, Object> map) {
        return emailMapper.checkAuthKey(map);
    }
}
