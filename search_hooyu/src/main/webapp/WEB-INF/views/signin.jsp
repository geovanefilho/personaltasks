<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login & Search Test Tech</title>
</head>
<body>
	<form:form method="POST" action="/login" modelAttribute="customerForm">
		<div>
			<span>${message}</span>
			<form:label path="emailAddress">Email</form:label>
			<form:input type="email" path="emailAddress" placeholder="john@test.com" required="required"/>
			<span>${error}</span>
			<br />
			<input type="submit" value="Sign In" />
		</div>
	</form:form>
</body>
</html>