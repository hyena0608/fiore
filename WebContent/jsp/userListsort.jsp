<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import ="dao.*" %>
<%
	request.setCharacterEncoding("utf-8");
	String which = request.getParameter("which");
	String ascdesc = request.getParameter("ascdesc");
	UserDAO dao = new UserDAO();
	String str = dao.getListsort(which, ascdesc);
	out.print(str);
%>