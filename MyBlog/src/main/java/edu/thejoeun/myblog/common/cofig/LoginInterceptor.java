package edu.thejoeun.myblog.common.cofig;

import edu.thejoeun.myblog.model.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// 아무 클래스에서나 작성
// 역할이 불명확한 형태의 설정
// SpringBoot 에서 알아서 관리해줘
// 기능 클래스, 도메인 구성 요소 주로 @Component 어노테이션 주로 사용
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        HttpSession session = request.getSession();
        // 특정 멤버의 세션을 저장하는 명칭 설정해서 유저 세션 데이터를 조회 설정
        Member loginUser = (Member) session.getAttribute("loginUser");
        // 로그인 안됐을 경우
        if(loginUser==null){
            response.sendRedirect("/login"); //다시 로그인으로 돌려보내고
            return false; //요청 중단
        }

        return true; //문제 없으면 계속 진행
    }
}
