package cn.yx.com.service.echarts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yx.egrant.component.dao.PieChartDao;
import com.yx.egrant.component.model.PieChart;

import util.SqlConverUtils;
@Service(value="piechartService")
@Transactional(rollbackFor = Exception.class)
public class PieChartServiceImpl implements PieChartService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PieChartDao pieChartDao;
	
	@Override
	public List<String> findPieChartDataById(String id, Map value) {
		List<String> result = new ArrayList<String>();
		List<Map<String, Object>> list = null;
		if (StringUtils.isBlank(id)) {
			return null;
		}
		try {
			PieChart pieChart = findPieChartById(id);
			// 优先统计老系统的数据
			if (pieChart != null && !StringUtils.isBlank(pieChart.getOldDataSql())) {
				list = execIsisTotalSql(pieChart.getOldDataSql(), value);
			}else {
				// 新系统查询
				if (pieChart != null && !StringUtils.isBlank(pieChart.getDataSql())) {
					list = execEgrantTotalSql(pieChart.getDataSql(), value);
				}
			}
			if (list != null) {
				for (Map<String, Object> map : list) {
					result.add(map.values().toArray()[0].toString());
				}
			}
		} catch (Exception e) {
			logger.error("统计饼图数据出错,key=" + id, e);
		}
		return result;
	}
	
	/**
	 * 根据sql语句执行新系统统计数据.
	 * 
	 * @param sql
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> execEgrantTotalSql(String sql, Map<String, Object> value)   {
		if (StringUtils.isBlank(sql)) {
			return null;
		}
		List<Object> params = new ArrayList<Object>();
		sql = SqlConverUtils.transSql(sql, value, params);
		return pieChartDao.queryForList(sql, params.toArray());
	}

	/**
	 * 根据sql语句执行老系统统计数据.
	 * 
	 * @param sql
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> execIsisTotalSql(String sql, Map<String, Object> value)   {
		if (StringUtils.isBlank(sql)) {
			return null;
		}
		List<Object> params = new ArrayList<Object>();
		sql = SqlConverUtils.transSql(sql, value, params);
		// TODO:service中不允许执行SQL，请移植到DAO中(修改好了把TODO删除)lqh add.
		return pieChartDao.queryForList(sql, params.toArray());
	}
	@Override
	public PieChart findPieChartById(String id) {
		return pieChartDao.get(id);
	}

}
