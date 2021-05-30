<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%
	session.invalidate();
	

	String str = "<p align=center><br>관리자 로그아웃을 완료하였습니다.<br><br>";
	str += "<a href='../manager.html'><button>관리자 로그인하기</button></a></p>";
	
	out.print(str);
%>	