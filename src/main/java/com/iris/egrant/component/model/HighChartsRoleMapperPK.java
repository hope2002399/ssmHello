package com.iris.egrant.component.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

/**
 * HighChartsRoleMapper实体的 联合主键
 * 
 * @author wen
 */
@Embeddable
public class HighChartsRoleMapperPK implements Serializable
{
	private static final long serialVersionUID = -1738598862447521647L;

	@Column(name="charts_id")
	private Long chartsId;
	
	@Column(name="roleId")
	private Long roleId;
	
	
	public Long getChartsId()
	{
		return chartsId;
	}

	public void setChartsId(Long chartsId)
	{
		this.chartsId = chartsId;
	}

	public Long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof HighChartsRoleMapperPK)
		{
			HighChartsRoleMapperPK key = (HighChartsRoleMapperPK) o;
			if (this.chartsId == key.getChartsId()
					&& this.roleId==key.roleId)
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
	}

}
