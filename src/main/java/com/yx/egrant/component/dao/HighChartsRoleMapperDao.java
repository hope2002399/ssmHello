package com.yx.egrant.component.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yx.egrant.component.model.HighChartsRoleMapper;
import com.yx.egrant.orm.hibernate.HibernateDao;

/**
 *  HighChartsRoleMapper图表dao
 *  @author wen
 */
@Repository
public class HighChartsRoleMapperDao extends HibernateDao<HighChartsRoleMapper, Long> {
	
	@SuppressWarnings("unchecked")
	public List<HighChartsRoleMapper> getCurrentRoleCharts(Integer roleId, Long psnCode)
	{
		String hql = "from HighChartsRoleMapper where id.roleId=:roleId or includePsnCodes like :psnCode";
		return this.createQuery(hql).setLong("roleId", roleId).setParameter("psnCode", "%"+ psnCode +"%").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<HighChartsRoleMapper> findAll()
	{
		String sql = "from HighChartsRoleMapper";
		return this.createQuery(sql).list();
	}
}

