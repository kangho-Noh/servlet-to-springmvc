<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!-- / 없이 그냥 save -> 상대경로 사용, [현재 URL이 속한 계층 경로 + /save] ((보통은 절대 경로 쓰는게 좋다)) -->
<form action="save" method="post">
    username: <input type="text" name="username" />
    age:      <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
</body>
</html>