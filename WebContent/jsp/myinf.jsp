<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import ="dao.*" %>
<%
	request.setCharacterEncoding("utf-8");
	String uid = (String) session.getAttribute("id");
	String str = (new UserDAO()).myInfo(uid);
	out.print(str);
%>