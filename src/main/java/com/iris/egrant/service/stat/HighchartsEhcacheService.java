package com.iris.egrant.service.stat;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.iris.egrant.component.model.HighCharts;
import com.iris.egrant.component.model.HighChartsRoleMapper;


 
/**
 *  HighCharts缓存处理接口
 * @author wen
 *
 */
 
public interface HighchartsEhcacheService extends Serializable
{
	/**
	 *  根据chartskey查找Highcharts数据
	 * @param keys
	 * @return
	 */
	public Map<String, HighCharts> getHighchartsFromEhCache(String[] keys);
	
	/**
	 *  根据chartId查找Highcharts数据
	 * @param keys
	 * @return
	 */
	public Map<String, HighCharts> getHighchartsFromEhCache(Long[] chartsIds);

	/**
	 *  根据chartskey查找图与角色的关系数据
	 * @param roleId
	 * @return
	 */
	public Map<String, HighChartsRoleMapper> getHighChartsRoleMapperFromEhCache(Integer roleId);
	
	/**
	 *  根据人的psnCode来查找对应的图表
	 * @param roleId
	 * @return
	 */
	public Map<String, HighChartsRoleMapper> getHighChartsRoleMapperFromEhCache(Long psnCode);

	/**
	 * 清除缓存的方法
	 */
	public void clearHighchartsEhcache();
	
	/**
	 *  缓存重是否有该角色对应的的图表
	 */
	public Boolean isExistsHighchartsWithRoleId(Integer roleId);
	/**
	 *  根据角色Id获得缓存中的highcharts图表对象集合
	 * @param roleId
	 * @return
	 */
	public List<HighCharts> getHighChartsByRole(Integer roleId);
}
