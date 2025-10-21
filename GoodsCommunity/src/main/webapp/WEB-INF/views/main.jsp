<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoodsCommunity</title>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background-color: #f4f7f6;
            color: #333;
            box-sizing: border-box;
        }

        *, *::before, *::after {
            box-sizing: inherit;
        }

        .main-container {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px 20px;
            min-height: calc(100vh - 60px);
        }

        .welcome-box {
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
            padding: 40px 50px;
            text-align: center;
            max-width: 600px;
            width: 100%;
            border: 1px solid #e0e0e0;
        }

        .welcome-box h1 {
            font-size: 2.8rem;
            font-weight: 700;
            color: #2c3e50;
            margin: 0 0 15px 0;
        }

        .welcome-box .user-greeting {
            font-size: 1.35rem;
            color: #555;
            margin-bottom: 10px;
            font-weight: 500;
        }

        .welcome-box .user-greeting strong {
            color: #007bff;
            font-weight: 700;
        }

        .welcome-box p {
            font-size: 1rem;
            color: #666;
            line-height: 1.6;
            margin-bottom: 35px;
        }

        .btn-primary {
            display: inline-block;
            background-color: #007bff;
            color: #ffffff;
            padding: 14px 30px;
            font-size: 1rem;
            font-weight: 600;
            text-decoration: none;
            border-radius: 8px;
            transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.3s ease;
            border: none;
            cursor: pointer;
        }

        .btn-primary:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(0, 123, 255, 0.2);
        }

    </style>
</head>
<body>
<%@ include file="common/header.jsp" %>

<div class="main-container">
    <div class="welcome-box">
        <h1>GoodsCommunity</h1>

        <c:choose>
            <c:when test="${not empty sessionScope.loginUser}">
                <p class="user-greeting">
                    환영합니다, <strong>${sessionScope.loginUser.memberName}</strong>님!
                </p>
                <p>
                    즐거운 커뮤니티 생활을 시작해보세요.
                </p>
                <a href="/member/myPage" class="btn-primary">마이페이지</a>
            </c:when>
            <c:otherwise>
                <p class="user-greeting">
                    Goods Community
                </p>
                <p>
                    로그인하여 다양한 서비스를 이용해보세요.
                </p>
                <a href="/login" class="btn-primary">로그인</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>