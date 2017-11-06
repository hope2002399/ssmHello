function MailTemplate(){
	this.tmpCode;
	this.tmpType;
	this.fromAddr;
	this.subject;
	this.content;
	this.sourceTmpCode;
	this.archives;
	this.divCode;
}

function ArchiveFile(){
	this.fileCode ;
	this.fileDesc;
}








/**
 * 带输入框的文件上传
 * @param type 附件类型
 * @param funName 回调函数名
 * @param tableId 相应的表id
 */
function irisAjaxFileUpload(type,funName,tableId){
	if(type==null||type==''){
		alert("请输入附件类型");
		return ;
	}
	/*
	$('#btnUpload2_cancel').attr("disabled", true);
	$("#loading").ajaxStart(function() {
		$(this).show();
	}).ajaxComplete(function() {
		$(this).hide();
	});//文件上传完成将图片隐藏起来//开始上传文件时显示一个图片
	*/
	$.ajaxFileUpload({
		url : ctx + '/file/ajax-fileupload',//请求地址
		secureuri : false,//一般设置为false
		fileElementId : 'filedata',//文件的id属性  
		limitSize : '10',//允许上传的文件大小,单位M
		allowType : '',//允许上传的文件类型.
		data : {'type':type},
		tableId:tableId,//tableID
		dataType : 'json',//返回值类型 
		success : function(data, status) //服务器成功响应处理函数
		{	
			if (data.result == 'error') {
				alert(data.msg);
			}else{
				//uploadSuccess(data,tableId);
				//回调方法
				eval(funName+"(data,tableId)");
			} 
			$("input[type=file]").each(function(i){
				$(this).change(function() {
					showFileName($(this),i);
				});
			});
		},
		error : function(data, status, e)//服务器响应失败处理函数
		{
			$('.file').each(function(j){
				$(this).attr('value','');
			});
			$("input[type=file]").each(function(i){
				$(this).change(function() {
					showFileName($(this),i);
				});
			});
			alert(status);
		}
	});

}

function showFileName(obj,i) {
		
	var filedata = obj.val();
	if (filedata == '')
		return;
	else {
		var fileName = filedata.substring(filedata.replace(/\\/g, '/')
				.lastIndexOf('/') + 1);
		
		$('.file').each(function(j){
			if(i==j){
				$(this).val(fileName);
			}
		});
		
	}
}

function JsonToStr(o) {      
	var arr = [];      
	var fmt = function(s) {      
		if (typeof s == 'object' && s != null) return JsonToStr(s);      
		return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s; 
	};  
	for (var i in o) arr.push("'" + i + "':" + fmt(o[i]));      
	return '{' + arr.join(',') + '}';      
}

/**
 * 不带输入框的文件上传
 * @param type 附件类型
 * @param funName 回调函数名
 * @param tableId 相应的表id
 */
function ajaxFileUploadAttach(type,funName,tableId) {
	if(type==null||type==''){
		alert("请输入附件类型");
		return ;
	}
	$.ajaxFileUpload( {
				url : ctx + "/file/ajax-fileupload",
				secureuri : false,
				fileElementId : 'filedata1',
				limitSize : '10',
				allowType : '',
				data : {'type':type},
				dataType : 'json',
				success : function(data, status) {
					
					if (data.result == 'error') {
						alert(data.msg);
						$("#filedata1").change(function() {
							showAttachFileName();
						});
						$('#file_attach').attr('value', '');
					} else {
						$("#filedata1").change(function() {
							showAttachFileName();
						});
						$('#file_attach').attr('value', '');
						
						eval(funName+"(data,tableId)");
					}
				},
				error : function(data, status, e) {
					$("#filedata1").change(function() {
						showAttachFileName();
					});
					$('#file_attach').attr('value', '');
					alert(status);
				}
			});
}

function showAttachFileName() {
	var filedata = $('#filedata1').val();
	if (filedata == '')
		return;
	else {
		var fileName = filedata.substring(filedata .replace(/\\/g, '/').lastIndexOf('/') + 1);
		$('#file_attach').attr('value', fileName);
		
	}
}




