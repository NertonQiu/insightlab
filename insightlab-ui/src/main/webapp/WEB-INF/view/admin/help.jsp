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
	<h1>Help page</h1>

	<h2>Troubleshooting global navigation</h2>
	
	<ul>
		<li>Try to clear browser cache.</li>
		<li>Clear Tomcat work directory (&lt;tomcat_directory&gt;/work).</li>
		<li>Restart Tomcat server.</li>
		<li>Check <a href="<%=path%>/srv/admin/log/navigation">logs</a>.</li>
		<li>Email developer team.</li>
	</ul>
	
	<i>Best regards, 
	developer team...</i>
</body>
</html>