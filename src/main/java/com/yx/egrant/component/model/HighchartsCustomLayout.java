package com.yx.egrant.component.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**.
 * 图表首页显示自定义
* @version $Rev$ $Date$
 */
@Entity
@Table(name = "HIGHCHARTS_CUSTOM_LAYOUT")
public class HighchartsCustomLayout implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1815654561448634477L;

	@Id
	@Column(name = "ROLEID")
   private Long roleId;
   
	@Column(name = "LAYOUT_LIST")
   private String layoutList;
   
	@Column(name = "LAYOUT_MAIN_HTML")
   private String layoutMainHtml;
   
	@Column(name = "UPDATE_DATE")
   private Date updateDate;
   
	@Column(name = "UPDATE_PSN_CODE")
   private Long updatePsnCode;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getLayoutList() {
		return layoutList;
	}

	public void setLayoutList(String layoutList) {
		this.layoutList = layoutList;
	}

	public String getLayoutMainHtml() {
		return layoutMainHtml;
	}

	public void setLayoutMainHtml(String layoutMainHtml) {
		this.layoutMainHtml = layoutMainHtml;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getUpdatePsnCode() {
		return updatePsnCode;
	}

	public void setUpdatePsnCode(Long updatePsnCode) {
		this.updatePsnCode = updatePsnCode;
	}
}
