<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .user-info {
        position: absolute;
        top: 20px;
        right: 20px;
        background: white;
        padding: 10px 20px;
        border-radius: 5px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        display: flex;
        align-items: center;
        gap: 15px;
    }
    .user-name {
        font-weight: 600;
        color: #333;
    }
    .user-role {
        background: #667eea;
        color: white;
        padding: 4px 12px;
        border-radius: 12px;
        font-size: 12px;
    }
    .user-role.admin {
        background: #e74c3c;
    }
    .btn-logout {
        background: #e74c3c;
        color: white;
        border: none;
        padding: 6px 15px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
    }
    .btn-logout:hover {
        background: #c0392b;
    }
    .link-mypage {
        color: #667eea;
        text-decoration: none;
        font-weight: 600;
    }
    .link-mypage:hover {
        text-decoration: underline;
    }
</style>

<c:choose>
    <c:when test="${not empty sessionScope.loginUser}">
        <div class="user-info">
            <span class="user-name">${sessionScope.loginUser.memberName}</span>
            <span class="user-role ${sessionScope.loginUser.memberRole == 'ADMIN' ? 'admin' : ''}">
                    ${sessionScope.loginUser.memberRole == 'ADMIN' ? '관리자' : '일반회원'}
            </span>

            <a href="/member/myPage" class="link-mypage">마이페이지</a>

            <form action="/logout" method="post" style="margin: 0;">
                <button type="submit" class="btn-logout">로그아웃</button>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <div class="user-info">
            <a href="/login" class="link-mypage">로그인</a>
        </div>
    </c:otherwise>
</c:choose>