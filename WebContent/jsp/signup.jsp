<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import="dao.*" %>
<%
	request.setCharacterEncoding("utf-8");
	
	String uid = request.getParameter("id");
	String userclass = request.getParameter("userclass");
	String jsonstr = request.getParameter("jsonstr");
	
	UserDAO dao = new UserDAO();
	if(dao.exists(uid)){
		out.print("EX");
		return;
	}
	
	if(dao.signup(uid, userclass, jsonstr) == true){
		session.setAttribute("id", uid);
		out.print("OK");
	}
	else {
		out.print("ER");
	}
	
%>