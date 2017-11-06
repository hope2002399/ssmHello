<%@ page language="java"  pageEncoding="UTF-8"%>
<c:set var="res_app" value="/egrantres_app" />
<c:set var="res" value="/egrantres" />


<%
java.util.Calendar calendarSys=new   java.util.GregorianCalendar(); 
//取当前时间 
java.util.Date d =new java.util.Date(System.currentTimeMillis()); 
calendarSys.setTime(d); 
//转换后，取年，月，日 
int year=calendarSys.get(java.util.Calendar.YEAR); 
int month=calendarSys.get(java.util.Calendar.MONTH)+1;
int day=calendarSys.get(java.util.Calendar.DATE);
int hour=calendarSys.get(java.util.Calendar.HOUR);
int minute=calendarSys.get(java.util.Calendar.MINUTE);
int second=calendarSys.get(java.util.Calendar.SECOND);
%>
<c:set var="year" scope="request"><%=year%></c:set>
<c:set var="sercverSysDate" scope="request"><%=year+"-"+ month+"-"+day+"-"+hour+"-"+minute+"-"+second%></c:set>
<c:set var="sercverSysDate1" scope="request"><%=year+"-"+ (month<10 ? "0" :  "")+ month +"-"+ (day<10 ? "0" :  "") + day%></c:set>
<c:set var="sysDate" scope="request"><%=year+"-"+ (month<10 ? "0" :  "")+ month +"-"+ (day<10 ? "0" :  "") + day+" "+(hour<10 ? "0" :  "")+ hour +":"+ (minute<10 ? "0" :  "") + minute+":"+ (second<10 ? "0" :  "") + second%></c:set>