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
	<tr>
		<td>this is hello </td>
	</tr>
	<tr>
		<td>
			<a href="${ctx}/showFileup">跳转文件上传页面</a>
		</td>
		<td>
			<a href="${ctx}/fileUpBatch">多文件跳转文件上传页面</a>
		</td>
	</tr>
</table>
</body>
</html>