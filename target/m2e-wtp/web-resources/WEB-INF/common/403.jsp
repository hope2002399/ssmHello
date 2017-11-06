<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="res" value="/egrantres"/>
<!DOCTYPE html>
<html>
<head>
	<title>403-error</title>
	<link href="${res}/css/public.css" rel="stylesheet" type="text/css" />
	<link href="${res}/css/public-${locale}.css" rel="stylesheet" type="text/css" />
	
	<style type="text/css">
body { margin:0px auto; padding:0px; font-size:12px; color:#333;}
ul{ list-style-type: none; }
ul, li{ margin:0px; padding:0px;}
.Permissions{ width:550px; padding:20px; background-color:#fef3ee; border:1px solid #f4c3ac; margin:0px auto; margin-top:100px;}
.tishi{ background:url(${res}/images/icon_tishi.gif) no-repeat; padding-left:28px; height:25px; line-height:25px; width:510px; font-size:14px; overflow:hidden;}
.tishi a{ font-size:14px; color:#F00; text-decoration:none;}
.tishi a:hover{ font-size:14px; color:#F00; text-decoration:underline;}
.Exp_list{ margin-left:28px; width:510px; margin-bottom:3px; color:#b34008;}
.Exp_list ul li{ line-height:16px; width:500px; overflow:hidden;}
</style>
</head>

<body> 
<fmt:bundle basename="resource.jsp500Resource"> 
<div class="Permissions">
           
              <div class="Exp_list">
                 <ul>
                 <li>403 - <fmt:message key="error.lackpower"/></li>
                 </ul>
                 </div>
              <div class="tishi"><fmt:message key="error.lackpowerAndClickHere"/><fmt:message key="error.error404Click"/><a href="${ctx }/j_spring_security_logout"><fmt:message key="error.here"/></a><fmt:message key="error.backToHome"/></div>
           
</div>
</fmt:bundle> 

    
</body>
</html>