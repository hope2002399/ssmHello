package cn.iris.com.service.echarts;

import java.util.List;
import java.util.Map;

import com.iris.egrant.component.model.PieChart;

public interface PieChartService {

	List<String> findPieChartDataById(String id, Map map);

	/**
	 * 根据id获取统计饼图.
	 * 
	 * @param id
	 * @return
	 */
	public PieChart findPieChartById(String id);
}
