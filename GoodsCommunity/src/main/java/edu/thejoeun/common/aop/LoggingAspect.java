package edu.thejoeun.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
* @Component @Bean 등록까지 함께 들어있는 어노테이션
*           개발자가 만든 파일을 스프링부트에서 자체적으로 관리하도록 세팅
*           Service Mapper Controller 처럼 특정 기능으로 분류 지을 수 없는 객체 파일 문서
* @Aspect 공통 관심사가 작성된 클래스임 명시(AOP 동작용 클래스)
* @Slf4j  log 를 찍을 수 있는 객체를 생성코드 추가 (lombok 회사에서 제공하는 어노테이션)
* */
@Component
@Aspect
@Slf4j
public class LoggingAspect {
    /*
    advice : 끼워넣을 코드 (=기능)
     */
}
