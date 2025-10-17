package edu.thejoeun.myblog.common.util;

import edu.thejoeun.myblog.model.Member;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    private static final String LOGIN_USER = "loginUser";

    // 로그인 세션
    public static void setLoginUser(HttpSession session, Member member) {
        session.setAttribute(LOGIN_USER, member);
        session.setMaxInactiveInterval(1800);
    }

    // 로그아웃 세션
    public static void invalidateLoginUser(HttpSession session) {
        session.removeAttribute(LOGIN_USER);
    }
}
