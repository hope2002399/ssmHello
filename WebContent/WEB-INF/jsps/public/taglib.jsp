<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/componentTagLib.tld" prefix="cpt"%>
<c:set var="ctx">${pageContext.request.contextPath }</c:set>
<c:set var="locale"><%=org.springframework.context.i18n.LocaleContextHolder.getLocale().toString() %></c:set>
<c:set var="lang" value="<%=org.springframework.context.i18n.LocaleContextHolder.getLocale().getLanguage() %>" />