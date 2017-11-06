<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><c:set var="ctx"
	value="${pageContext.request.contextPath}" /><%@ taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><%@taglib
	prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%><%@ taglib uri="/WEB-INF/componentTagLib.tld" prefix="cpt"%>
	<c:set var="res" value="/egrantres" />
<c:set
	var="res_app" value="/egrantres_app" />
<s:set var="locale"><%=org.springframework.context.i18n.LocaleContextHolder.getLocale().toString()%></s:set>
<c:set var="lang"
	value="<%=org.springframework.context.i18n.LocaleContextHolder.getLocale().getLanguage() %>" />
	
 