<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import ="dao.*" %>
<%
	request.setCharacterEncoding("utf-8");
	String byhow = request.getParameter("byhow");
	
	if(byhow == "no"){
		String str = (new UserDAO()).getList2("no");
		out.print(str);
	}
	else{
		String str = (new UserDAO()).getList();
		out.print(str);
	}
%>