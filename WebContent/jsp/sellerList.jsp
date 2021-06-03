<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import ="dao.*" %>
<%
	request.setCharacterEncoding("utf-8");
	
	
	String str = (new UserDAO()).getSellerList();
	out.print(str);

%>