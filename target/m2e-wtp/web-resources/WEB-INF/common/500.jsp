<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="res" value="/egrantres"/>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Exception) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	if (ex != null) {
		Logger logger = LoggerFactory.getLogger("500.jsp");
		logger.error(ex.getMessage(), ex);
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>服务器错误</title>
<link href="${res}/css/public.css" rel="stylesheet" type="text/css" />
<link href="${res}/css/public-${locale}.css" rel="stylesheet" type="text/css" />


</head>
<body id="all"> 
<fmt:bundle basename="resource.jsp500Resource">  
  <br />
  <br />
  <div class="Maintain">
  <div class="Maintain_pic"></div>
  <div class="Maintain_line"></div>
  <div class="Maintain_contant">
    <p><img src="${res}/images/Service_wz.gif" width="315" height="75" /></p>
    <p class="Service_back_buttom"><a href="${ctx}"></a></p>
  </div>
</fmt:bundle>
</body>
</html>
