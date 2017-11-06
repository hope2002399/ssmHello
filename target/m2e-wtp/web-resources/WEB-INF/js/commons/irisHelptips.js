//鍒濆鍖栧府鍔╂彁绀�
function initHelpTips(key,ctx){
	$.ajax( {
		url : ctx+"/cpt/ajax-init-tips",
		type : 'post',
		dataType:'json',
		data : {'key':key},
		success : function(data) {
			if(data){
				$(".detail2").hide();
				$(".on").show();
				$(".off").hide();
			}else{
				$(".detail2").show();
				$(".on").hide();
				$(".off").show();
			}
		}
	});
}

function initHelpTipsAndVieFlag(key,ctx,defaultView){
	$.ajax( {
		url : ctx+"/cpt/ajax-init-tips",
		type : 'post',
		dataType:'json',
		data : {'key':key},
		success : function(data) {
			if(data && !defaultView){
				$(".detail2").hide();
				$(".on").show();
				$(".off").hide();
			}else{
				$(".detail2").show();
				$(".on").hide();
				$(".off").show();
			}
		}
	});
}
//甯姪鎻愮ず鐐瑰嚮浜嬩欢
function bindHelps(id,key,ctx)
{
	$("#"+id+" a").bind("click",function(event){
		var content = $(this).parents(".tipsBox").find(".detail2");
		if(content.is(":visible")){
			content.hide();
			$(this).parents(".tipsBox").find(".off").hide();
			$(this).parents(".tipsBox").find(".on").show();
			//闅愯棌甯姪鎻愮ず
			$.ajax( {
				url : ctx+"/cpt/hide-helptips",
				type : 'post',
				dataType:'json',
				data : {'key':key},
				success : function(data) {
				}
			});

		}else{
			content.show();
			$(this).parents(".tipsBox").find(".off").show();
			$(this).parents(".tipsBox").find(".on").hide();
			//鏄剧ず甯姪鎻愮ず
			$.ajax( {
				url : ctx+"/cpt/show-helptips",
				type : 'post',
				dataType:'json',
				data : {'key':key},
				success : function(data) {
					
				}
			});
		}
	});
	$("#"+id+" a").each(function(){
		var isShow = $(this).find(".detail2").is(":visible")? true : false;
		
		if(isShow){
			$(this).find(".on").hide();
			$(this).find(".off").show();
		}else{
			$(this).find(".on").show();
			$(this).find(".off").hide();
		}
	});
	//閾炬帴涓嶈繘琛屼簨浠跺啋娉�
	$(".tipsBox").find(".detail2 a").bind("click",function(event){
		stopBubble(event);
	});
}
