<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search</title>
</head>
<body>
	<form:form method="POST" action="/search/for" modelAttribute="query">
		<div>
			<span>${message}</span>
			<form:label path="surname">Surname</form:label>
			<form:input type="text" path="surname" />
			
			<br />
			<form:label path="postcode">Postcode</form:label>
			<form:input type="text" path="postcode" />
			
			<br />
			<input type="submit" value="Search" />
		</div>
	</form:form>
</body>
</html>