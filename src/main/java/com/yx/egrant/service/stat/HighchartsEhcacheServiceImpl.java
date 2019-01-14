package com.yx.egrant.service.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yx.egrant.component.dao.HighChartsCustomLayoutDao;
import com.yx.egrant.component.dao.HighChartsDao;
import com.yx.egrant.component.dao.HighChartsRoleMapperDao;
import com.yx.egrant.component.model.HighCharts;
import com.yx.egrant.component.model.HighChartsRoleMapper;
import com.yx.egrant.component.model.HighchartsCommon;
 

/**
 *  HighCharts图表接口实现类
 * @author wen
 *
 */
@Service("highchartsEhcacheService")
@Transactional(rollbackFor = Exception.class)
public class HighchartsEhcacheServiceImpl implements HighchartsEhcacheService
{
	private static final long serialVersionUID = -193812106179512460L;

	@Autowired
	public HighChartsDao highChartsDao;
	
	@Autowired
	public HighChartsCustomLayoutDao highLayoutDao;
	
	@Autowired
	private HighChartsRoleMapperDao highChartsRoleMapperDao;
	
	//highcharts的缓存
	private Ehcache highchartsCache;
	
	

	/**
	 *  初始化数据
	 */
	public void initHighchartsEhcache()
	{
		if(this.highchartsCache.getSize() == 0)
		{
			initHighchartsToEhcache();
			initHighChartsRoleMapperToEhcache();
		}
	}
	
	/**
	 * 清除缓存的方法
	 */
	public void clearHighchartsEhcache()
	{
		this.highchartsCache.removeAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, HighCharts> getHighchartsFromEhCache(String[] chartskeys)
	{
		//初始化缓存
		initHighchartsEhcache();
		Map<String, HighCharts> returnData = null;
		if(chartskeys != null && chartskeys.length > 0)
		{
			Element e = null;
			returnData = new HashMap<String, HighCharts>();
			for(String chartskey: chartskeys)
			{
				List<String> keysList = this.highchartsCache.getKeys();
				
				if(keysList != null && !keysList.isEmpty())
				{
					for(int i = 0; i < keysList.size(); i++)
					{
						String cacheKey = keysList.get(i);
						if(cacheKey.startsWith(chartskey+"#"))
						{
							e = this.highchartsCache.get(cacheKey);
							HighCharts newCloneHighcharts;
							if(e.getValue() instanceof HighCharts)
							{
								try
								{
									newCloneHighcharts = (HighCharts)HighchartsCommon.cloneObject(e.getValue());
									returnData.put(chartskey, newCloneHighcharts);
								} catch (Exception e1)
								{
									e1.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		return returnData;
	}
	
	@Override
	public Map<String, HighCharts> getHighchartsFromEhCache(Long[] chartsIds)
	{
		//初始化缓存
		initHighchartsEhcache();
		return getDataByChartsIDs(chartsIds);
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, HighCharts> getDataByChartsIDs(Long[] chartsIds)
	{
		Map<String, HighCharts> returnData = null;
		if(chartsIds != null && chartsIds.length > 0)
		{
			Element e = null;
			returnData = new HashMap<String, HighCharts>();
			List<String> keysList = this.highchartsCache.getKeys();

			for(int i=0;  i < chartsIds.length; i++)
			{
				if(keysList != null && !keysList.isEmpty())
				{
					for(int j = 0; j < keysList.size(); j++)
					{
						String cacheKey = keysList.get(j);
						if(cacheKey.endsWith("#"+chartsIds[i]))
						{
							e = this.highchartsCache.get(cacheKey);
							HighCharts newCloneHighcharts;
							try
							{
								if(e.getValue() instanceof HighCharts)
								{
									newCloneHighcharts = (HighCharts)HighchartsCommon.cloneObject(e.getValue());
									String chartsKey = cacheKey.split("#")[1];
									returnData.put(chartsKey, newCloneHighcharts);
								}
							} catch (Exception e1)
							{
								e1.printStackTrace();
							}
						}
					}
				}
			}
		}
		return returnData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, HighChartsRoleMapper> getHighChartsRoleMapperFromEhCache(Integer roleId)
	{
		//初始化缓存
		initHighchartsEhcache();
		Map<String, HighChartsRoleMapper> returnData = null;
		if(roleId != null)
		{
			Element e = null;
			   List<String> keysList = this.highchartsCache.getKeys();
					
					if(keysList != null && !keysList.isEmpty())
					{
						returnData = new HashMap<String, HighChartsRoleMapper>();
						for(int i = 0; i < keysList.size(); i++)
						{
							   String cacheKey = keysList.get(i);
							   if(cacheKey.endsWith("#"+ roleId))
							   {
								   e = this.highchartsCache.get(cacheKey);
									HighChartsRoleMapper newCloneRoleMapper;
									try
									{
										if(e.getValue() instanceof HighChartsRoleMapper)
										{
											newCloneRoleMapper = (HighChartsRoleMapper)HighchartsCommon.cloneObject(e.getValue());
											returnData.put(cacheKey, newCloneRoleMapper);
											
										}
									} catch (Exception e1)
									{
										e1.printStackTrace();
									}
							   }
						}
					}
		  
		}
		return returnData;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, HighChartsRoleMapper> getHighChartsRoleMapperFromEhCache(Long psnCode)
	{
		//初始化缓存
		initHighchartsEhcache();
		Map<String, HighChartsRoleMapper> returnData = null;
		if(psnCode != null)
		{
			Element e = null;
		   List<String> keysList = this.highchartsCache.getKeys();
				
				if(keysList != null && !keysList.isEmpty())
				{
					returnData = new HashMap<String, HighChartsRoleMapper>();
					for(int i = 0; i < keysList.size(); i++)
					{
						   String cacheKey = keysList.get(i);
							e = this.highchartsCache.get(cacheKey);
							HighChartsRoleMapper newCloneRoleMapper;
							try
							{
								if(e.getValue() instanceof HighChartsRoleMapper)
								{
									HighChartsRoleMapper tmpMapper = (HighChartsRoleMapper)e.getValue();
									String includePsnCodes = tmpMapper.getIncludePsnCodes();
									//人对应的图表映射实体
									if(!StringUtils.isBlank(includePsnCodes) && includePsnCodes.contains(psnCode+""))
									{
										newCloneRoleMapper = (HighChartsRoleMapper)HighchartsCommon.cloneObject(e.getValue());
										returnData.put(cacheKey, newCloneRoleMapper);
									}
								}
							} catch (Exception e1)
							{
								e1.printStackTrace();
							}
						
					}
				}
			
		}
		return returnData;
	}
	
	/**
	 *  初始化缓存数据（cacheKey为 图表charts_keys标示+"#"+ 图表id）
	 */
	private void initHighchartsToEhcache()
	{
		List<HighCharts> hcList = this.highChartsDao.findAll();
		if(hcList != null && !hcList.isEmpty())
		{
			Element e = null;
			for(HighCharts h: hcList)
			{
				h.setChartAttrsObjs(new XMLSerializer().read(h.getChartAttrsObjs()).toString());
				h.setChartDataObjs(new XMLSerializer().read(h.getChartDataObjs()).toString());
				e = new Element( h.getChartsKey()+"#"+h.getChartsId(), h);
				this.highchartsCache.put(e);
			}
		}
	}
	
	/**
	 *  初始化关系映射到缓存(cacheKey为 图表Id+"#"+角色Id)
	 */
	private void initHighChartsRoleMapperToEhcache()
	{
		List<HighChartsRoleMapper> hcrmList = this.highChartsRoleMapperDao.findAll();
		if(hcrmList != null && !hcrmList.isEmpty())
		{
			Element e = null;
			List<Long> roles = new ArrayList<Long>();
			//保存关系映射实体
			for(HighChartsRoleMapper h: hcrmList)
			{
				Long roleId = h.getId().getRoleId();
				e = new Element(h.getId().getChartsId() + "#" + roleId, h);
				if(!roles.contains(roleId))
				{
					roles.add(roleId);
				}
				this.highchartsCache.put(e);
			}
			//保存角色对应的关系映射实体
			saveChartsByRole(roles, hcrmList);
		}
	}
	
	/**
	 *  保存角色对应的关系映射实体
	 * @param roles
	 * @param roleMapperData
	 */
	private void saveChartsByRole(List<Long> roles, List<HighChartsRoleMapper> roleMapperData)
	{
		if(roles != null && !roles.isEmpty())
		{
			List<Long> chartsIds = null;
			Element e = null;
			for(Long role: roles)
			{
				chartsIds = new ArrayList<Long>();
	
				for(HighChartsRoleMapper hcr: roleMapperData)
				{
					if(hcr.getId().getRoleId().longValue() == role.longValue())
					{
						chartsIds.add(hcr.getId().getChartsId());
					}
				}
				//将role对应的图表信息保存到缓存中(角色，图表对象实体map)
				
				saveChartsToCacheByRole(role, chartsIds);
				/*e = new Element(role+"", rolesHighcarts);
				this.highchartsCache.put(e);
				System.out.println(this.highchartsCache.get(role));*/
			}
		}
	}
	
	/**
	 *  保存角色对应的图表信息到缓存
	 * @param role
	 * @param chartsIds
	 */
	private void saveChartsToCacheByRole(Long role, List<Long> chartsIds)
	{
		Long[] ids = new Long[chartsIds.size()];
		if(chartsIds != null && !chartsIds.isEmpty())
		{
			
			for(int i = 0; i < chartsIds.size(); i++)
			{
				ids[i]=Long.parseLong(chartsIds.get(i).toString());
			}
		}
		Map<String, HighCharts> highchartsMap = getDataByChartsIDs(ids);
		if(highchartsMap != null && !highchartsMap.isEmpty())
		{
			Element e = new Element(role+"", highchartsMap);
			this.highchartsCache.put(e);
		}
	}
	
	public Ehcache getHighchartsCache()
	{
		return highchartsCache;
	}


	public void setHighchartsCache(Ehcache highchartsCache)
	{
		this.highchartsCache = highchartsCache;
	}

	@Override
	public Boolean isExistsHighchartsWithRoleId(Integer roleId)
	{
		Element e = null;
		e = this.highchartsCache.get(roleId+"");
		if(e != null)
		{
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<HighCharts> getHighChartsByRole(Integer roleId)
	{
		//初始化缓存
		initHighchartsEhcache();
		List<HighCharts> returnData = null;
		Element e = null;
		e = this.highchartsCache.get(roleId+"");
		if(e != null)
		{
			Map<String, HighCharts> hcMap = (Map<String, HighCharts>)e.getValue();
			if(hcMap != null && !hcMap.isEmpty())
			{
				returnData = new ArrayList<HighCharts>();
				for(String key: hcMap.keySet())
				{
					HighCharts hc = (HighCharts)hcMap.get(key);
					HighCharts newCloneHighcharts;
					try
					{
						newCloneHighcharts = (HighCharts)HighchartsCommon.cloneObject(hc);
						returnData.add(newCloneHighcharts);
					} catch (Exception e1)
					{
						e1.printStackTrace();
					}
				}
			}
		}
		return returnData;
	}

}
