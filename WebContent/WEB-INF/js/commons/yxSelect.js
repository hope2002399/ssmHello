function loadAjaxSelect(selectId,url,params,varParm){
$.ajax( {
	url : url,
	type : 'get',
	dataType:'json',
	cache:false,
	data : params,  
	success : function(data) {
		for(var i=0;i<data.length;i++){
			var value=data[i].id;
			var text=data[i].name;
			if(value==varParm){
				$("#"+selectId).append("<option value='"+value+"' selected='selected'>"+text+"</option>");
			}else{
				$("#"+selectId).append("<option value='"+value+"'>"+text+"</option>");
			}
			

		}		
	}
});
}
//根据class填充select
function loadAjaxSelectByClass(classId,url,params,varParm){
	$.ajax( {
		url : url,
		type : 'post',
		dataType : 'json',
		data : params,
		async : false,
		success : function(data) {
			if(!!data){
			$("."+classId).each(function(){
				for(var i=0;i<data.length;i++){
					var value=data[i].id;
					var text=data[i].name;
					if(value==varParm){
						$(this).append("<option value='"+value+"' selected='selected'>"+text+"</option>");
					}else{
						$(this).append("<option value='"+value+"'>"+text+"</option>");
					}
				}
			});
		}
			}
	});
	}

