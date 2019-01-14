/*
 * 进度条组件  百分比显示
 * res 资源文件根目录
 * progressBarId 组件id
 * percent 百分比数字
 * isShowText 是否显示文本
 */ 
function irisProgressBarPercent(res,progressBarId,percent,isShowText){
	
	if(percent=="undefined")
		percent=0
	if(isShowText=="undefined")
		isShowText=true;
	/*
	$("#spaceused1").progressBar();
	$("#spaceused2").progressBar({ barImage: 'images/progressbg_yellow.gif'} );
	$("#spaceused3").progressBar({ barImage: 'images/progressbg_orange.gif', showText: true} );
	$("#spaceused4").progressBar(66, { showText: true, barImage: 'images/progressbg_red.gif'} );
	*/
	
	//$("#"+progressBarId).progressBar(percent, { showText: true, boxImage:res+'/images/progressbar.gif' ,barImage: res+'/images/progressbg_green.gif'} );
	$("#"+progressBarId).progressBar(percent, { showText: isShowText, boxImage:res+'/images/progressbar.gif' ,barImage: res+'/images/progressbg_green.gif'} );
	
}
/*
 * 进度条组件  分数显示
 * res 资源文件根目录
 * progressBarId 组件id
 * currVal 当前分数
 * maxVal 最大值
 * isShowText 是否显示文本
 * callbackFun 回调函数
 */ 
function irisProgressBarFraction(res,progressBarId,currVal,maxVal,isShowText,callbackFun){
	if(currVal=="undefined")
		percent=0
	if(maxVal=="undefined")
		maxVal=0
	if(isShowText=="undefined")
		isShowText=true;
	//$(".pb5").progressBar({ max: 2000, textFormat: 'fraction', callback: function(data) { if (data.running_value == data.value) { alert("Callback example: Target reached!"); } }} );
	$("#"+progressBarId).progressBar(currVal,{showText: isShowText,max:maxVal,textFormat:'fraction',boxImage:res+'/images/progressbar.gif' ,barImage: res+'/images/progressbg_green.gif',callback:callbackFun});
}