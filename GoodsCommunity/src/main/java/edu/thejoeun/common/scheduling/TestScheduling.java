package edu.thejoeun.common.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/* Spring Scheduler :
 * 스프링에서 제공하는 일정 시간/주기 마다 예정된 코드를 실행하는 객체
 * [설정방법]
 * 1. 프로젝트 명칭 Application.java 파일에
 * @Enable  S  che  duling 어노테이션 추가
 *
 * 2. 스케줄러 코드를 작성할 별도 클래스를 생성한 후 Bean 으로 등록
 * -> @Component 어노테이션 작성
 *
 * 3. 해당 클래스에 @Scheduled(시간/주기) 어노테이션을 축한 메서드 작성
 *
 *  * 주의사항 *
 *  - 해당 메서드는 반환형이 존재해서는 안된다.
 */
@Slf4j     // log 를 작성할 수 있는 어노테이션
@Component // -> Bean 등록 -> 스프링이 자동으로 스케쥴링 코드를 수행하게 함
public class TestScheduling {

    // @Scheduled // 매개변수
    // fixedDelay :
    // 이전 작업이 끝난 후 다음 작업이 시작할 때 까지의 시간을 지정

    // fixedRate :
    // 이전 작업이 시작한 후 다음 작업이 시작할 때 까지의 시간을 지정

    // cron
    // - UNIX 계열 잡 스케줄러 표현식

    // cron = "초 분 시 일 월 요일 [년도]" (일요일 1 ~ 토요일 7)

    // [2025년] 월요일 10월 27일 12시 50분 0초에 수행
    //      cron="0 50 12 27 10 2 2025

    // - 특수문자 별 의미
    // * : 모든 수
    // - : 두 사이의 값 (ex) 10-15 => 10 이상 15 이하
    // , : 특정 값 지정 (ex) 분 자리에 3, 6, 9, 12 => 3분 6분 9분 12분 동작
    // / : 값 증가      (ex) 0/5 => 0 부터 시작해서 5 씩 증가할 때 마다 수행
    // ? : 특별한 값 없이 (월/요일만 가능)
    // L : 마지막(월/요일만 가능)

    // @Scheduled(fixedRate = 5000) // ms 단위 5000 = 5초
    // @Scheduled(fixedDelay = 1000)
    // cron = "초 분 시 일 월 요일 [년도]"
    // @Scheduled(cron = "0 * * * * *")     // 매 0초마다 수행 (1분마다 수행)
    // @Scheduled(cron = "0 1 * * * *")    // 1분마다 수행
    // @Scheduled(cron = "0/10 * * * * *") // 매 0초부터 10초마다 수행 (1분마다 수행)
    // @Scheduled(cron = "0 0 0 * * *")    // 매일 자정마다 수행
    // @Scheduled(cron = "59 59 23 * * *") // 다음 날 되기 1초 전 수행
    public void testMethod(){
        log.info("0초 스케쥴러 테스트 중입니다...");
    }
}