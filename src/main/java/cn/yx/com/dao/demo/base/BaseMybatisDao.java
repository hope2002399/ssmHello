package cn.yx.com.dao.demo.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.apache.commons.lang3.ObjectUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @param <T>
 * 
 * 
 */
@Repository
public class BaseMybatisDao<T> extends SqlSessionDaoSupport {
 
	/**
	 * ��ѯList
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getSearchList(String queryStr, Object params) {

		List<T> list = new ArrayList<T>();
		try {
			list = getSqlSession().selectList(queryStr, params);
		} catch (Exception e) {
			logger.error("getSearchList()�쳣", e);
			throw new PersistenceException("getSearchList()�쳣", e);
		}
		return list;
	}
	
	/**
	  
	 * 
	 * @param queryStr
	 * @param params 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getSearchListToMap(String queryStr, Object params) {
 
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = getSqlSession().selectList(queryStr, params); 
		} catch (Exception e) {
			logger.error("getSearchList������ֵList<Map<String,Object>>�쳣", e);
		}
		return list;
	}


	 
	/**
	 * ����SQLִ��
	 * 
	 * @param updateStr
	 * @param params
	 * @return
	 */
	public int insert(String updateStr, Map<String, Object> params) {
		int result = 0;
		try {
			result = getSqlSession().insert(updateStr, params);
		} catch (Exception e) {
			logger.error("update()�쳣", e);
			throw new PersistenceException("insert()�쳣", e);
		}
		return result;
	}

	/**
	 * ����SQLִ��
	 * 
	 * @param updateStr
	 * @param params
	 * @return
	 */
	public int update(String updateStr, Map<String, Object> params) {
		int result = 0;
		try {
			result = getSqlSession().update(updateStr, params);
		} catch (Exception e) {
			logger.error("update()�쳣", e);
			throw new PersistenceException("update()�쳣", e);
		}
		return result;
	}

	/**
	 * ɾ��SQLִ��
	 * 
	 * @param updateStr
	 * @param params
	 * @return
	 */
	public int delete(String updateStr, Map<String, Object> params) {
		int result = 0;
		try {
			result = getSqlSession().delete(updateStr, params);
		} catch (Exception e) {
			logger.error("delete()�쳣", e);
			throw new PersistenceException("delete()�쳣", e);
		}
		return result;
	}

	/**
	 * �����������ص�������
	 * 
	 * @param selectStr
	 * @param params
	 * @return
	 */
	public Object getOneInfo(String selectStr, Map<String, Object> params) {
		Object result = null;
		try {
			result = getSqlSession().selectOne(selectStr, params);
		} catch (Exception e) {
			logger.error("select()�쳣", e);
			throw new PersistenceException("select()�쳣", e);
		}
		return result;
	} 
	
	/**
	 * ��������
	 * 
	 * @param countStr
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer getSearchSize(String countStr, Map<String, Object> params) {
		Integer totalCount = 0;
		if (!"".equalsIgnoreCase(countStr)) {
			try {
				List<Map<String, Object>> list = getSqlSession().selectList(countStr, params);
				if (list.size() > 0) {
					totalCount = Integer.parseInt(ObjectUtils.toString(list.get(0).get("totalCount")));
				}
			} catch (Exception e) {
				logger.error("getSearchSize()�쳣", e);
			}
		}
		return totalCount;
	}
	
}
