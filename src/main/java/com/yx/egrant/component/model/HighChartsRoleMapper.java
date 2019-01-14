package com.yx.egrant.component.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * HighChartsRoleMapper图形报表角色关系实体
 * @author wen
 */
@Entity
@Table(name="highcharts_role_mapper")
public class HighChartsRoleMapper implements Serializable {
	
	private static final long serialVersionUID = 1923230161255630792L;

	@EmbeddedId
	private HighChartsRoleMapperPK id;
	
	@Column(name="include_psn_codes")
	private String includePsnCodes;//指定人   格式：，10000，
	
	@Column(name="exclusion_psn_codes")
	private String exclusionPsnCodes;//排除人   格式：，2000，

	public HighChartsRoleMapperPK getId()
	{
		return id;
	}

	public void setId(HighChartsRoleMapperPK id)
	{
		this.id = id;
	}

	public String getIncludePsnCodes()
	{
		return includePsnCodes;
	}

	public void setIncludePsnCodes(String includePsnCodes)
	{
		this.includePsnCodes = includePsnCodes;
	}

	public String getExclusionPsnCodes()
	{
		return exclusionPsnCodes;
	}

	public void setExclusionPsnCodes(String exclusionPsnCodes)
	{
		this.exclusionPsnCodes = exclusionPsnCodes;
	}
}
