package edu.thejoeun.common.scheduling;

import edu.thejoeun.common.scheduling.Service.SchedulingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Service 나 Controller 와 같이 특정 명칭을 지정해주지 않는 파일이고
// 객체처럼 활용해야하는 파일은 @Component 를 작성해서
// SpringBoot 에 @Bean 으로 등록
@Component
@Slf4j       // log.info() 를 쓸 사용하게 해주는 어노테이션
@RequiredArgsConstructor
public class BoardScheduling {
    // 오버라이드 되어 사용된 기능을 사용하기 때문에 Service 나 ServiceImpl 가져오기
    private final SchedulingService  schedulingService;

    /*
     * 매일 23시 59분에 인기글 목록 업데이트
     * cron = "초 분 시 일 월 요일 [년도]"
     * 0 59 23 * * * : 매일 23시 59분 0초에 실행
     */

    // controller 에 작성하지 않아도 알아서 11시 59분이 되면
    // 자동으로 데이터 저장과 삭제를 진행할 것이기 때문에
    // PostMapping 이나 DeleteMapping 을 따로 api 설정해서 연결하지 않아도 된다.
    // 소비자한테 보여주는 것이 아니라, 우리 회사의 페이지를 위해서
    // 개발자가 자동 업데이트 처리를 진행하는 것이기 때문
    // 방법 2탄 : 배포 했을 때 제대로 동작할 지 확인하는 방법
    // dev  -      stage        - prod
    // 개발 - 배포전 회사 테스트 - 실제 배포
    // 실제 배포할 때 사용할 스케줄링 : @Scheduled(cron = "0 59 23 * * *")
    // 개발자가 스케줄링이 제대로 작동하는지 확인하는 스케줄링 : @Scheduled(cron = "0 * * * * *")
    // 10초마다 실행 @Scheduled(cron = "*/10 * * * * *")
    @Scheduled(cron = "*/10 * * * * *")
    public void updatePopularBoards(){
        String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // printf() 처럼 {} 를 작성하면 , 변수이름에 존재하는 데이터가 {} 내부에 작성
        // "==== 인기글 업데이트 스케쥴러 시작 [2025-10-28 23:59:00] ===="
        log.info("==== 인기글 업데이트 스케쥴러 시작 [{}] ====", startTime);
        // 서비스에서 update 인기 게시물 가져오기 작업
        try{
            // result 내부에는 delete 한 후, insert 를 몇 개 완성했는지
            // DB 에 저장되는 개수가 저장될 것
        int result = schedulingService.updatePopularBoards();
        // result 라는 공간에 저장된 개수를 log 로 확인
            // "" 사이에 들어갈 데이터 값
            // "" 사이에 데이터 값이 들어갖 ㅣ않아도 되면 , 로 이어서 작성하면 된다.
        log.info("인기글 업데이트 완료 : {} 건", result);
        } catch(Exception e){
            log.error("인기글 업데이트 중 오류 발생 : ", e);
        }

        // "==== 인기글 업데이트 스케쥴러 종료 [2025-10-28 23:59:01] ===="
        String endTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("==== 인기글 업데이트 스케쥴러 종료 [{}] ====", endTime);

    }

}
