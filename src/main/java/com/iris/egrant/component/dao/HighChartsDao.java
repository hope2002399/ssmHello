package com.iris.egrant.component.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iris.egrant.component.model.HighCharts;
import com.iris.egrant.component.model.HighchartsCustomLayout;
import com.iris.egrant.orm.hibernate.HibernateDao;
 
/**
 * HighCharts图表dao
 * 
 * @author wen
 */
@Repository
public class HighChartsDao extends HibernateDao<HighCharts, Long>
{

	@Autowired
	public HighChartsCustomLayoutDao highLayoutDao;

	/*@SuppressWarnings("unchecked")
	public List<HighCharts> findAll()
	{
		String sql = "from HighCharts where chartsId=34 order by chartType,chartsSortNo";
		return this.getSession().createQuery(sql).list();
	}*/

	@SuppressWarnings("unchecked")
	public List<HighCharts> findByRoleId(final Long roleId)
	{
		String hql = "from HighCharts h where h.enabled = '0' and h.isHomePage = '1' and h.chartsId in (select r.id.chartsId from HighChartsRoleMapper r where r.id.roleId = :roleId)  order by h.chartsSortNo ";
		return this.getSession().createQuery(hql).setLong("roleId", roleId).list();
	}

	public HighchartsCustomLayout getCustomLayout()
	{
		return this.highLayoutDao.get(Long.parseLong( "210"));
	}

	public void save(HighchartsCustomLayout customLayout)
	{
		this.highLayoutDao.save(customLayout);
	}
	
	@SuppressWarnings("unchecked")
	public List<HighCharts> findAll()
	{
		String sql = "from HighCharts h where h.enabled = '0'";
		return this.getSession().createQuery(sql).list();
	}

}
