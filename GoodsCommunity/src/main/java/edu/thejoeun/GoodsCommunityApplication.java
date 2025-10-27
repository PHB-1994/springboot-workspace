package edu.thejoeun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스프링 스케쥴러를 활성화하는 어노테이션
@SpringBootApplication
public class GoodsCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsCommunityApplication.class, args);
    }

}
