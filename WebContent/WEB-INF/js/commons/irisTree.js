/**
 * 本js为实现tree 是扩展了jquery.ztree-2.6.js
 */

var setting;// tree的设置 ,json格式
var zTreeObj;// 得到tree的实例
var zNodes = [];
var treebgId;// 包裹tree的背景div
var treeId = "";// tree的id
var inputId;// 要获取值的html标记 . text 或 select
var level = 0;// 可以选择的等级，并且把可以选择的选颜色加亮
var hiddenId = "";// 隐藏域id
var hiddenName = "";// 隐藏域name
var checkable = false;
var separator = ","; // 分隔符
var params = "";
var data = [];// 加载tree的json数据
var valueObj = "";
function initTree() {
	setting = {
		isSimpleData : true,
		treeNodeKey : "id",
		treeNodeParentKey : "pid",
		async : false,// 是否异步
		// asyncUrl:url, //获取节点数据的URL地址
		// asyncParam: ["id","pid","name"], //获取节点数据时，必须的数据名称，例如：id、name
		// asyncParamOther:
		// {"key":params.key,"sqlParamVal":params.sqlParamVal,"cacheable":params.cacheable},
		// //其它参数 ( key, value 键值对格式)
		fontCss : setFont,// 设置字体样式
		checkable : checkable,// 是否为checkbox
		checkType : {
			"Y" : "",
			"N" : ""
		},// checkbox勾选和取消时，父子节点互不影响
		callback : {
			beforeExpand : beforeExpand,// 展开触发的事件
			beforeCollapse : function() {
				return true;
			},// 收缩前触发的事件
			beforeClick : zTreeOnBeforeClick,// 点击节点之前触发的事件
			click : zTreeOnClick,// 点击事件
			nodeCreated : zTreeOnNodeCreated,// 节点创建事件
			change : zTreeOnChange,// checkbox改变时触发的事件
			asyncSuccess : zTreeAjaxSuccess
		// 异步加载成功事件
		}
	/*
	 * isSimpleData: true, treeNodeKey: "id", treeNodeParentKey: "pId", fontCss:
	 * setFont, checkable:false, callback: { nodeCreated: zTreeOnNodeCreated,
	 * beforeExpand: function(){return true;}, beforeCollapse: function(){return
	 * true;}, beforeClick: zTreeOnBeforeClick, click: zTreeOnClick }
	 */
	};

}
/**
 * @param inputIdVar	控件ID前缀
 * @param dataVar	用来展示树的json格式数据
 * @param paramsVar	树控件上js事件或控制控件的参数
 * @param value	已经选择的节点ID，用于定位数据在树中的节点位置
 * @param label	当加载树出现异常时显示的信息
 * */
function loadTree(inputIdVar, dataVar, paramsVar, value, label,showId) {
	if (!(dataVar == null || dataVar == "" || dataVar == "undefined")) {
		data = dataVar;
		params = paramsVar;
		valueObj = value;
		treeId = inputIdVar + "_treeId";
		treebgId = inputIdVar + "_bgId";
		inputId = inputIdVar;
		hiddenId = inputIdVar + "_hideId";
		hiddenName = inputIdVar + "_hideName";
		if ($("#" + inputIdVar + "_bgId").length == 0) {
			$(
					"<div>",
					{
						id : inputIdVar + '_bgId',
						html : '<div id="' + inputIdVar
								+ '_treeId" class="tree"></div>'
					}).appendTo("body");
		}

		$("#" + inputIdVar + "_bgId").css("position", "absolute");
		$("#" + inputIdVar + "_bgId").css("height", 250);
		// $("#"+feildName+"_bgId").css("width",$("#"+id).width()+10);
		$("#" + inputIdVar + "_bgId").css("background-color", "white");
		$("#" + inputIdVar + "_bgId").css("border", "1px solid");
		$("#" + inputIdVar + "_bgId").css("overflow-x", "hidden");
		$("#" + inputIdVar + "_bgId").css("overflow-y", "auto");
		$("#" + inputIdVar + "_bgId").css("z-index", "99999999");
		if (params.key
				&& $("#" + params.key).contents().find("#" + inputIdVar).length == 0) {
			$("#" + params.key).contents().find("#" + inputIdVar + "_text")
					.parent().append(
							'<input type="hidden" id="' + inputIdVar
									+ '" name="' + inputIdVar + '" />');
		}
		
		if (params.checkable == "false")
			checkable = false;
		else
			checkable = true;
		
		initTree();
		// if(!(params.level==null||params.level==""||params.level=="undefined")){
		// level=params.level;
		// }
		reloadTree();
		linkWithData();

		// 隐藏tree
		$("body").bind(
				"mousedown",
				function(event) {
					if (!(event.target.id == treebgId || $(event.target)
							.parents("#" + treebgId).length > 0)) {
						hideTree();
					}
				});
		
		if(showId==null || showId=="")
			showId = inputIdVar;
		showTree(showId);
		
	} else {
		hideTree();
		if (label != null)
			scmError(label);

		// 隐藏tree

	}

};

function initTitle(inputId){
	var title = "";
	var targetTitleObj = $("#"+inputId).parent().find("input[name$='"+inputId+"_name']");
	if (null != targetTitleObj && targetTitleObj!=undefined && targetTitleObj.length > 0) {
		title = $(targetTitleObj[0]).val();
	} else {
		title = $("#"+inputId).val();
	}
	setTitle($("#"+inputId), title);
}
/**
 * 查找指定ID的父ID
 * */
function findpid(p){
	var pid = null;
	for (var d in data) {
		if (data[d].id==p){
			pid = data[d].pid;
			if (pid==0)	//不查找父节点为0的节点
				pid = null;
			break;
		}
	}
	return pid;
}
/**
 * 展开指定的节点
 * */
function expandTargetNode(nodes, pid){
	addNodes(zTreeObj, data, pid);
	var cnodes = null;
	for (var node in nodes) {
		var n = nodes[node];
		if (n != null && n.id===pid) {
			zTreeObj.expandNode(n, true, false);
			cnodes = zTreeObj.getNodeByParam("id", pid).nodes;
			//flag = true;
			break;
		}
	}
	return cnodes;
}
function doLinkWithData(pid){
	var pids = new Array();
	while ((pid = findpid(pid)) != null) {	//找到节点的各个父节点，并push到pids中
		pids.push(pid);
	}
	var nodes = zTreeObj.getNodes();
	pid = pids.pop();
	while ((nodes = expandTargetNode(nodes, pid)) != null){	//从pids中pop父节点到子节点并展开对应的节点
		pid = pids.pop();
	}
}
/**
 * 数据和树节点同步展示，打开当前数据节点的所有父节点
 * */
function linkWithData(){
	if (null == valueObj || valueObj == undefined || valueObj == '')
		return;
	//valueObj = eval("("+valueObj+")");
	var values = valueObj.split(",");
	if (values && values.length > 0) {	//如果有多个值被选中，则展开多个节点
		for (var v in values) {
			doLinkWithData(values[v]);
		}
	} else {
		doLinkWithData(valueObj);
	}
}
function reloadTree() {
	hideTree();
	zTreeObj = $("#" + treeId).zTree(setting, "");
	addNodes(zTreeObj, data, 0);
}
// 加入节点
function addNodes(treeObj, data, pid) {
	var nodes = zTreeObj.getNodesByParam("pid", pid); 
	if (nodes.length == 0) {
		for ( var i = 0; i < data.length; i++) {
	
			if (data[i].pid == pid) {
				var cnode = zTreeObj.getNodeByParam("id", data[i].id);
				var node = {
					"id" : data[i].id,
					"name" : data[i].name,
					"isParent" : isParent(data[i].id, data)
				};
				var parentNode = zTreeObj.getNodeByParam("id", pid);
				if (pid == 0 && !node.isParent) {
					node.icon = "/egrantres/css/zTreeStyle/img/folder_Close.gif";
				}
				if (pid == 0 && (params.topCheckbox == "false")) {
					node.nocheck = true;
				} else {
					node.nocheck = false;
				}
				if(checkable){
					if(isParent(data[i].id, data) && params.onlyChooseLeaf == "true"){
						node.nocheck = true;
					}
				}
				treeObj.addNodes(parentNode, node);
			}
		}
	}

}

// 判断是否有子节点
function isParent(id, data) {
	for ( var i = 0; i < data.length; i++) {
		if (data[i].pid == id) {
			return true;
		}
	}
	return false;
}
//根据参数中的显示和屏蔽模式值设置显示结果
function buildName(treeNode, params){
	if (null!=treeNode&&null!=params){
		var name = treeNode.name;
		var names = name;
		var flag = true;
		if ($.trim(params.showPattern) == "all") { // 显示所有节点
			flag = false;
			var parent = treeNode.parentNode;
			var lev = 0;
			while (parent != null) {
				lev ++;
				names = parent.name+"," + names;
				parent = parent.parentNode;
			}
			names = names.split(",");
			var shield = $.trim(params.shieldPattern);
			var shields = shield.split(",");
			for (var i = shields.length - 1; i >= 1; i--) {	//过滤重复数据
				if (shields[i - 1] == shields[i]) {
					shields.splice(i, 1);
				}
			}
			shields.sort();	//对数组排序
			if (shields[0] > -1) { // 屏蔽节点
				for (var i = shields.length-1; i >= 0; i --) {
					if (shields[i] > lev) {
						continue;
					}
					if (shields[i] != "")
						names.splice(shields[i], 1);
				}
			}
		}
		name = "";
		if (flag) {
//			names += ",";
//			names = names.split(",");
			name = names;
		} else {
			for (var n in names) {
				name += names[n];
			}
		}
		return name;
	}
	return null;
}

// 节点点击事件
function zTreeOnClick(event, treeId, treeNode) {

	if (treeNode) {
		if (params.onlyChooseLeaf && params.onlyChooseLeaf == 'true' && treeNode.isParent == true) {
			return initOnlyChooseLeaf(params,treeNode);
		}
		if (checkable) {// 复选框

			if (treeNode.checked == false) {
				treeNode.checked = true;
				var checkObj = $("#" + treeNode.tId + "_check");
				checkObj.removeClass();
				checkObj.addClass("chk checkbox_true_full_focus");
			} else {
				treeNode.checked = false;
				var checkObj = $("#" + treeNode.tId + "_check");
				checkObj.removeClass();
				checkObj.addClass("chk checkbox_false_full");
			}

			var inputNameVal = "";
			var hideIdVal = "";
			var checkedNodes = zTreeObj.getCheckedNodes();
			for ( var i = 0; i < checkedNodes.length; i++) {
				if (i == 0) {
					inputNameVal = checkedNodes[i].name;
					hideIdVal = checkedNodes[i].id;
				} else {
					inputNameVal += "," + checkedNodes[i].name;
					hideIdVal += "," + checkedNodes[i].id;
				}
			}
			// $("#"+inputId).val(inputNameVal);
			// $("#"+hiddenId).val(hideIdVal);
			if ($("#" + inputId + "_text").length == 0) {
				$("#" + inputId).val(inputNameVal);
				$("#" + hiddenId).val(hideIdVal);
				$("#" + hiddenName).val(inputNameVal);
			} else {
				$("#" + params.key).contents().find("#" + inputId + "_text")
						.val(inputNameVal);
				$("#" + params.key).contents().find("#" + inputId).val(
						hideIdVal);
			}

			// 自定义的onclick事件
			if (params.onClick != "") {
				return eval(params.onClick + "(inputId, treeNode)");
			}
		} else {
			var name = buildName(treeNode, params);
			if ($("#" + inputId + "_text").length == 0) {
				$("#" + inputId).val(name);
				$("#" + hiddenId).val(treeNode.id);
				$("#" + hiddenName).val(name);
				
			} else {
				//$("#" + params.key).contents().find("#" + inputId + "_text").attr("value", treeNode.name);
				$("#" + params.key).contents().find("#" + inputId + "_text").val(name);
				$("#" + params.key).contents().find("#" + inputId).val(treeNode.id);
			}

			hideTree();
		}
		initTitle(inputId);

	}
}
// 设置字体样式
function setFont(treeId, treeNode) {
	if (treeNode && treeNode.level >= level) {
		return {
			color : "blue"
		};
	} else {
		return null;
	}
}
// 显示
function showTree(inputId) {
	var cityObj = $("#" + inputId);
	if (params.key) {
		cityObj = $("#" + params.key).contents().find("#" + inputId);
	}
	var cityOffset = cityObj.offset();
	$("#" + treebgId).css({
		left : cityOffset.left,
		top : cityOffset.top + cityObj.height() + 7
	}).show();
}
// 隐藏
function hideTree() {
	$("#" + treebgId).hide();
}
// 点击节点之前事件
function zTreeOnBeforeClick(treeId, treeNode) {
	// 自定义的onclick事件
	if (params.onClick != "" && params.onClick != undefined) {
		return eval(params.onClick + "(inputId, treeNode, params)");
	}

	/*
	 * var check = (treeNode && treeNode.level>=level); if (!check)
	 * alert("只能选择>="+level+"的选项！"); return check;
	 */
}
// 异步加载成功事件
function zTreeAjaxSuccess(event, treeId, treeNode, msg) {

}
// tree结构展开之前事件
function beforeExpand(treeId, treeNode) {
	// 避免重复加入
	var nodes = zTreeObj.getNodesByParam("pid", treeNode.id);
	if (nodes.length == 0) {
		addNodes(zTreeObj, data, treeNode.id);
		return false;
	}
	return true;
}
// 每个节点创建时的事件
function zTreeOnNodeCreated(event, treeId, treeNode) {

	if (checkable) {
		if ($("#" + hiddenId).length > 0) {
			var ids = $("#" + hiddenId).val().split(",");
			for ( var i = 0; i < ids.length; i++) {
				if (ids[i] == treeNode.id) {

					zTreeObj.selectNode(treeNode);
					treeNode.checked = true;
					// 添加checkbox的样式,默认情况下要鼠标移上去才显示checkbox勾选状态
					var checkObj = $("#" + treeNode.tId + "_check");
					checkObj.addClass("chk checkbox_true_full_focus");
					var parentNode = treeNode.parentNode;
					if (!(parentNode == null || parentNode == "" || parentNode == "undefined")) {
						zTreeObj.selectNode(parentNode);
						parentNode.checked = true;
						// 添加checkbox的样式,默认情况下要鼠标移上去才显示checkbox勾选状态
						var checkObj = $("#" + parentNode.tId + "_check");
						checkObj.addClass("chk checkbox_true_full_focus");
					}
				}
			}
		}
	} else {
		if ($("#" + hiddenId).val() == treeNode.id) {
			zTreeObj.selectNode(treeNode);
		}
	}
}
// checkbox改变时触发的事件,只对checkbox有效
function zTreeOnChange(event, treeId, treeNode) {
	if (treeNode.checked) {
		zTreeObj.selectNode(treeNode);
	}
	var inputNameVal = "";
	var hideIdVal = "";
	var checkedNodes = zTreeObj.getChangeCheckedNodes();
	for ( var i = 0; i < checkedNodes.length; i++) {
		if (i == 0) {
			inputNameVal = checkedNodes[i].name;
			hideIdVal = checkedNodes[i].id;
		} else {
			inputNameVal += "," + checkedNodes[i].name;
			hideIdVal += "," + checkedNodes[i].id;
		}
	}
	if ($("#" + inputId + "_text").length == 0) {
		$("#" + inputId).val(inputNameVal);
		$("#" + hiddenId).val(hideIdVal);
		$("#" + hiddenName).val(inputNameVal);
	} else {
		$("#" + params.key).contents().find("#" + inputId + "_text").val(
				inputNameVal);
		$("#" + params.key).contents().find("#" + inputId).val(hideIdVal);
	}
	initTitle(inputId);

	// 自定义的onclick事件
	if (params.onClick != "") {
		return eval(params.onClick + "(inputId, treeNode)");
	}
}

function parentCannotClick(treeId, treeNode) {
	var check = (treeNode && treeNode.level >= level);
	// if (!check) alert("只能选择>="+level+"的选项！");
	return check;
}
