package com.yx.egrant.service.stat;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yx.egrant.component.model.HighCharts;
import com.yx.egrant.component.model.HighchartsCustomLayout;

 
 
public interface HighchartsService extends Serializable{

	List<HighCharts> getHighChartsByConditions(Map<String, Object> params);

	HighchartsCustomLayout getCustomLayout();

	String getRootUrl();

	 
}
