
/**
 * 本js为实现饼状图
 *  //{加载图形的数据,对应各个数据的名称,对应各个数据的链接}
 * var dataObj={data:[55, 20, 13, 32],name:["%%.%%-aaaaaa","%%.%%-b","%%.%%-c","%%.%%-d"],href:[]};
 * var contentObj={x:320,y:240,size:100}; //{图的X轴,Y轴,图形大小}
 * var titleObj={x:320,y:100,name:"Interactive Pie Chart"}; //{标题的X轴,Y轴,标题内容}
 * var direction方位 默认:west
 */
function pieChart(pieId,dataObj,contentObj,titleObj,direction){

	if(direction==""||direction==null||direction=="undefined")
		direction="east";
	if(contentObj==""||contentObj==null||contentObj=="undefined"){
		contentObj=eval('('+"{x:70,y:90,size:65}"+')');
	}
	if(titleObj!=""){
		titleObj['x']=contentObj.x;
		titleObj['y']=10;
	}
	//该饼状图支持浏览器:Firefox 3.0+, Safari 3.0+, Opera 9.5+ and Internet Explorer 6.0+. 
    var r = Raphael(pieId);
    r.g.txtattr.font = "12px 'sonti', arial, sans-serif";
	
    
    //piechart函数的参数说明:1 X坐标值;2 Y坐标值;3 图形大小 4 图形需要的数据 5 需要扩展的属性
	// 第5个扩展属性的说明：legend为对图形的说明，可以和输入数据一一对应(%%.%% 为百分比格式);legendpos为图形说明的方位;href为饼状图中对应块的链接
    var _data = new Array();
    var _name = new Array();
    var _href = new Array();
    var _colors = new Array();
    //用来判断数据是否全部为0
    var flag=false;
    $.each(dataObj.data,function(index,item){
    	if(item != 0 && item != "0"){//如果数据数组中有0，则显示不正常
    		flag=true;
    		_data.push(item);
    		if(dataObj.name!=null){
    			_name.push(dataObj.name[index]);
    		}
    		if(dataObj.href!=null){
    			_href.push(dataObj.href[index]);
    		}
    		if(dataObj.colors!=null){
    			_colors.push(dataObj.colors[index]);
    		}
    	}
    }); 
    //如果没有自定义颜色则按照默认颜色配置
    if(_colors.length==0){
//    	_colors.push("#be3936");
//    	_colors.push("#87ab3e");
//    	_colors.push("#7755a0");
//    	_colors.push("#30a0be");
//    	_colors.push("#356cb0");
    	
    }
    
    var pie = r.g.piechart(contentObj.x, contentObj.y, contentObj.size, _data, {legend: _name, legendpos: direction, href: _href,colors:_colors});

    if(flag){//如果没有数据则不显示标题
        //标题名称和坐标位置以及字体大小
        if(!(titleObj==""||titleObj==null||titleObj=="undefined"))
        	r.g.text(titleObj.x, titleObj.y, titleObj.name).attr({"font-size": 14});
    	
    }
    
	//饼状图上的悬停事件
    pie.hover(function () {
        this.sector.stop();
        this.sector.scale(1.1, 1.1, this.cx, this.cy);
        if (this.label) {
            this.label[0].stop();
            this.label[0].scale(1.5);
            this.label[1].attr({"font-weight": 800});
        }
    }, function () {
        this.sector.animate({scale: [1, 1, this.cx, this.cy]}, 500, "bounce");
        if (this.label) {
            this.label[0].animate({scale: 1}, 500, "bounce");
            this.label[1].attr({"font-weight": 400});
        }
    });
    
    //文字上的鼠标悬停事件
    $("#"+pieId+" a").find("text").hover(function () {
    	/*var href = $(this).parents('a').attr('href');*/
    	$(this).attr("style","text-anchor: start; font:12px 'sonti',arial,sans-serif;fill:black;font-weight:800");
    	/* $("#"+pieId+" a").each(function(){
    		// var href1 = $(this).attr('href');
    		// if($.trim(href) == $.trim(href1)){
    		//	 alert($(this).mouseover());
    		// }
    	 });*/
    	
    	 
    }, function () {
    	$(this).attr("style","text-anchor: start; font: 12px 'sonti',arial,sans-serif;font-weight:400");
    });
};
//加载统计饼图
function loadPieChart(divId,params,titleObj,contentObj,dataObj,direction){
	$.ajax( {
		url : ctx+'/cpt/ajaxload-piechart',
		type : 'post',
		dataType:'json',
		data : params, 
		success : function(data) {
			if(data!=null&&data!=''){
				//加载数据
				dataObj.data=eval("["+data+"]");
				for(var i=0;i<dataObj.name.length;i++){
					dataObj.name[i]=dataObj.name[i]+"("+dataObj.data[i]+")";
				}
				pieChart(divId,dataObj,contentObj,titleObj,direction);
			}
		}
	});
};
