package edu.thejoeun.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
BackEnd 와 FrontEnd 가 나누어 작업할 때 사용
corsConfigurer 설정은
WebConfig 이름을 사용하거나 CorsConfig 라는 명칭을 사용하기도 함
회사에서 지정한 명명규칙을 따를 것
*/

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // profile.upload.path
    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Value("${file.product.upload.path}")
    private String productUploadPath;

    @Value("${file.board.upload.path}")
    private String boardUploadPath;

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

    // 프로필 이미지 정적 리소스 매핑 추가
    // ctrl + o -> override

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // profile_images/** 로 요청이 오면 실제 파일 시스템 경로에서 이미지 가져오기
        // 폴더 별로 registry 설정한다. registry 는 다수가 될 수 있다.
        registry.addResourceHandler("/profile_images/**")
                .addResourceLocations("file:"+fileUploadPath+"/"); // 폴더 명칭뒤에 바로 이미지명칭 붙어서 에러 발생

        //게시물 이미지 폴더
        registry.addResourceHandler("/board_images/**")
                .addResourceLocations("file:"+boardUploadPath+"/");
        
        // 상품 이미지 : product_images/** 라는 변수이름으로 데이터 가져와서 활용하겠다는 요청이 들어오면
        // 실제 파일 시스템 경로에서 이미지를 가져와 사용
        // 현재는 DB 명칭과 바탕화면 폴더 명칭을 동일하게 했지만, DB 명칭은 img_prd 형태로 작성하고,
        // 바탕화면 폴더는 풀네임으로 작성하여, 외부에서 함부로 데이터를 가져가거나 서버 정보를 노출되지 않도록
        // 보호할 수 있다.
        /**
         * 동작 방식
         * 클라이언트가 /product_images/제품번호/main.jpg 요청
         * -> 실제 파일 : 바탕화면/product_images/제품번호/main.jpg 반환
         * 
         * 주의 사항 :
         * file: 뒤에 공백 없이 바로 연결
         * 경로 끝에 / 꼭 붙이기 (안붙이면 경로 인식 오류)
         * 프로필/상품 이미지 경로가 각각 독립적으로 설정되있는지 필히 확인
         */
        registry.addResourceHandler("/product_images/**")
                .addResourceLocations("file:"+productUploadPath+"/");

    }
}
