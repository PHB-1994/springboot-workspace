package edu.the.joeun.common.config;

import edu.the.joeun.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 관리자 권한 체크를 자동화하는 인터셉터
 */
@Component
public class Admin인터셉터 implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if(loginUser == null){
            response.sendRedirect("/login");
            return false;
        }

        if(!"Admin".equals(loginUser.getRole())){
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
