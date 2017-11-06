package com.iris.egrant.service.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iris.egrant.component.dao.HighChartsCustomLayoutDao;
import com.iris.egrant.component.dao.HighChartsQueryDao;
import com.iris.egrant.component.model.HighCharts;
import com.iris.egrant.component.model.HighChartsQuery;
import com.iris.egrant.component.model.HighChartsRoleMapper;
import com.iris.egrant.component.model.HighchartsCommon;
import com.iris.egrant.component.model.HighchartsCustomLayout;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import util.IrisStringUtils;
import util.JsonUtils;
import util.ScriptEngineUtils;

 

/**
 * highcharts
 * @author zhangrl
 * @creatDate 2014-08-02
 */
@Service("highchartsService")
@Transactional(rollbackFor = Exception.class)
public class HighchartsServiceImpl implements HighchartsService {

	@Autowired
	private HighchartsEhcacheService highchartsEhcacheService;
	@Autowired
	public HighChartsCustomLayoutDao highLayoutDao;
	
	@Autowired
	private HighChartsQueryDao highChartQueryDao;
	
	@Override
	public List<HighCharts> getHighChartsByConditions(Map<String, Object> params) {
		List<HighCharts>  chartsList = null;
// 		setNormalParams(params);
 		//按keys查找
 		if(params.get("keys") != null && !"".equals(params.get("keys")))
 		{
 			chartsList = getHighChartsByKeys(params.get("keys").toString());
 		}else if((params.get("keys") != null && !"".equals(params.get("keys"))) || (params.get("isHomepage") != null && !"".equals(params.get("isHomepage"))))
 		{
 			chartsList = getHighChartsByKeys(getCurrentRoleChartsByisHome(params.get("isHomepage").toString()));
 		}
 		//查找当前角色的全部
 		else
 		{
 				chartsList = getHighChartsByKeys(getAllCurrentRoleCharts());
 		}
 		//Session session = this.highChartsDao.getSessionFactory().getCurrentSession();
 		
 		if(chartsList != null && chartsList.size() > 0)
		{
			//遍历list,替换数据
			for(HighCharts chart: chartsList)
			{
				//取处数据json对象，替换相应的data数据
				String newChartDataStr = queryData(chart.getChartDataObjs(), chart.getHcQueries(), chart.getChartType(), params);
				chart.setChartDataObjs(newChartDataStr);
				chart.setHcQueries(null);
			}
		}
 		//清空缓存，避免数据库更新数据字段
 		//session.clear();
		return chartsList;
	}

	@Override
	public HighchartsCustomLayout getCustomLayout() {
		return this.highLayoutDao.get(210L);
	}
	 
	  /**
     * 从缓存中获取需要的图表信息
     */
    private List<HighCharts> getHighchartsBykeys(List<Long> keysList)
    {
    	List<HighCharts> dataList = new ArrayList<HighCharts>();	
    	Long[] chartsIds = new Long[keysList.size()];
    	for(int i=0; i< keysList.size();  i++)
    	{
    		chartsIds[i]= keysList.get(i);
    	}
    	Map<String, HighCharts> map = this.highchartsEhcacheService.getHighchartsFromEhCache(chartsIds);
    	if(map != null && !map.isEmpty())
    	{
    		for(String key: map.keySet())
    		{
    			dataList.add(map.get(key));
    		}
    	}
    	return dataList;
    }

	@Override
	public String getRootUrl() {
 		return "123213";
	}
	
	 
	   
	   /**
	    * 判断当前用户拥有的所有图表(角色拥有，或个人拥有)
	    * @return
	    */
	   private List<Long> getCurrentRoleChartsByisHome(String isHomePage)
	   {
		   List<Long> chartIds = null;
		   Integer currentRoleId = 210;
		   Long currentPsnId = 1090030463L;
		   List<HighChartsRoleMapper> crmList = getgetCurrentRoleOrPersonCharts(currentRoleId,currentPsnId);
		   if(crmList != null && !crmList.isEmpty())
		    {
			   chartIds = new ArrayList<Long>();
		    	for(HighChartsRoleMapper chartRole: crmList)
		    	{
		    		//当前用户不再排除人之内
		    		if(!StringUtils.contains(chartRole.getExclusionPsnCodes(), isHomePage+""))
		    		{
		    				chartIds.add(chartRole.getId().getChartsId());
		    		}
		    		
		    		//当前用户不再排除人之内
		    		if(!StringUtils.contains(chartRole.getExclusionPsnCodes(), currentPsnId+""))
		    		{
		    			//指定用户为空并且是当前角色，或当前用户在指定用户列表中
		    			if((StringUtils.isBlank(chartRole.getIncludePsnCodes()) && chartRole.getId().getRoleId().intValue() == currentRoleId) || 
		    					StringUtils.contains(chartRole.getIncludePsnCodes(), currentPsnId+""))
		    			{
		    				chartIds.add(chartRole.getId().getChartsId());
		    			}
		    		}
		    	}
		    }
		   return chartIds;
	   }
	   
	   
	   /**
	    *  从缓存重获取图表和人或角色的关系实体
	    */
	    private List<HighChartsRoleMapper> getgetCurrentRoleOrPersonCharts(Integer roleId, Long psnCode)
	    {
	    	List<HighChartsRoleMapper> dataList = null;
	    	//获取当前角色对应的图表实体映射
	    	Map<String, HighChartsRoleMapper> map1 = this.highchartsEhcacheService.getHighChartsRoleMapperFromEhCache(roleId);
	    	//获取当前人对应的图表实体映射
	    	Map<String, HighChartsRoleMapper> map2 = this.highchartsEhcacheService.getHighChartsRoleMapperFromEhCache(psnCode);
	      if(map1 != null && !map1.isEmpty() && !this.highchartsEhcacheService.isExistsHighchartsWithRoleId(roleId))
	      	{
	    	  dataList = new ArrayList<HighChartsRoleMapper>();
	    	  for(String key: map1.keySet())
	     	  {
	     		  dataList.add(map1.get(key));
	     	  }
	      	}
	       if(map2 != null && !map2.isEmpty())
	        {
	    	   if(dataList == null)
	    	   {
	    		   dataList = new ArrayList<HighChartsRoleMapper>();
	    	   }
	    	   for(String key: map2.keySet())
	     	  {
	     		  dataList.add(map2.get(key));
	     	  }
	        }
	       return dataList;
	    }
	    
	    /**
		 *  饼图替换数据的方法
		 * @param chartsData
		 * @param chartId
		 * @return
		 */
		private String queryData(String chartsData, Set<HighChartsQuery> hcQuerys, String chartType, Map<String, Object> params) 
		{  
			JSONObject json = null;
			if(JsonUtils.isJson(chartsData))
			{
				json = (JSONObject) JSONSerializer.toJSON(chartsData);
				JSONObject jso = json.getJSONObject("options").getJSONObject("series");
				if(jso.isNullObject())
				{
					return "{}";
				}
				List<Map<String, Object>> listData = getDataByKeys(hcQuerys, chartType, params);
				//所有状态的值都为0默认表示没数据
				/*if(isNodata(listData))
				{
					return "{}";
				}*/
				List<String> keys = new ArrayList<String>();
				keys.add("sql");
				setNullByCondition(jso, keys);
				chartsData = jso.toString();
				if(listData != null && listData.size() > 0)
				{
					for(Map<String, Object> map: listData)
					{
						for(String key: map.keySet())
						{
							String count = map.get(key).toString();
							chartsData = chartsData.replace("\"[@" + key + "@]\"", count).replace("[@" + key + "@]", count);
						}
					}
				}
				//格式化数据格式
				chartsData = formatData(chartsData, chartType);
				
			}
			return chartsData;
		}	
		
		private String formatData(String chartsData, String chartsType)
		{
			if(JsonUtils.isJson(chartsData))
			{
				if(chartsType.equals(HighchartsCommon.LINE) || chartsType.equals(HighchartsCommon.ZHUZHUANG))
				{
					JSONObject json = (JSONObject) JSONSerializer.toJSON(chartsData);
					JSONArray arrays = json.getJSONArray("data");
					
					chartsData = arrays.toString().replace("y", "data");
					chartsData = "{\"series\":" +chartsData + "}";
				}
				else if(chartsType.equals(HighchartsCommon.PIE))
				{
					chartsData = "{\"series\":[" +chartsData + "]}";
				}
			}
			return chartsData;
		}
		
	   
		/**
		 *  修改指定key的值
		 * @param keys
		 * @return
		 */
		private JSONObject setNullByCondition(JSONObject json, List<String> keys)
		{
			if(keys != null && keys.isEmpty())
			{
				return json;
			}
			if(json == null)
			{
				return json;
			}
			JSONArray arrays = json.getJSONArray("data");
			JSONArray returnArray = new JSONArray();
			for(int i = 1;i < arrays.size(); i++)
			{
					if(arrays.get(i) instanceof JSONObject)
					{
						JSONObject j = (JSONObject)arrays.get(i);
						//将集合里给出的key的value置为空
						for(String key: keys)
						{
							j.element(key, "");
						}
						returnArray.add(j);
					}
				}
			json.put("data", returnArray);
			return json;
		}
		/**
		    *  获得每个状态的数据
		    * @param chartId 图表Id
		    * @param keys 需要执行的sql的key的集合&lt;
		    * @return
		    */
		   public List<Map<String, Object>> getDataByKeys(Set<HighChartsQuery> hcQuerys, String chartType, Map<String, Object> params)
		   {
			   List<Map<String, Object>> keyDatas = new ArrayList<Map<String, Object>>();
			   Map<String, Object> columData = null;
			   	
			   //饼图的联合查询
			   if(chartType.equals(HighchartsCommon.PIE))
			   	{
				   return getDataByKeys(hcQuerys,params);
			   	}
			   else
				{
				   for(Iterator<HighChartsQuery> iter = hcQuerys.iterator(); iter.hasNext();)
				    {
					   columData = new HashMap<String, Object>();
					   	HighChartsQuery hc = iter.next();
					    List<Object> list = executeDataSql(hc, params);
					   if(null != list)
					   { 
						   if(HighchartsCommon.LINE.equals(chartType) || HighchartsCommon.ZHUZHUANG.equals(chartType))
						   {
							   list = setList(list, chartType);
						   }
						  columData.put(hc.getQueryKey(),list);
						  keyDatas.add(columData);
					   }
				    }
			   }
			   return keyDatas;
		   }
		   
		   /**
		    *  饼图数据连接查询（特例）
		    */
		@SuppressWarnings("unchecked")
		public List<Map<String, Object>> getDataByKeys(Set<HighChartsQuery> hcQuerys, Map<String, Object> params)
		   {
			   List<Map<String, Object>> keyDatas = new ArrayList<Map<String, Object>>();
			   Map<String, Object> columData = null;
			   List<String> keys = new ArrayList<String>();
			   
			   Set<HighChartsQuery> hqSet = hcQuerys;
			   //查询所有的key的sql，并将他们拼接成连接查询执行一次
			   if(hqSet != null && !hqSet.isEmpty())
			   {
				   Object[] hqArray = hqSet.toArray();
				   StringBuffer unionSql = new StringBuffer();
				  for(int i = 0; i < hqArray.length;i++)
				   {
					    HighChartsQuery hq = (HighChartsQuery)hqArray[i];
					   //取sql
					   String dataSql = hq.getSql();
					   String condStr = hq.getSqlParams();
					   if(!StringUtils.isBlank(condStr))
					    {
					    	String condSql = ScriptEngineUtils.exectureScriptByJavaScript(condStr, params);
					    	dataSql += (StringUtils.isBlank(condSql) || condSql.equals("null")?"":condSql);
					    }
					    //替换系统参数
					   dataSql = builderSql(dataSql, params);
					   unionSql.append(dataSql);
					   if(i < hqArray.length-1)
					   {
						   unionSql.append(" union all ");
					   }
					   //获得keys值保存list中
					   keys.add(hq.getQueryKey());
				   }
				  //执行sql
				  List<Object> queryDataList = this.highChartQueryDao.getSession().createSQLQuery(unionSql.toString().replaceAll("/&gt;/", ">").replaceAll("/&lt;/", "<")).list();
				  //构造数据key，countValue
				  if(queryDataList != null && !queryDataList.isEmpty())
				  {
					  for(int i=0; i<queryDataList.size(); i++)
					  {
						  columData = new HashMap<String, Object>();
						  columData.put(keys.get(i), queryDataList.get(i));
						  keyDatas.add(columData);
					  }
				 }
					  
			   }
			   return keyDatas;
		   }
		

		   /**
		    *  根据keys获得图表对象集合
		    * @param keys 由key组成的字符串（逗号隔开）
		    * @return 图表对象集合
		    */
		   private List<HighCharts> getHighChartsByKeys(String keys)
		   {
			   List<HighCharts> data = null;
			  if(!StringUtils.isBlank(keys))
			  {
				  String[] ids = keys.split(",");
				  data = new ArrayList<HighCharts>();
				  Map<String, HighCharts> hcMap = this.highchartsEhcacheService.getHighchartsFromEhCache(ids);
				  if(hcMap != null && !hcMap.isEmpty())
				  {
					  for(String key: hcMap.keySet())
					  {
						  data.add(hcMap.get(key));
					  }
				  }
			  }
			  return data;
		   }
		   
		   /**
		    *   获得图表对象列表
		    * @param keysList keys集合
		    * @return 图表集合
		    */
		   private List<HighCharts> getHighChartsByKeys(List<Long> keysList)
		   {
			  List<HighCharts> data = new ArrayList<HighCharts>();
			//通过角色获取角色对应的图表
		  	List<HighCharts> roleHcList = this.highchartsEhcacheService.getHighChartsByRole(210);
		  	if(roleHcList != null && !roleHcList.isEmpty())
		  	{
		  		data.addAll(roleHcList);
		  	}
			  if(keysList != null && !keysList.isEmpty())
			  {
				  List<HighCharts> psnHcList = getHighchartsBykeys(keysList);
				  if(psnHcList != null && !psnHcList.isEmpty())
				  {
					  data.addAll(psnHcList);
				  }
			  }
			  return data;
		   }
		   
		   
		   /**
		    * 判断当前用户拥有的所有图表(角色拥有，或个人拥有)
		    * @return
		    */
		   private List<Long> getAllCurrentRoleCharts()
		   {
			   List<Long> chartIds = null;
			   Integer currentRoleId =210;
			   Long currentPsnId = 1090030463l;
			   List<HighChartsRoleMapper> crmList = getgetCurrentRoleOrPersonCharts(currentRoleId,currentPsnId);
			   if(crmList != null && !crmList.isEmpty())
			    {
				   chartIds = new ArrayList<Long>();
			    	for(HighChartsRoleMapper chartRole: crmList)
			    	{
			    		//当前用户不再排除人之内
			    		if(!org.apache.commons.lang3.StringUtils.contains(chartRole.getExclusionPsnCodes(), currentPsnId+""))
			    		{
			    			//指定用户为空并且是当前角色，或当前用户在指定用户列表中
			    			if((StringUtils.isBlank(chartRole.getIncludePsnCodes()) && chartRole.getId().getRoleId().intValue() == currentRoleId) || 
			    					StringUtils.contains(chartRole.getIncludePsnCodes(), currentPsnId+""))
			    			{
			    				chartIds.add(chartRole.getId().getChartsId());
			    			}
			    		}
			    	}
			    }
			   return chartIds;
		   }

		   /**
		    * 执行每一条sql，获得数据 
		    */
		   @SuppressWarnings("unchecked")
		   public List<Object> executeDataSql(HighChartsQuery hcQuerys, Map<String,Object> params)
		   {
//			   setNormalParams(params);
			   if(hcQuerys != null)
			    {
			    	
			 		   String dataSql = hcQuerys.getSql();
					   String condStr = hcQuerys.getSqlParams();
					   if(!StringUtils.isBlank(condStr))
					    {
					    	String condSql = ScriptEngineUtils.exectureScriptByJavaScript(condStr, params);
					    	dataSql += (StringUtils.isBlank(condSql) || condSql.equals("null")?"":condSql);
					    }
					    //替换系统参数
					   dataSql = builderSql(dataSql, params);
					  return this.highChartQueryDao.getSession().createSQLQuery(dataSql.replaceAll("/&gt;/", ">").replaceAll("/&lt;/", "<")).list();
		     }
			   return null;
		   }
		   
		   /**
		    *  构造系统参数
		    * 
		    * @param sql
		    * @param sqlParamMap
		    * @return
		    */
		   private static String builderSql(String sql, Map<String, Object> sqlParamMap) {
				if(StringUtils.isNotBlank(sql)){
					Pattern pattern = Pattern.compile("\\[@[\\w]+@\\]");
					Matcher m = pattern.matcher(sql);
					String key;
					String paramKey;
					while (m.find()) {
						key = m.group();
						paramKey = key.substring(2, key.length() - 2);// 去掉[@ @]
						sql = IrisStringUtils.regexReplaceString(sql, "\\[@" + paramKey + "@\\]", sqlParamMap.get(paramKey) == null ? (sqlParamMap.get(paramKey.toLowerCase()) == null ?  sqlParamMap.get(paramKey.toUpperCase()) : sqlParamMap.get(paramKey.toLowerCase())) : sqlParamMap.get(paramKey) == null ? "" : sqlParamMap.get(paramKey) );
					}	
				}
		       return sql;
		   }
		   
		   /**
		    *  折线图和柱状图对于为零的情况补全
		    * @param list
		    * @return
		    */
		  private List<Object> setList(List<Object> list, String chartType)
		   {
			   List<Object> objList = new ArrayList<Object>();
			   for(int i = 0; i < 12; i++)
			   {
				   Object o = 0;
				   if(HighchartsCommon.ZHUZHUANG.equals(chartType))
				    {
				    	o = null;
				    }
				  for(Object obj: list)
				  {
					 
					  if(obj instanceof Object[])
					  {
						  Object[] perData = (Object[])obj;
						  if(perData[1] != null){
							  if(perData[1].toString().equals((i+1)+""))
							  {
								  o = perData[0]/*.toString()+perData[3].toString()*/;
							  }
						  }
					  }
				  }
				  objList.add(o);
			   }
			   return objList;
		   }
}

