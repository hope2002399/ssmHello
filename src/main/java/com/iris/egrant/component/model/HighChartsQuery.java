package com.iris.egrant.component.model;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * HighChartsQuery图形报表查询实体
 * @author wen
 */
@Entity
@Table(name="highcharts_query")
public class HighChartsQuery  implements Serializable{
	
	/*@EmbeddedId
   private HighChartsQueryPK id ;*/

	private static final long serialVersionUID = -6196182469637799545L;

	@Id
	@Column(name="id")
	@SequenceGenerator(name = "SEQ_STORE", sequenceName = "seq_highcharts_query", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_STORE")
	private Long id;
	
	/*@Column(name="charts_id")
	private Long chartsId;*/
	
	@Column(name="query_key")
	private String queryKey;
	
	@Column(name="type")
	private String type;//接口类型   1：SQL 2：javabean常量类型
	
	@Column(name="sql")
	private String sql;//sql语句
	
	@Column(name="sql_cond")
	private String sqlParams;
	
	@Column(name="springbean")
	private String springBean;
	
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE },fetch = FetchType.LAZY)
	@JoinColumn(name = "charts_id")
	private HighCharts highCharts;
	
	
	public HighCharts getHighCharts()
	{
		return highCharts;
	}

	public void setHighCharts(HighCharts highCharts)
	{
		this.highCharts = highCharts;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/*public Long getChartsId()
	{
		return chartsId;
	}

	public void setChartsId(Long chartsId)
	{
		this.chartsId = chartsId;
	}*/

	public String getQueryKey()
	{
		return queryKey;
	}

	public void setQueryKey(String queryKey)
	{
		this.queryKey = queryKey;
	}

	/*@Override
	public boolean equals(Object o)
	{
		if (o instanceof HighChartsQueryPK)
		{
			HighChartsQueryPK key = (HighChartsQueryPK) o;
			if (this.chartsId == key.getChartsId()
					&& this.queryKey.equals(key.getQueryKey()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return this.chartsId.hashCode();
	}*/
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getSql()
	{
		return sql;
	}

	public void setSql(String sql)
	{
		this.sql = sql;
	}

	public String getSqlParams()
	{
		return sqlParams;
	}

	public void setSqlParams(String sqlParams)
	{
		this.sqlParams = sqlParams;
	}

	public String getSpringBean()
	{
		return springBean;
	}

	public void setSpringBean(String springBean)
	{
		this.springBean = springBean;
	}
}
