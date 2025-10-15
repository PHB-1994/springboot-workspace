package edu.the.joeun.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Login인터셉터 loginInterceptor;

    @Autowired
    private Admin인터셉터 adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 일반 유저가 로그인했을 때 접속할 수 있는 api 리스트
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(     // 유저가 특별히 더 들어갈 수 있는 api 주소들
                        "/goodList",  // 상품 목록
                        "/goodList"   // 상품 등록
                )
                .excludePathPatterns( // 누구든지 들어갈 수 있는 api 주소들
                        "/",          // 메인     페이지
                        "/login",     // 로그인   페이지
                        "/logout",    // 로그아웃 페이지
                        "/css/**",    // CSS   파일
                        "/js/**",     // JS    파일
                        "/images/**", // Image 파일
                        "/api/**",    // API   요청
                        "/user/add"   // 유저  등록
                );

        // 관리자 유저가 로그인했을 때 특별히 더 접속할 수 있는 api 리스트
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns(
                        "/user/list"    // 유저 목록은 관리자만 들어갈 수 있다.
                );
    }
}
