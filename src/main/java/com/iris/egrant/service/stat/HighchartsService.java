package com.iris.egrant.service.stat;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.iris.egrant.component.model.HighCharts;
import com.iris.egrant.component.model.HighchartsCustomLayout;

 
 
public interface HighchartsService extends Serializable{

	List<HighCharts> getHighChartsByConditions(Map<String, Object> params);

	HighchartsCustomLayout getCustomLayout();

	String getRootUrl();

	 
}
