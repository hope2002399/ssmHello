<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../jsps/public/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>打印</title>
<script type="text/javascript">
  


	function contentReady(){
		var obj = window.dialogArguments;
		var heads = document.getElementsByTagName("head");
		for(var i=0;i<obj.styles.length;i++){
			var link = document.createElement("link");
			link.rel = "stylesheet";
			link.type = "text/css";
			link.href = obj.styles[i].href;
			heads[0].appendChild(link);
		}
		document.getElementById("content").innerHTML = obj.content;
	}
	
	function printtable(){
		document.getElementById('print_button1').style.display = 'none';
		document.getElementById('print_button2').style.display = 'none';
		window.print();
	}
	
</script>
</head>
<body onload='contentReady();'>
	<div align="center">
		<input id='print_button1' class='button_02' type='button' onclick='printtable();' value='打 印' />
	</div>
	<div id='content'></div>
	<div align="center">
		<input id='print_button2' class='button_02' type='button' onclick='printtable();' value='打 印' />
	</div>
</body>
</html>
</body>
</html>