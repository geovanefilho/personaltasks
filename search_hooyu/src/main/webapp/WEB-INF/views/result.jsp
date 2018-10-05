<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.hooyu.exercise.helper.ResponseBodyHelper" %>
<%@ page import="net.icdpublishing.exercise2.searchengine.domain.Record" %>
<%@ page import="net.icdpublishing.exercise2.searchengine.domain.SourceType" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search</title>
</head>
<body>
	<form:form method="GET" action="/search/">
		<div>
			<input type="submit" value="Search again" />

			<table>
				<tr>
					<th>Name</th>
					<th>Surname</th>
					<th>Source</th>
				</tr>
				<%
					List<Record> data = (List<Record>) ((ResponseBodyHelper)request.getAttribute("result")).getData();
					for (Record record : data) { %>
					<tr>
						<td><%= record.getPerson().getForename() %></td>
						<td><%= record.getPerson().getSurname()  %></td>
						<td>
							<table>
								<% for (SourceType source : record.getSourceTypes()) { %>
									<tr><td><%= source.toString() %></td></tr>
								<%} %>
							</table>
						</td>
					</tr>
				<%} %>
			</table>

		</div>
	</form:form>
</body>
</html>