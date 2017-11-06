package com.iris.egrant.component.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 饼图实体类(统计).
 * 
 * 
 * @version $Rev$ $Date$
 */
@Entity
@Table(name = "CPT_PIE_CHART")
public class PieChart implements Serializable {

	private static final long serialVersionUID = -260510901228330583L;

	/**
	 * id.
	 */
	@Id
	@Column(name = "ID")
	private String id;
	/**
	 * 标题.
	 */
	@Column(name = "TITLE")
	private String title;
	/**
	 * 查询数据的sql语句,此sql语句针对新系统.
	 */
	@Column(name = "DATA_SQL")
	private String dataSql;

	/**
	 * 查询数据的sql语句,此sql语句针对老系统.
	 */
	@Column(name = "OLD_DATA_SQL")
	private String oldDataSql;

	/**
	 * 饼图中每块的名称。多个用逗号分隔.
	 */
	@Column(name = "NAMES")
	private String names;
	/**
	 * 每块对应的链接地址，多个用逗号分隔.
	 */
	@Column(name = "HREFS")
	private String hrefs;
	/**
	 * 每块对应的颜色.
	 */
	@Column(name = "COLORS")
	private String colors;
	/**
	 * 显示的方位.
	 */
	@Column(name = "DIRECTION")
	private String direction;

	/**
	 * 描述备注.
	 */
	@Column(name = "REMARK")
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDataSql() {
		return dataSql;
	}

	public void setDataSql(String dataSql) {
		this.dataSql = dataSql;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getHrefs() {
		return hrefs;
	}

	public void setHrefs(String hrefs) {
		this.hrefs = hrefs;
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOldDataSql() {
		return oldDataSql;
	}

	public void setOldDataSql(String oldDataSql) {
		this.oldDataSql = oldDataSql;
	}

}
