<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import ="dao.*" %>
<%
	request.setCharacterEncoding("utf-8");
	String uid = request.getParameter("id");
	UserDAO dao = new UserDAO();
	String str = dao.userSearch(uid);
	out.print(str);
%>