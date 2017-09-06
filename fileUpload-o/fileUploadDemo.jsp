<%--
  Created by IntelliJ IDEA.
  User: ergou
  Date: 2017/9/6
  Time: 19:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<form action="/fileUpload" method="post" enctype="multipart/form-data">
    <input type="text" name="username">
    <input type="file" name="fileName">
    <input type="submit" value="上传">
</form>
<form action="/fileUpload2" method="post" enctype="multipart/form-data">
    <input type="text" name="username2">
    <input type="file" name="fileName2">
    <input type="submit" value="上传2">
</form>
</body>
</html>
