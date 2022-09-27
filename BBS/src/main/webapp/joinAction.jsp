<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8");%>
<jsp:useBean id="user" class="user.User" scope="page"/>  
<jsp:setProperty name="user" property="userID"/> 
<jsp:setProperty name="user" property="userPassword"/>
<jsp:setProperty name="user" property="userName"/> 
<jsp:setProperty name="user" property="userGender"/> 
<jsp:setProperty name="user" property="userEmail"/>    <!-- sign in 페이지에 있는 것을 입력받는것 -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID")!=null){
			userID = (String) session.getAttribute("userID");
		}
		if(userID!=null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 로그인이 되어있습니다.')");
			script.println("location.href = 'main.jsp'");//다시 로그인 페이지로 사용자를 돌려보냄
			script.println("</script>");
		}
		if(user.getUserID()==null || user.getUserPassword()==null || user.getUserName()==null || 
			user.getUserGender()==null|| user.getUserEmail()==null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('모든 항목을 적어주세요')");
			script.println("history.back()");//이전페이지로
			script.println("</script>");
		} else {
			UserDAO userDAO = new UserDAO();
			int result = userDAO.join(user);//line 6부터 입력받은 사항들을 넣어서 join함수수행
			if(result==-1){
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('이미 존재하는 아이디입니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
			else {//회원가입 성공
				session.setAttribute("userID",user.getUserID());//세션부여 후 세션페이지로 이동
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("location.href = 'main.jsp'");
				script.println("</script>");
			}
		}
		
	%><!-- 입력받은 ID와 PW를 로그인함수에 넣어서 실행시킴 -->
</body>
</html>