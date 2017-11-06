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
		<form action="${ctx}/fileUpbatch" method="post" enctype="multipart/form-data"> 
					 	<tr>
					 		<td><h2>文件上传</h2></td>
					 		<td><input  type="file" name ="files" /></td> 
					 	</tr>  
					 	<tr>
					 		<td><h2>文件上传</h2></td>
					 		<td><input  type="file" name ="files" /></td> 
					 	</tr>  
					 	 
					 <tr>
					 	<td><input type="submit" value="提交"></td>
					 	<td></td>
					 </tr>
		</form>
			 </table> 
</body>
</html>