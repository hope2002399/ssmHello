<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/public/taglib.jsp" %>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 <table>
	 	<tr >
	 		<td>a</td>
	 		<td>b</td>
	 		<td>c</td>
	 	</tr >
  	<c:forEach  items="${data.res }"  var="pro"> 
	 	<tr >
	 		<td>${pro.zh_title }</td>
	 		<td>${pro.org_code }</td>
	 		<td>${pro.start_date }</td>
	 	</tr >
  	</c:forEach>
 </table>
 
</body>
</html>