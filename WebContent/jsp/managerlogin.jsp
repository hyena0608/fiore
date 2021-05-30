<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import="dao.*" %>
<%
	request.setCharacterEncoding("utf-8");
	String managenum = request.getParameter("managenum");
	
	if(managenum.equals("fiore")){
		session.setAttribute("id", managenum);
		out.print("OK");
	}
	else{
		out.print("PE");
	}
%>