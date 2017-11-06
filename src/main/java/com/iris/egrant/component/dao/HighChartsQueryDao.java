package com.iris.egrant.component.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.iris.egrant.component.model.HighChartsQuery;
import com.iris.egrant.orm.hibernate.HibernateDao;
 
/**
 * HighChartsQuery图表dao
 * @author wen
 */
@Repository
public class HighChartsQueryDao extends HibernateDao<HighChartsQuery, Long> {
	
	   public HighChartsQuery getValue(Long chartId, String queryKey)
	   {
		   if(!StringUtils.isBlank(chartId+"") && !StringUtils.isBlank(queryKey))
		   {
			   String sql= "from HighChartsQuery h where h.highCharts.chartsId=? and h.queryKey=?";
			   return (HighChartsQuery)this.createQuery(sql,chartId, queryKey).uniqueResult();
			    
		   }
		   return null;
	   }
	   
	 @SuppressWarnings("unchecked")
	public List<HighChartsQuery> findHighChartsQueriesByChartId(Long chartId)
	  {
		  String sql = "from HighChartsQuery  where highCharts.chartsId=?";
		  return this.createQuery(sql,chartId).list();
	  }

}

