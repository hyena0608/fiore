<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import ="dao.*" %>
<%
	request.setCharacterEncoding("utf-8");
	String str = (new FeedDAO()).myCommentList();
	out.print(str);
%>