<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Session - Guess</title>
</head>
<body>
<h1>${message}</h1>
<c:if test="${sessionScope.count != null}">
    <form method="get" action="/guess">
        1 ~ 100사이의 숫자를 입력: <input type="number" name="number"/>
        <input type="submit" value="제출"/>
    </form>
</c:if>
<a href="guess">게임 다시 시작하기</a>
</body>
</html>
