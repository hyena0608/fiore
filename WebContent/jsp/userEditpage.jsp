<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import ="dao.*" %>
<%
	request.setCharacterEncoding("utf-8");
	String editno = request.getParameter("editno");
	UserDAO dao = new UserDAO();
	String str = dao.editlist(editno);
	out.print(str);
%>