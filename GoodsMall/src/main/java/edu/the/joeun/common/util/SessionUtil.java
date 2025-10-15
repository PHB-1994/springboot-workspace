package edu.the.joeun.common.util;

import edu.the.joeun.model.User;
import jakarta.servlet.http.HttpSession;

/**
 * 세션(Session)
 * 서버 측에서 관리하는 사용자의 연결 상태
 * 사용자가 웹 어플리케이션에 접속하면 서버는 고유한 세션을 할당하고
 * 클라이언트는 그 세션을 식별하는 섹션 ID 를 서버에 전송
 * 세션 데이터는 서버에 저장되고, 클라이언트 측에는 세션 ID 만 저장
 * 보통 쿠키에 세션 ID 가 저장
 * 
 * 쿠키(Cookie)
 * 쿠키는 사용자 브라우저에 저장되고, 서버는 쿠키 데이터를 읽고 쓸 수 있다.
 * 중요한 데이터를 저장하기에는 클라이언트에 저장이기 때문에 보안상 위험하므로
 * 광고, 마케팅, 테마와 같은 동의여부 정도만 저장
 * 
 * 로컬 스토리지(Local Storage)
 * html 에서 도입된 클라이언트 측 데이터 저장 방식
 * 쿠키와 달리 서버에 데이터를 저장하지 않고, 오로지 클라이언트 브라우저에만 데이터 저장
 * 저장된 데이터는 유효기간이 없기 때문에 사용자가 직접 삭제하지 않으면 데이터 영원히
 * 남아있음. 컴퓨터 종료해도 남아있음
 * 장바구니 상태나 단순히 저장할 상태 정도만 저장
 */
public class SessionUtil {

    // 세션 키 상수 형태로 저장
    private static final String LOGIN_USER = "loginUser";

    /**
     * 로그인된 사용자 정보를 세션에 저장
     */
    public static void setLoginUser(HttpSession session, User user){
        session.setAttribute(LOGIN_USER, user);
        session.setMaxInactiveInterval(1800); // 30분만 로그인 설정
    }

    /**
     * 세션에서 로그인된 사용자 정보 가져오기
     */
    public static User getLoginUser(HttpSession session){
        return (User) session.getAttribute(LOGIN_USER);
    }

    /**
     * 로그인 여부 확인
     */
    public static boolean isLoginUser(HttpSession session){
        return session.getAttribute(LOGIN_USER) != null;
    }

    /**
     * 관리자 여부 확인
     */
    public static boolean isAdmin(HttpSession session){
        User user = getLoginUser(session);
        return user != null && "ADMIN".equals(user.getRole());
    }

    /**
     * 전체 세션 무효화(로그아웃)
     */
    public static void invalidate(HttpSession session){
        session.invalidate();
    }

}
