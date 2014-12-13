<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%
	final String path = request.getContextPath();
%>
<body>
	<h1>Admin panel</h1>


	<h2>Logs</h2>
	
	<ul>
		<li><a href="<%=path%>/srv/admin/log/connection">API</a></li>
		<li><a href="<%=path%>/srv/admin/log/navigation">Global
				Navigation</a></li>
	</ul>
	<h2>
		<a href="<%=path%>/admin/performance/">Performance</a>
	</h2>
	<h2>Cache</h2>
	<ul>
		<li><a href="<%=path%>/admin/cache?flush_all">Flush all</a></li>
		<li><a href="<%=path%>/admin/cache?stats">Show stats</a></li>
	</ul>

	<h4>to be continued...</h4>

</body>
</html>