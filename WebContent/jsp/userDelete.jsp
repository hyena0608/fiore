<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import="dao.*" %>
<%
	request.setCharacterEncoding("utf-8");
	
	String deleteno = request.getParameter("deleteno");
	
	UserDAO dao = new UserDAO();
	if(dao.delete(deleteno) == true){
		out.print("OK");
	}
	else{
		out.print("ER");
	}
	
%>