package com.iris.egrant.orm.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.QueryTranslator;
import org.hibernate.hql.QueryTranslatorFactory;
import org.hibernate.hql.ast.ASTQueryTranslatorFactory;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.CharacterType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.InterruptibleBatchPreparedStatementSetter;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;

import util.ReflectionUtils;
 

/**
 * 封装带分页功能的Hibernat泛型基类.
 * 
 * 扩展功能包括分页查询,按属性过滤条件列表查询. 可在Service层直接使用,也可以扩展泛型DAO子类使用,见两个构造函数的注释.
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <PK>
 *            主键类型
 * 
 * 
 */
public class HibernateDao<T, PK extends Serializable> extends SimpleHibernateDao<T, PK> {
	/**
	 * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends HibernateDao<User, Long>{ }
	 */
	public HibernateDao() {
		super();
	}

	/**
	 * 用于省略Dao层, Service层直接使用通用HibernateDao的构造函数. 在构造函数中定义对象类型Class. eg. HibernateDao<User, Long> userDao = new
	 * HibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public HibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	// 分页查询函数 //

 
  

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		Long count = 0L;
		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			count = findUnique(countHql, values);
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
		return count;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, Object> values) {
		Long count = 0L;
		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			count = findUnique(countHql, values);
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}

		return count;
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected int countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		int totalCount = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}

	// -- 属性过滤条件(PropertyFilter)查询函数 --//

 

  
	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return object == null;
	}

	/**
	 * 仿spring JdbcTemplate 的 queryForList 方法.
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryForList(String sql, Object[] objects) {
		Session session = this.getSession();

		SQLQuery sqlQuery = (SQLQuery) session.createSQLQuery(sql).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);

		if (objects != null) {
			sqlQuery.setParameters(objects, this.findTypes(objects));
		}

		return sqlQuery.list();
	}

	/**
	 * 仿spring JdbcTemplate 的 queryForList 方法.
	 * 
	 * @author lineshow created on 2011-12-8
	 * @param sql
	 * @param Map
	 *            <String,Object> objects
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List queryForListByMap(String sql, Map<String, Object> objects) {
		Session session = this.getSession();

		SQLQuery sqlQuery = (SQLQuery) session.createSQLQuery(sql).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);

		if (objects != null && !objects.isEmpty()) {
			sqlQuery.setProperties(objects);
		}

		return sqlQuery.list();
	}

	/**
	 * 仿spring JdbcTemplate 的 queryForList 方法.
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryForList(String sql) {
		return this.queryForList(sql, null);
	}

	/**
	 * 仿spring JdbcTemplate 的 queryForInt 方法.
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int queryForInt(String sql, Object[] objects) {
		Session session = this.getSession();

		SQLQuery sqlQuery = session.createSQLQuery(sql);

		if (objects != null) {
			sqlQuery.setParameters(objects, this.findTypes(objects));
		}
		Object obj = sqlQuery.uniqueResult();
		String rsValue = "";
		if (obj instanceof Map) {
			Map result = (Map) obj;
			for (Iterator iterator = result.keySet().iterator(); iterator.hasNext();) {
				Object key = iterator.next();
				rsValue = ObjectUtils.toString(result.get(key));
			}
		} else {
			rsValue = ObjectUtils.toString(obj);
		}
		return NumberUtils.toInt(rsValue);
	}

	/**
	 * 仿spring JdbcTemplate 的 queryForInt 方法.
	 * 
	 * @param sql
	 * @return
	 */
	public int queryForInt(String sql) {

		return this.queryForInt(sql, null);
	}

	/**
	 * 仿spring JdbcTemplate 的 queryForLong 方法.
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public long queryForLong(String sql, Object[] objects) {
		Session session = this.getSession();

		SQLQuery sqlQuery = session.createSQLQuery(sql);

		if (objects != null) {
			sqlQuery.setParameters(objects, this.findTypes(objects));
		}
		Object obj = sqlQuery.uniqueResult();
		String rsValue = "";
		if (obj instanceof Map) {
			Map result = (Map) obj;
			for (Iterator iterator = result.keySet().iterator(); iterator.hasNext();) {
				Object key = iterator.next();
				rsValue = ObjectUtils.toString(result.get(key));
			}
		} else {
			rsValue = ObjectUtils.toString(obj);
		}

		return NumberUtils.toLong(rsValue);
	}

	/**
	 * 仿spring JdbcTemplate 的 queryForLong 方法.
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public long queryForLong(String sql) {

		return this.queryForLong(sql, null);
	}

	/**
	 * 仿spring JdbcTemplate 的 update 方法.
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public int update(String sql, Object[] objects) {
		Session session = this.getSession();

		SQLQuery sqlQuery = session.createSQLQuery(sql);
		if (objects != null) {
			sqlQuery.setParameters(objects, this.findTypes(objects));
		}

		int i = sqlQuery.executeUpdate();

		return i;
	}

	/**
	 * 仿spring JdbcTemplate 的 update 方法.
	 * 
	 * @param sql
	 * @return
	 */
	public int update(String sql) {

		return this.update(sql, null);
	}

	/**
	 * 仿spring JdbcTemplate 的 batchUpdate 方法.
	 * 
	 * @param sql
	 * @param batchPstm
	 * @return
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	public int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss) throws DataAccessException,
			SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("Executing SQL batch update [" + sql + "]");
		}

		return (int[]) execute(sql, new PreparedStatementCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException {
				try {
					int batchSize = pss.getBatchSize();
					InterruptibleBatchPreparedStatementSetter ipss = pss instanceof InterruptibleBatchPreparedStatementSetter ? (InterruptibleBatchPreparedStatementSetter) pss
							: null;
					if (JdbcUtils.supportsBatchUpdates(ps.getConnection())) {
						for (int i = 0; i < batchSize; i++) {
							pss.setValues(ps, i);
							if (ipss != null && ipss.isBatchExhausted(i)) {
								break;
							}
							ps.addBatch();
						}
						return ps.executeBatch();
					} else {
						List rowsAffected = new ArrayList();
						for (int i = 0; i < batchSize; i++) {
							pss.setValues(ps, i);
							if (ipss != null && ipss.isBatchExhausted(i)) {
								break;
							}
							rowsAffected.add(new Integer(ps.executeUpdate()));
						}
						int[] rowsAffectedArray = new int[rowsAffected.size()];
						for (int i = 0; i < rowsAffectedArray.length; i++) {
							rowsAffectedArray[i] = ((Integer) rowsAffected.get(i)).intValue();
						}
						return rowsAffectedArray;
					}
				} finally {
					if (pss instanceof ParameterDisposer) {
						((ParameterDisposer) pss).cleanupParameters();
					}
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private Object execute(String sql, PreparedStatementCallback action) throws DataAccessException, SQLException {
		return execute(new SimplePreparedStatementCreator(sql), action);
	}

	/**
	 * Simple adapter for PreparedStatementCreator, allowing to use a plain SQL statement.
	 */
	private static class SimplePreparedStatementCreator implements PreparedStatementCreator, SqlProvider {

		private final String sql;

		public SimplePreparedStatementCreator(String sql) {
			Assert.notNull(sql, "SQL must not be null");
			this.sql = sql;
		}

		@Override
		public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			return con.prepareStatement(this.sql);
		}

		@Override
		public String getSql() {
			return this.sql;
		}
	}

	// -------------------------------------------------------------------------
	// Methods dealing with prepared statements
	// -------------------------------------------------------------------------

	@SuppressWarnings({ "rawtypes", "deprecation" })
	private Object execute(PreparedStatementCreator psc, PreparedStatementCallback action) throws DataAccessException,
			SQLException {

		Assert.notNull(psc, "PreparedStatementCreator must not be null");
		Assert.notNull(action, "Callback object must not be null");
		Session session = super.getSession();
		Connection con = session.connection();
		PreparedStatement ps = null;
		try {
			// Connection conToUse = con;
			// if (this.nativeJdbcExtractor != null
			// && this.nativeJdbcExtractor.isNativeConnectionNecessaryForNativePreparedStatements()) {
			// conToUse = this.nativeJdbcExtractor.getNativeConnection(con);
			// }
			ps = psc.createPreparedStatement(con);
			// applyStatementSettings(ps);
			// PreparedStatement psToUse = ps;
			// if (this.nativeJdbcExtractor != null) {
			// psToUse = this.nativeJdbcExtractor.getNativePreparedStatement(ps);
			// }
			Object result = action.doInPreparedStatement(ps);
			// handleWarnings(ps.getWarnings());
			return result;
		} catch (SQLException ex) {
			// Release Connection early, to avoid potential connection pool deadlock
			// in the case when the exception translator hasn't been initialized yet.
			if (psc instanceof ParameterDisposer) {
				((ParameterDisposer) psc).cleanupParameters();
			}
			// String sql = getSql(psc);

			psc = null;
			// JdbcUtils.closeStatement(ps);
			ps.close();
			ps = null;
			// DataSourceUtils.releaseConnection(con, getDataSource());
			con.close();
			con = null;
			throw new SQLException(ex.getMessage() + "批处理失败");
		} finally {
			if (psc instanceof ParameterDisposer) {
				((ParameterDisposer) psc).cleanupParameters();
			}
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}

		}
	}

	/**
	 * 仿spring JdbcTemplate 的 batchUpdate 方法.
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int[] batchUpdate(final String[] sql) throws SQLException {

		Assert.notEmpty(sql, "SQL array must not be empty");
		if (logger.isDebugEnabled()) {
			logger.debug("Executing SQL batch update of " + sql.length + " statements");
		}
		/**
		 * 批量更新参数处理内部类.
		 * 
		 */
		@SuppressWarnings("rawtypes")
		class BatchUpdateStatementCallback implements StatementCallback, SqlProvider {
			private String currSql;

			@Override
			public Object doInStatement(Statement stmt) throws SQLException, DataAccessException {
				int[] rowsAffected = new int[sql.length];
				if (JdbcUtils.supportsBatchUpdates(stmt.getConnection())) {
					for (String element : sql) {
						this.currSql = element;
						stmt.addBatch(element);
					}
					rowsAffected = stmt.executeBatch();
				} else {
					for (int i = 0; i < sql.length; i++) {
						this.currSql = sql[i];
						if (!stmt.execute(sql[i])) {
							rowsAffected[i] = stmt.getUpdateCount();
						} else {
							throw new InvalidDataAccessApiUsageException("Invalid batch SQL statement: " + sql[i]);
						}
					}
				}
				return rowsAffected;
			}

			@Override
			public String getSql() {
				return currSql;
			}
		}
		try {
			return (int[]) execute(new BatchUpdateStatementCallback());
		} catch (DataAccessException e) {
			throw new SQLException(e.getMessage() + "批处理SQL失败");
		} catch (SQLException e) {
			throw new SQLException(e.getMessage() + "批处理SQL失败");
		}
	}

	// -------------------------------------------------------------------------
	// Methods dealing with a plain java.sql.Connection
	// -------------------------------------------------------------------------

	@SuppressWarnings({ "rawtypes", "deprecation" })
	private Object execute(StatementCallback action) throws DataAccessException, SQLException {
		Assert.notNull(action, "Callback object must not be null");
		Session session = super.getSession();
		Connection con = session.connection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			// applyStatementSettings(stmt);
			// Statement stmtToUse = stmt;
			// if (this.nativeJdbcExtractor != null) {
			// stmtToUse = this.nativeJdbcExtractor.getNativeStatement(stmt);
			// }
			Object result = action.doInStatement(stmt);
			// handleWarnings(stmt.getWarnings());
			return result;
		} catch (SQLException ex) {
			// Release Connection early, to avoid potential connection pool deadlock
			// in the case when the exception translator hasn't been initialized yet.
			stmt.close();
			stmt = null;
			con.close();
			con = null;
			throw new SQLException(ex.getMessage() + "批处理SQL失败");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}

		}

	}

	/**
	 * 构造数据集检索排序部分.
	 * 
	 * @param sortFields
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getSortSqlPart(Map sortFields, String tableAlias) {
		Set allKeys = sortFields.keySet();
		Iterator itor = allKeys.iterator();
		String temp = "";
		while (itor.hasNext()) {
			String key = String.valueOf(itor.next());
			String value = String.valueOf(sortFields.get(key));
			// extremeCompontent
			if ("default".equalsIgnoreCase(value)) {
				value = "asc";
			}
			temp += "," + tableAlias + "." + key + " " + value + "  nulls last ";
		}
		if (!"".equals(temp)) {
			temp = temp.substring(1) + ",";
		}
		return temp;
	}

	 

	// 拼类型，所有类型都当字符串处理
	protected Type[] findTypes(Object[] objects) {
		List<Type> list = new ArrayList<Type>();
		for (Object object : objects) {
			if (object instanceof Integer) {
				list.add(new IntegerType());
			} else if (object instanceof Long) {
				list.add(new LongType());
			} else if (object instanceof BigDecimal) {
				list.add(new BigDecimalType());
			} else if (object instanceof Character) {
				list.add(new CharacterType());
			} else if (object instanceof Double) {
				list.add(new DoubleType());
			} else if (object instanceof Date) {
				list.add(new DateType());
			} else {
				list.add(new StringType());
			}
		}
		return list.toArray(new Type[] {});
	}

	/**
	 * translate hql into sql.
	 * 
	 * @param hql
	 * @return sql
	 */
	public String hql2Sql(String hql) {
		if (hql != null && hql.trim().length() > 0) {
			final QueryTranslatorFactory translatorFactory = new ASTQueryTranslatorFactory();
			final SessionFactoryImplementor factory = (SessionFactoryImplementor) sessionFactory;
			final QueryTranslator translator = translatorFactory.createQueryTranslator(hql, hql, Collections.EMPTY_MAP,
					factory);
			translator.compile(Collections.EMPTY_MAP, false);
			return translator.getSQLString();
		}
		return null;
	}

	/**
	 * fetch the data which displayed in grid of page.
	 * 
	 * @author lineshow created on 2011-7-16 | modified on 2011-12-12
	 * @param hql
	 * @param paramList
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getGridDataInQl(String hql, List<Object> paramList, Integer startIndex,
			Integer endIndex) {
		Query query = createQuery(hql, paramList).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		/** keep queried data from being tampered **/
		query.setReadOnly(true);
		if (startIndex != null) {
			query.setFirstResult(startIndex);
		}
		if (endIndex != null) {
			query.setMaxResults(endIndex);
		}
		return query.list();
	}

	/**
	 * fetch the data which displayed in grid of page.
	 * 
	 * @author lineshow created on 2011-7-16 | modified on 2011-12-12
	 * @param hql
	 * @param paramMap
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getGridDataInQl(String hql, Map<String, Object> paramMap, Integer startIndex,
			Integer endIndex) {
		Query query = this.getSession().createQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		/** keep queried data from being tampered **/
		query.setReadOnly(true);
		query.setProperties(paramMap);
		if (startIndex != null) {
			query.setFirstResult(startIndex);
		}
		if (endIndex != null) {
			query.setMaxResults(endIndex);
		}
		return query.list();
	}

	/**
	 * fetch the data which displayed in grid of page.
	 * 
	 * @author lineshow created on 2011-7-16 | modified on 2011-12-12
	 * @param sql
	 * @param paramList
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getGridDataInSql(String sql, List<Object> paramList, Integer startIndex,
			Integer endIndex) {
		Query query = createSqlQuery(sql, paramList).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		query.setReadOnly(true);
		if (startIndex != null) {
			query.setFirstResult(startIndex);
		}
		if (endIndex != null) {
			query.setMaxResults(endIndex);
		}
		return query.list();
	}

	/**
	 * fetch the data which will displayed in grid of page.
	 * 
	 * @author lineshow created on 2011-7-16 | modified on 2011-12-12
	 * @param sql
	 * @param paramMap
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getGridDataInSql(String sql, Map<String, Object> paramMap, Integer startIndex,
			Integer endIndex) {
		Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		query.setReadOnly(true);
		query.setProperties(paramMap);
		if (startIndex != null) {
			query.setFirstResult(startIndex);
		}
		if (endIndex != null) {
			query.setMaxResults(endIndex-startIndex);
		}
		return query.list();
	}

	public Query createQuery(String hql, List<Object> paramList) {
		return createQuery(hql, paramList.toArray());
	}

	public SQLQuery createSqlQuery(String sql, List<Object> paramList) {
		SQLQuery query = getSession().createSQLQuery(sql);
		Iterator<Object> iterator = paramList.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			query.setParameter(i++, iterator.next());
		}
		return query;
	}
 
 

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDataList(String sql, Map<String, Object> paramMap) {
		Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		query.setReadOnly(true);
		query.setProperties(paramMap);
		return query.list();
	}
}
