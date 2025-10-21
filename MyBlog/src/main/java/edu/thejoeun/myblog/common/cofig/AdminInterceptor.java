package edu.thejoeun.myblog.common.cofig;

import edu.thejoeun.myblog.model.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("loginUser");

        // 로그인 되지 않은 경우
        if (member == null) {
            response.sendRedirect("/login");
            return false;
        }

        if(!"ADMIN".equals(member.getMemberRole())) {
            response.sendRedirect("/"); //관리자가 아니면 메인으로 이동시키기
            return false;
        }
        return true;
    }
}
