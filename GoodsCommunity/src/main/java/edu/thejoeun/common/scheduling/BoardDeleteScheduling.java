package edu.thejoeun.common.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// Service 나 Controller 와 같이 특정 명칭을 지정해주지 않는 파일이고
// 객체처럼 활용해야하는 파일은 @Component 를 작성해서
// SpringBoot 에 @Bean 으로 등록
@Component
@Slf4j       // log.info() 를 쓸 사용하게 해주는 어노테이션
public class BoardDeleteScheduling {
}
