<script type="text/javascript" charset="utf-8">
   $(document).ready(function(){		
		
		<#if dataList != "">
			 loadCharts(${dataList}, '${rootUrl}', '${keys}', '${divIds}', '${defaultYear}', '${beforeDoFun}', '${afterDoFun}');				
		</#if>
});
</script>

<style type="text/css">

<!--

#mainContent .widget-place1{
float:left;
width:280px;
/*拖动块的高度是一定要的
不然当你把一列的所有可拖动
的块东拖到其他列的时候会有
问题，这个你可以根据具体情
况设置*/
height:auto;
min-height:200px;
margin:15px;
/*height:400px;*/

}
#mainContent{
width:100%;
}

-->

</style>

<#if keys == "">
	${divs}
<#else>
<input type="hidden" id="needChartsKeys" value="">
<input type="hidden" id="needDivIds" value="">
</#if>