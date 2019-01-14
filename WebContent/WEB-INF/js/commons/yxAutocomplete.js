/**
 * 智能单选过滤下拉框组件 。json格式为[{id:111,name='aaaa'},{id=222,name='bbbb'}]
 * <input type="text" id="completeId" />
 * <input type="hidden" id="completeName" />
 * hiddenId：隐藏域Id. 选中下拉框时，选中项的Id赋予此id
 * inputId：input输入框的id
 * url：异步请求时的url
 * author:lj
 */
function irisAutocomplete(inputId,url,params,jsParams){
	var hiddenId=inputId+"_hideId";
	var hiddenName = inputId+"_hideName";
	var hiddenIdObj=$("#"+hiddenId);
	var inputIdObj=$("#"+inputId);
	var hiddenNameObj = $("#"+hiddenName);
	if(jsParams!=null && jsParams.searchKey!=null && jsParams.searchKey!='' && jsParams.searchKey!=undefined){
		hiddenIdObj=$("#"+$.trim(jsParams.searchKey)).find("#"+hiddenId);
		hiddenNameObj=$("#"+$.trim(jsParams.searchKey)).find("#"+hiddenName);
		inputIdObj=$("#"+$.trim(jsParams.searchKey)).find("#"+inputId);
	}
	
	inputIdObj.autocomplete(url, {
		minChars: 1,//最少输入几个字节
		//width: 280,//下拉框的宽度,默认为输入框的宽度
		highlight: function(value,term){return value;},
		max:10,//最大显示多个选项
		cacheLength: 0,
		dataType:'JSON', 
		scroll: false,//超过规定的height时是否有滚动条
		scrollHeight: 220,//下拉框的高度
		extraParams: params,//特别的参数.json格式的key-value.默认有个q参数，值为输入框的值，作为条件查询
		formatItem: function(row, i, max) {
			return row.name;
		},
		formatMatch: function(row, i, max) {
			return row.name;
		},
		formatResult: function(row) {
			return row.name; 
		},
		parse: function(data){//转化数据
			var parsed = [];
			for(var i=0;i<data.length;i++){ 
				var item = data[i];
				parsed.push({data:item,value:item.id.toString(),result:item.name});
			}
			if(jsParams.isAdd=='true'){
				var item={id:'-1',name:'手动添加'};
				parsed.push({data:item,value:item.id.toString(),result:item.name});
			}
			
			return parsed;
		}
	}).result(function(event, data, formatted) {//返回result
		hiddenIdObj.val(data.id);
		hiddenNameObj.val(data.name);
		setTitle(inputIdObj, data.name);
		if(jsParams.onClick!=""&&jsParams.onClick!="undefined"){
			eval(jsParams.onClick+"(inputId,data)");
		}
		
	});
	
}

/**
 * 智能多选过滤下拉框组件 。json格式为[{id:111,name='aaaa'},{id=222,name='bbbb'}]
 * <input type="text" id="inputId" />
 * <input type="hidden" id="hiddenId" />
 * hiddenId：隐藏域Id. 选中下拉框时，选中项的Id赋予此id
 * inputId：input输入框的id
 * url：异步请求时的url
 * author:lj
 */
function irisAutocompleteMultiSelect(inputId,url,params,jsParams){
	var hiddenId=inputId+"_hideId";
	$("#"+inputId).autocompleteMultiSelect(url, {
		multiple: true,
		highlight: function(value,term){return value;},
		minChars: 1,
		max: 10,
		autoFill: true,
		mustMatch: true,
		matchContains: false,
		scroll: true,
		scrollHeight: 220,
		extraParams:  params,//特别的参数.json格式的key-value.默认有个q参数，值为输入框的值，作为条件查询
		formatItem: function(row, i, max) {
			return row;
		},
		formatItem: function(row, i, max) {
		      var pspan = $("<span/>");	      
		      var span = $("<span/>");
		       var check =$("<input type=checkbox value="+row.id+" />");
		       span.append(check);
		       pspan.append(span.html()+row.name);
			return pspan.html();
		},
		parse: function(data){//转化数据
			var parsed = [];
			for(var i=0;i<data.length;i++){ 
				var item = data[i];
				parsed.push({data:item,value:item.id.toString(),result:item.name});
			}
			return parsed;
		}
		
	}).result(function(event, data, formatted) {//返回result
		/*
		var v=$("#"+hiddenId).val();
		var result="";
		if(v==""){
			result=data.id+",";
		}else{
			if(String(v).indexOf(data.id)>=0){
				result=v.replace(data.id+",","");
			}else{
				result=v+data.id+",";
			}
			
		}
		$("#"+hiddenId).val(result);
		*/
	});
	$(document).bind("click",hidden_out_div);
}

hidden_out_div = function(evt) {
	 var element = evt.srcElement||evt.target; 
	 var flag = true;
	 while (element) {
	  if ($(element).hasClass("ac_results")){
	   		flag = false;
			break;
	  }
	  	element = element.parentNode; 
	 }
	 if (flag) {
	 	$(".ac_results").hide();
	 }
};

/**
 * 智能单选过滤下拉框组件，为了适应如果没有下来值，做其他的操作 。json格式为[{id:111,name='aaaa'},{id=222,name='bbbb'}]
 * <input type="text" id="completeId" />
 * <input type="hidden" id="completeName" />
 * hiddenId：隐藏域Id. 选中下拉框时，选中项的Id赋予此id
 * inputId：input输入框的id
 * url：异步请求时的url
 * author:lj
 */
function irisAutocomplete2(inputId,url,params){
	var hiddenId=inputId+"_hideId";
	$("#"+inputId).autocomplete(url, {
		minChars: 1,//最少输入几个字节
		//width: 280,//下拉框的宽度,默认为输入框的宽度
		highlight: function(value,term){return value;},
		max:10,//最大显示多个选项
		cacheLength: 0,
		dataType:'JSON', 
		scroll: false,//超过规定的height时是否有滚动条
		scrollHeight: 220,//下拉框的高度
		extraParams: params,//特别的参数.json格式的key-value.默认有个q参数，值为输入框的值，作为条件查询
		formatItem: function(row, i, max) {
			//不存在记录
			if(row.id==-1){
				return '<a href="#">更多>></a>';
			}
			return row.name;
		},
		formatMatch: function(row, i, max) {
			return row.name;
		},
		formatResult: function(row) {
			return row.name; 
		},
		parse: function(data){//转化数据
			var parsed = [];
			for(var i=0;i<data.length;i++){ 
				var item = data[i];
				parsed.push({data:item,value:item.id.toString(),result:item.name});
			}
			if(parsed.length==0){
				var item={id: -1,name:''};
				parsed.push({data:item,value:item.id,result:item.name});
			}
			return parsed;
		}
	}).result(function(event, data, formatted) {//返回result
		$("#"+hiddenId).val(data.id);
		if(params.onChange!=""){
			eval(params.onChange+"(data)");
		}
		
	});
	
}


function irisAutocompleteTestKeyword(inputId,url,params){
	$("#"+inputId).autocomplete(url, {
		minChars: 0,//最少输入几个字节
		width: 135,//下拉框的宽度,默认为输入框的宽度
		max:100,
		highlight: function(value,term){return value;},
		cacheLength: 0,
		dataType:'JSON', 
		scroll: true,//超过规定的height时是否有滚动条
		scrollHeight: 220,//下拉框的高度
		minHeight : '100px', //最小高度
		extraParams: params,//特别的参数.json格式的key-value.默认有个q参数，值为输入框的值，作为条件查询
		formatItem: function(row, i, max) {
			return row.name;
		},
		formatMatch: function(row, i, max) {
			return row.name;
		},
		formatResult: function(row) {
			return row.name; 
		},
		parse: function(data){//转化数据
			var parsed = [];
			if(data.length > 0){
				for(var i=0;i<data.length;i++){ 
					var item = data[i];
					parsed.push({data:item,value:item.id.toString(),result:item.name});
				}
			}
			return parsed;
		}
	});
}

function irisAutocompleteKeyword(inputId,url,params){
	$("#"+inputId).autocomplete(url, {
		minChars: 1,//最少输入几个字节
		width: 135,//下拉框的宽度,默认为输入框的宽度
		max:10,
		highlight: function(value,term){return value;},
		cacheLength: 0,
		dataType:'JSON', 
		scroll: false,//超过规定的height时是否有滚动条
		scrollHeight: 220,//下拉框的高度
		extraParams: params,//特别的参数.json格式的key-value.默认有个q参数，值为输入框的值，作为条件查询
		formatItem: function(row, i, max) {
			return row.name;
		},
		formatMatch: function(row, i, max) {
			return row.name;
		},
		formatResult: function(row) {
			return row.name; 
		},
		parse: function(data){//转化数据
			var parsed = [];
			if(data.length > 0){
				for(var i=0;i<data.length;i++){ 
					var item = data[i];
					parsed.push({data:item,value:item.id.toString(),result:item.name});
				}
			}
			return parsed;
		}
	});
}
	