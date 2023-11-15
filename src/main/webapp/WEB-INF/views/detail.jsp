<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>What Happened</title>
<link href="./css/detail.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="title">
	<h1>On this day...</h1>
	</div>
    <c:forEach var="event" items="${events}">
        <div class="singleEvent">
	        <p>${event.text}</p>
	        Related Wikipedia Page: <a href="${event.pageUrl}">${event.pageTitle}</a> - ${event.pageDescription}
        </div>
    </c:forEach>
</body>
</html>