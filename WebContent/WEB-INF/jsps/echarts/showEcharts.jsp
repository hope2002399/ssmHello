<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../../jsps/public/taglib.jsp" %>
<%@ include file="../../jsps/public/taglibs.jsp" %>
<%@ include file="../../common/meta.jsp" %>
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	  <cpt:pieChart id="ev_prj_assign2" titleObj="{name:'轮项目指派进度'}" contentObj ="{x:90,y:120,size:100}" 
								key="ev_prj_assign2"
								dataObj="{name:['%%-未指派','%%-已指派'],href:['${ctx }/review/evassign/assign-list?atvtype=1&tabid=tab1&assign_status=0&group_status=-1','${ctx }/review/evassign/assign-list?atvtype=1&tabid=tab1&assign_status=1&group_status=-1']}"></cpt:pieChart>
	
	
	  <cpt:pieChart id="rptprogress_dw"  titleObj="{name:'绩效报告状态'}"  contentObj ="{x:170,y:190,size:100}" 
								key="rptprogress_dw"
								dataObj="{name:['%%-未指派1','%%-已指派2','%%-已指派3','%%-已指派4','%%-已指派5','%%-已指派6','%%-已指派7'],href:['${ctx }/review/evassign/assign-list?atvtype=1&tabid=tab1&assign_status=0&group_status=-1','${ctx }/review/evassign/assign-list?atvtype=1&tabid=tab1&assign_status=1&group_status=-1','${ctx }/review/evassign/assign-list?atvtype=1&tabid=tab1&assign_status=1&group_status=-1','${ctx }/review/evassign/assign-list?atvtype=1&tabid=tab1&assign_status=1&group_status=-1','${ctx }/review/evassign/assign-list?atvtype=1&tabid=tab1&assign_status=1&group_status=-1','${ctx }/review/evassign/assign-list?atvtype=1&tabid=tab1&assign_status=1&group_status=-1','${ctx }/review/evassign/assign-list?atvtype=1&tabid=tab1&assign_status=1&group_status=-1']}"></cpt:pieChart>
	 
</body>
</html>