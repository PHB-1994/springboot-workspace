package edu.thejoeun.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
BackEnd 와 FrontEnd 가 나누어 작업할 때 사용
corsConfigurer 설정은
WebConfig 이름을 사용하거나 CorsConfig 라는 명칭을 사용하기도 함
회사에서 지정한 명명규칙을 따를 것
*/

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() { // 특정 주소와 메서드만 스프링부트에 접근할 수 있게 설정
        return new WebMvcConfigurer() { // new WebMvcConfigurer 객체 사용 후 return 을 한 번에 작성하는 방식
            @Override
            public void addCorsMappings(CorsRegistry registry) { // Cors 매핑 추가 기능
                // REST API CORS 설정
                registry.addMapping("/api/**")        // /api/ 로 시작하는 모든 하위 경로에 대해 접근 허용
                        .allowedOrigins("http://localhost:3000") // 요청을 허용할 출처를 지정
                        .allowCredentials(true)                  // 쿠키, HTTP 인증을 포함한 요청을 허용할 것인가
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 허용할 HTTP 메서드 종류
                        .allowedHeaders("*");                    // 허용할 HTTP 요청 헤더

                // WebSocket CORS 설정 추가
                registry.addMapping("/ws/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
