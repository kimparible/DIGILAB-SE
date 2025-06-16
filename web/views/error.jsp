<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Error</h1>
    <p>${errorMessage}</p>
    <a href="${pageContext.request.contextPath}/">Back to Home</a>
</body>
</html>