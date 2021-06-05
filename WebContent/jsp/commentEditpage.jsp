<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import ="dao.*" %>
<%
	request.setCharacterEncoding("utf-8");
	String editno = request.getParameter("editno");
	FeedDAO dao = new FeedDAO();
	String str = dao.editCommentlist(editno);
	out.print(str);
%>