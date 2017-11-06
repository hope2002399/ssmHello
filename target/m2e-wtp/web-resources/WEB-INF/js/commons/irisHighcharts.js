/**
 * @param chartId
 * char 挂载点
 */
//所有数据页面
var alldataList = {};
var rootUrl = '';
var selectYear = '';
function IrisHighcharts(chartId) {
	this.beanChart = this;
	this.chartId = chartId; // chart 挂载点
	this.type = "pie";  //chart 类型(默认为饼图)
	this.linkUrl='';
	this.title = '';
	this.xAxis = {};
	this.yAxis = {};
	this.tooltip = {};
	this.legend = {};
	this.plotOptions = {};
	this.series = [];
	this.url = '';
	this.params = {};
	this.credits = {};
	this.setting = {};
	this.data = {};
	this.exporting = {};
	this.defaultYear="";
};

/**
 * 加载批量生成图表
 */
function loadCharts(dataList, root, chartsKeys, divIds, defaultYear, beforeDoFunName, afterDoFunName)
{
	//var dataList = eval("(" +data+")");
	var count = 0;
	var ids;
	//保存key和divId的值
	setKeysAndDivIds(chartsKeys, divIds);
	if(divIds != "" && divIds != undefined)
	{
		ids = divIds.split(",");
	}
	rootUrl=root;
	selectYear=defaultYear;
	//画图前需要执行的方法
	if(beforeDoFunName != 'null' && beforeDoFunName != undefined)
	{
		beforeCreate(beforeDoFunName);
	}
	alldataList=dataList;
	/*$("div[id^='widget-content_']").each(function(){
		//alert($(this).html());
		$(this).html("");
	});*/
	for(var i= 0; i < dataList.length; i++)
	{
			//清除以前的旧图
			$("#widget-content_"+ dataList[i].chartsKey).html("");
			$("#widget-content_"+ dataList[i].chartsKey).removeAttr("data-highcharts-chart");
			if(dataList[i].chartDataObjs != '{}')
			{
				var charts = new IrisHighcharts("widget-content_"+ dataList[i].chartsKey);
				charts.setSetting(dataList[i].chartAttrsObjs);
				charts.setChartKey(dataList[i].chartsKey);
				charts.setData(dataList[i].chartDataObjs)
				charts.setLinkUrl("javascript:viewLargeCharts('"+ dataList[i].chartsKey +"');");
				charts.setTitle(dataList[i].chartName);
				setChartsType(charts, dataList[i].chartType);
				if(dataList[i].isHomePage == '1')
				{
					charts.setDefaultYear(defaultYear);
					charts.build();
				}
				//自定义div，即自定义显示位置
				else
				{
					//重新设定画图使用的的divId
					if(ids != undefined && count<ids.length)
					{
						var divId = ids[count];
						var div = $("#" + divId);
						div.html("");
						div.removeAttr("data-highcharts-chart");
						charts.setChartId(divId.replace(/[ ]/g,""));
						count++;
					}
					charts.setDefaultYear(defaultYear);
					charts.build2();
				}
			}
	}
	//画图后需要执行的方法
	if(afterDoFunName != 'null' && afterDoFunName != undefined)
	{
		afterCreate(afterDoFunName);
	}
}

function beforeCreate(functionName)
{
	window.setTimeout(functionName,1);
}

function afterCreate(functionName)
{
	window.setTimeout(functionName,1);
}

/**
 * 当指定显示key和divId的图表,保存key和divIds,为查询操作提供参数
 */
function setKeysAndDivIds(chartsKeys, divIds)
{
	if(chartsKeys != null && chartsKeys != "" && chartsKeys != undefined)
	{
		$("#needChartsKeys").val(chartsKeys);
	}
	if(divIds != null && divIds != "" && divIds != undefined)
	{
		$("#needDivIds").val(divIds);
	}
}

/**
 * 查看大图的方法
 */
function viewLargeCharts(chartKey)
{
	tb_show("查看大图",
			"/egrantweb/high-charts/showLargeChart?chartKey="
			+ chartKey + "&TB_iframe=true&height=380&width=800",
			false);
}

/**
 * building chart
 */
IrisHighcharts.prototype.build = function(){
	var options = {
			//主表区配置
			chart:{
				type:this.type,
				renderTo:this.chartId,
				options3d:{enabled: false,alpha: 0, beta: 0},
				renderTo:this.chartId,
				reflow:true,
				width:280,
				height:190,
				marginTop:18,
				marginBottom:20,
				marginLeft:-130,
				marginRight:10,
				plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
			},
			//图表名称和字体大小及位置
			title: {
	            text: this.title,
	            style:{"fontSize": "14px", "fontWeight": "normal" },
	            x:-70,
	            y:5
	        },
	        xAxis: {
	            categories: []
	        },
	        yAxis: {
	            title: {
	                text: ''
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        //版权声明
	        credits:{enabled:true,
	        			  text:'查看大图',
	        			  href:this.linkUrl,
	        			  style: {
	        					"cursor": "pointer",
	        					"color": "#909090",
	        					"fontSize": "9px",
	        					"fontFamily":"Arial"
	        				},
	        			  position: {
	   		 			  		align: 'center',
	   		 			  		x: -70,
	   		 			  		verticalAlign: 'bottom',
	   		 			  		y: -16
	   		 		  	  }        
	            },
	      colors:['#1750a7', '#8aa717','#a74216','#a98b19','#5e17a5','#002f7e','#17a779','#1780a7'],
	        //图例
			legend:{
            	align:'right', 
            	layout:'vertical', 
            	verticalAlign:'middle',
            	width: 80,
               floating: true,
               itemStyle:{ "color": "#333333", "cursor": "pointer", "fontSize": "12px", "fontWeight": "normal" },
               itemWidth: 70,
            	x:-52,
            		y:-8,
            	useHTML:true
			        },
			exporting:{enabled:false},
			tooltip: { 
			pointFormat: '<b>{point.percentage:.0f}%</b>' },//<span style=\"color:{point.color}\">{series.name}</span>: 
			//数据点显示项
			plotOptions:{ 
				 		pie: {
				 				allowPointSelect: true,cursor: 'pointer',depth: 35,
				 			   dataLabels: {enabled: false,format: '{point.name}'}
				                ,
				            showInLegend: true,
				            borderWidth:0.01,
				            borderColor:'#F0F0F0'
	                           },
							  //饼图的单机事件
						   series: {
								  		cursor: 'pointer',
								  		size:'100%',
								  		point: {
								  		events: {
								  		click: function () {
								  				if(this.options.url != undefined)
								  				{
								  					var linUrl = rootUrl + this.options.url;
								  					var stat_year = "&stat_year=" + selectYear;
								  					if(linUrl.indexOf("?", 0) == -1)
								  					{
								  						stat_year = "?stat_year=" + selectYear;
								  					}
								  					location.href = linUrl + stat_year;//'/egrantweb/orgmanage/orgInfoEdit/editorginfo';
								  				}
								  				return false;
								  		},
								  		legendItemClick:function(){
							  				if(this.options.url != undefined)
							  				{
							  					var linUrl = rootUrl + this.options.url;
							  					var stat_year = "&stat_year=" + selectYear;
							  					if(linUrl.indexOf("?", 0) == -1)
							  					{
							  						stat_year = "?stat_year=" + selectYear;
							  					}
							  					location.href = linUrl + stat_year;
							  				}
							  				return false;
								  		},
								  		legendItemMouseOver:function(){
								  			//alert("鼠标位于上方");
								  		}
								  	  }
                }
			 }
			 },
			 series:[]
	};
	//深度合并镶套对象
	var setting = eval("(" +this.setting+")");
	var data = eval("(" +this.data+")");
	options = $.extend(true,options, setting.options, data);
	//去掉数据值为0的图例数据
	options = removeZeroData(options, this.type);
	this.beanChart = new Highcharts.Chart(options);
};

/**
 * 去掉图表数据中y值等于0的数据项
 */
function removeZeroData(options, type)
{
	var returnData;
	if(type == 'pie')
	{
		var d =  options.series[0].data;
		returnData = new Array();
		var index=0;
		for(var i = 0; i < d.length; i++)
		{
			perData = d[i];
			if(perData.y != 0)
			{
				returnData[index]=perData;
				index++;
			}
		}
		options.series[0].data=returnData;
	}
	return options;
}
//设置图类型
function setChartsType(obj, type)
{
	
	if(type == '1')
	{
		obj.type='line';
	}
	else if(type == '2')
	{
		obj.type='pie';
	}else if(type == '3')
	{
		obj.type='column';
	}
}

/**
 * building chart
 */
IrisHighcharts.prototype.build2 = function(){
	var options = {
			//主表区配置
			chart:{
				type:this.type,
				renderTo:this.chartId,
				options3d:{enabled: false,alpha: 0, beta: 0},
				renderTo:this.chartId,
				reflow:false,
				/*marginTop:20,
				marginLeft:-120,
				marginRight:10,*/
				plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
			},
			//图表名称和字体大小及位置
			title: {
	            text: this.title,
	            style:'font-size:12px',
	            x:-65,
	            y:2
	        },
	        xAxis: {
	            categories: [
	                '一月',
	                '二月',
	                '三月',
	                '四月',
	                '五月',
	                '六月',
	                '七月',
	                '八月',
	                '九月',
	                '十月',
	                '十一月',
	                '十二月'
	            ]
	        },
	        yAxis: {
	        	   allowDecimals:false,
	            min: 0,
	            title: {
	                text: '申报量 (份)',
	                align: 'high',
	                offset: 0,
	                rotation: 0,
	                y: -10
	            },
	            plotLines: [{
	                value: 0,
	                width: 0.001,
	                color: '#808080'
	            }]
	        },
	        exporting:{enabled:false},
	       //版权声明
	        credits:{enabled:false},
	        colors:['#1750a7', '#8aa717','#a74216','#a98b19','#5e17a5','#002f7e','#17a779','#1780a7'],
	          //图例
				legend:{
	            	align:'right', 
	            	layout:'vertical', 
	            	verticalAlign:'middle',
	            	/*width: 60,
	               floating: true,*/
	               itemStyle:{ "color": "#333333", "cursor": "pointer", "fontSize": "12px", "fontWeight": "normal" },
	              /* itemWidth: 70,
	            	x:0,*/
	            	useHTML:true
				        },
			tooltip: { 
		   pointFormat: '<span style=\"color:{point.color}\">{series.name}</span>: <b>{point.percentage:.0f}%</b>' },
			//数据点显示项
			plotOptions:{ 
				 		pie: {
				 				allowPointSelect: true,cursor: 'pointer',depth: 35,
				 			   dataLabels: {enabled: false,format: '{point.name}'}
				                ,
				            showInLegend: true,
				            borderWidth:0.01,
				            borderColor:'#F0F0F0'
	                           },
	                column: {
	                           pointPadding: 0.2,
	                           borderWidth: 0,
	                           pointWidth: 30,
	                           dataLabels:{
	                        	   enabled:true //是否显示数据标签
	                        	       }
	                           },
							  //饼图的单机事件
						   series: {
								  		cursor: 'pointer',
								  		point: {
								  		events: {
								  		click: function () {
								  				if(this.options.url != undefined)
								  				{
								  					location.href = rootUrl + this.options.url;
								  				}
								  				return false;
								  		},
								  		legendItemClick:function(){
							  				if(this.options.url != undefined)
							  				{
							  				  location.href = rootUrl + this.options.url;
							  				}
							  				return false;
								  		},
								  		mouseOver:function(){
								  			//alert("鼠标位于上方");
								  		}
								  	  }
                }
			 }
			 }
	};
	//深度合并镶套对象
	var setting = eval("(" +this.setting+")");
	var data = eval("(" +this.data+")");
	options = $.extend(true,options, setting.options, data);
	options = removeAttr(options,this.type,this.chartId,this.title, this.defaultYear);
	this.beanChart = new Highcharts.Chart(options);
	setTheme(this.type, this.beanChart);
};

/**
 * 设置个性化属性
 */
function removeAttr(options, t, id, name, selectYear)
{
	//折线图
	if(t == "line")
	{
		options.chart={type:t,renderTo:id};
		options.legend={};
		options.tooltip={headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
								pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
								'<td style="padding:0"><b>{point.y:.0f} 份</b></td></tr>',
	           footerFormat: '</table>',
	           shared: false,
	           useHTML: true
	       };
		options.plotOptions.series.cursor='';
		options.title={text: selectYear+name,style:{"fontSize": "14px", "fontWeight": "bold" }};
		return options;
	}
	else if(t == "pie")
	{
		return options;
	}
	else if(t == "column")
	{
		options.chart={type:t,renderTo:id};
		options.legend={};
		options.tooltip={headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
								pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
								'<td style="padding:0"><b>{point.y:.0f} 份</b></td></tr>',
	           footerFormat: '</table>',
	           shared: false,
	           useHTML: true
	       };
		options.plotOptions.series.cursor='';
		options.title={text: selectYear+name,style:{"fontSize": "14px", "fontWeight": "bold" }};
		return options;
	}
}

function setTheme(type, chartObj)
{
	if(type == "line")
	{
		chartObj.theme='grid-light';
	}
}

/**
 * 获取chart Dom
 */
IrisHighcharts.prototype.getChartDom = function() {
	return this.beanChart;
};

IrisHighcharts.prototype.setLinkUrl = function(linkUrl) {
	this.linkUrl=linkUrl;
};
/**
 * chart数据来源
 * @param url
 * string
 */
IrisHighcharts.prototype.setUrl = function(url) {
	this.url = url;
};

IrisHighcharts.prototype.setChartKey = function(chartKey) {
	this.chartKey = chartKey;
};

IrisHighcharts.prototype.setDefaultYear = function(defaultYear) {
	this.defaultYear = defaultYear;
};

IrisHighcharts.prototype.getDefaultYear = function() {
	return this.defaultYear;
};

IrisHighcharts.prototype.setData = function(data) {
	this.data = data;
};

IrisHighcharts.prototype.setChartId = function(chartId) {
	this.chartId = chartId;
};

IrisHighcharts.prototype.setParams = function(params) {
	this.params = params;
};

IrisHighcharts.prototype.setType = function(type) {
	this.type = type;
};

IrisHighcharts.prototype.setTitle = function(title) {
	this.title = title;
};

IrisHighcharts.prototype.setSetting= function(setting) {
	this.setting = setting;
};

IrisHighcharts.prototype.setXAxis = function(xAxis) {
	this.xAxis = xAxis;
};

IrisHighcharts.prototype.getXAxis = function() {
	return this.xAxis;
};

IrisHighcharts.prototype.setYAxis = function(yAxis) {
	this.yAxis = yAxis;
};

IrisHighcharts.prototype.getYAxis = function() {
	return this.yAxis;
};

IrisHighcharts.prototype.setTooltip = function(tooltip) {
	this.tooltip = tooltip;
};

IrisHighcharts.prototype.getTooltip = function() {
	return this.tooltip;
};

IrisHighcharts.prototype.setLegend = function(legend) {
	this.legend = legend;
};

IrisHighcharts.prototype.setPlotOptions = function(plotOptions) {
	this.plotOptions = plotOptions;
};

IrisHighcharts.prototype.getLegend = function() {
	return this.legend;
};

IrisHighcharts.prototype.setCredits = function(credits) {
	this.credits = credits;
};

IrisHighcharts.prototype.getCredits = function() {
	return this.credits;
};

IrisHighcharts.prototype.setExporting = function(exporting) {
	this.exporting = exporting;
};


