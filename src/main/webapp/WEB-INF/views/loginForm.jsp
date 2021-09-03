<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>loginform</title>
    </head>
    <body>
        <h1>관리자 로그인</h1>
        <br>
        ${message}
        <br>
        <form method="post" action="login">
            비밀번호 : <input type="password" name="passwd"><br>
            <input type="submit">
        </form>
    </body>
</html>