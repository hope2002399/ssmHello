package com.yx.egrant.component.model;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * HighCharts图形报表实体
 * @author wen
 */

@Entity
@Table(name="highcharts")
public class HighCharts implements Cloneable, Serializable{

	private static final long serialVersionUID = 7759267643708085880L;

	@Id
	@Column(name="charts_id")
	@SequenceGenerator(name = "SEQ_STORE", sequenceName = "seq_highcharts", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STORE")
	private Long chartsId;//图型CODE
	
	@Column(name="charts_key")            
	private String chartsKey;//业务key页面标签直接指定调用
	
	@Column(name="chart_type")            
	private String chartType;//业务key页面标签直接指定调用
	
	@Column(name="chart_name")
	private String chartName;//图名称
	
	@Column(name="chart_sort_no")            
	private String chartsSortNo;//图排序
	
	@Column(name="chart_attrs_objs")
	private String chartAttrsObjs;
	
	@Column(name="chart_data_objs")
	private String chartDataObjs;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="description")
	private String description;
	
	@Column(name="update_psn_code")
	private Long updatePsnCode;
	
	@Column(name="ENABLED")
	private String enabled;
	
	@Column(name="IS_HOME_PAGE")
	private String isHomePage;
	
	//关联的数据查询实体
	@OneToMany(mappedBy = "highCharts",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<HighChartsQuery> hcQueries = new HashSet<HighChartsQuery>();
	
	
	public String getEnabled() {
		return enabled;
	}

	public Set<HighChartsQuery> getHcQueries()
	{
		return hcQueries;
	}
	
	public void setHcQueries(Set<HighChartsQuery> hcQueries)
	{
		this.hcQueries = hcQueries;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getIsHomePage() {
		return isHomePage;
	}

	public void setIsHomePage(String isHomePage) {
		this.isHomePage = isHomePage;
	}


	public Long getChartsId()
	{
		return chartsId;
	}

	public void setChartsId(Long chartsId)
	{
		this.chartsId = chartsId;
	}

	public String getChartsKey()
	{
		return chartsKey;
	}

	public void setChartsKey(String chartsKey)
	{
		this.chartsKey = chartsKey;
	}



	public String getChartType()
	{
		return chartType;
	}

	public void setChartType(String chartType)
	{
		this.chartType = chartType;
	}

	public String getChartName()
	{
		return chartName;
	}

	public void setChartName(String chartName)
	{
		this.chartName = chartName;
	}

	public String getChartsSortNo()
	{
		return chartsSortNo;
	}

	public void setChartsSortNo(String chartsSortNo)
	{
		this.chartsSortNo = chartsSortNo;
	}

	public String getChartAttrsObjs()
	{
		return chartAttrsObjs;
	}

	public void setChartAttrsObjs(String chartAttrsObjs)
	{
		this.chartAttrsObjs = chartAttrsObjs;
	}

	public String getChartDataObjs()
	{
		return chartDataObjs;
	}

	public void setChartDataObjs(String chartDataObjs)
	{
		this.chartDataObjs = chartDataObjs;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Long getUpdatePsnCode()
	{
		return updatePsnCode;
	}

	public void setUpdatePsnCode(Long updatePsnCode)
	{
		this.updatePsnCode = updatePsnCode;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
