<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>This is directed from JSP</title>
</head>
<body>
	<%
		System.out.println("haha this a me!");
		Date date = new Date();
	%>
	<h1>My name is ${name} and my password is ${password}</h1>
	<h1>
		today is
		<%=date%></h1>
	<!-- without putting the post in the form it still accept the password as get and put it in the url after submit -->
	<p>
		<font color="red">${errorMsg}</font>
	</p>
	<!-- This is an example of sending http form post request to an API -->
	<form action="/login.do" method="post">
		Enter your user name <input type="text" name="username" />Enter your
		password <input type="password" name="password" /><input type="submit"
			value="submit">
	</form>


</body>
</html>