
<script type="text/javascript" charset="utf-8">
   $(document).ready(function(){
		
		<#if titleObj=="" >
			var titleObj="";
		<#else>
			var titleObj=${titleObj};
		</#if>
		<#if contentObj=="" >
			var contentObj="";
		<#else>
			var contentObj=${contentObj};
		</#if>
		<#if dataObj=="" >
			var dataObj="";
		<#else>
			var dataObj=${dataObj};
		</#if>
		
		<#if direction == "">
			var direction="";
		<#else>
			var direction=${direction};
		</#if>
		
		<#if key == "">
			pieChart("${id}",dataObj,contentObj,titleObj,direction);
		<#else>
			loadPieChart("${id}",${params},titleObj,contentObj,dataObj,direction);
		</#if>
 });
</script>
<div id="${id}" style="height:170px;">
	                    	
</div>
	

