package com.iris.egrant.component.dao;

import org.springframework.stereotype.Repository;

import com.iris.egrant.component.model.PieChart;
import com.iris.egrant.orm.hibernate.HibernateDao;

/**
 * 
 * 
 * 饼图dao.
 * 
 */
@Repository
public class PieChartDao extends HibernateDao<PieChart, String> {

}
