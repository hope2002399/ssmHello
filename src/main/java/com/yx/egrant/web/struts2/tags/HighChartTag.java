package com.yx.egrant.web.struts2.tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yx.egrant.component.model.HighCharts;
import com.yx.egrant.component.model.HighchartsCustomLayout;
import com.yx.egrant.service.stat.HighchartsService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import util.JsonUtils;

/**
 * highCharts饼图标签
 * 
 * 
 * 
 */

public class HighChartTag extends BodyTagSupport {

	private static final long serialVersionUID = 1077194066541229822L;
	
	//显示特定的keys的饼图(key用逗号隔开)
	private String keys ="";
	
	private String divIds;
	
	private String afterDoFun;
	
	private String beforeDoFun;
	//默认年度
	private String defaultYear;
	
	//折线图转换为饼图标识
	private String isHomepage;
	
 
	
	@Override
	public int doStartTag() throws JspException {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(pageContext.getServletContext());
//		ServiceFactory serviceFactory = (ServiceFactory) ctx.getBean("serviceFactory");
		Configuration cptFreemarkereConfiguration = (Configuration) ctx.getBean("cptFreemarkereConfiguration");

		/*ResourceBundleMessageSource messageSource = (ResourceBundleMessageSource) ctx.getBean("messageSource");*/
		try {
			
			/*String res = (String) pageContext.getAttribute("res");*/
			Map<String, Object> rootMap = new HashMap<String, Object>();
			
			//业务当前年度
			String businessYear =2017+"";
			if(StringUtils.isBlank(businessYear))
			{
				 
			}
			//构造参数
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("keys", keys);
			params.put("defaultKeys", "");
			params.put("grant_code"," ");
			
			params.put("org_code", 9000005926L);
			params.put("role_id", 210L);
			params.put("psn_code", 1090030463L);
			//params.put("AMT_GRADE", " ");
//			params.put("isHomepage", Struts2Utils.getParameter("isHomepage"));
//			if(Struts2Utils.getParameter("selectYear") != null){
//				params.put("year",Struts2Utils.getParameter("selectYear"));
//			}else{
//				params.put("year", defaultYear==null?businessYear:defaultYear);
//			}
			/*params.put("sortByChartType", sortByChartType);*/
			/*int year = DateUtils.getYear(new Date());
			params.put("year", year);*/
			HighchartsService highchartService = (HighchartsService) ctx.getBean("highchartsService");

			List<HighCharts> dataList = highchartService.getHighChartsByConditions(params);
			HighchartsCustomLayout layout = highchartService.getCustomLayout();
			String rootUrl= highchartService.getRootUrl();
			/*if(isHomepage == "1"){
				for (int i = 0; i < dataList.size(); i++) {
					if(dataList.get(i).getChartType().equals("1")){
						dataList.remove(i);
					}
				}
			}*/
			String divs = null;
			if(layout != null){
				divs = layout.getLayoutMainHtml();
			}
			String dataStr = JsonUtils.FormatToJsonstr(dataList);
			if(dataStr.equals("[]"))
			{
				dataStr = "";
			}

			rootMap.put("dataList", dataStr);
			rootMap.put("divIds", divIds==null?"":divIds);
			rootMap.put("divs", divs == null?"":divs);
			rootMap.put("rootUrl", rootUrl);
			rootMap.put("keys", keys==null?"":keys);
			rootMap.put("beforeDoFun", beforeDoFun==null?"null":beforeDoFun);
			rootMap.put("afterDoFun", afterDoFun==null?"null":afterDoFun);
			rootMap.put("defaultYear", params.get("year")==null?"":params.get("year").toString());
			Template template = cptFreemarkereConfiguration.getTemplate("highChart.ftl", "utf-8");

			template.process(rootMap, pageContext.getOut());

		} catch (Exception e) {
			e.printStackTrace();
			throw new JspException(e);
		}

		return SKIP_BODY;
	}

	public String getAfterDoFun()
	{
		return afterDoFun;
	}



	public void setAfterDoFun(String afterDoFun)
	{
		this.afterDoFun = afterDoFun;
	}



	public String getBeforeDoFun()
	{
		return beforeDoFun;
	}



	public void setBeforeDoFun(String beforeDoFun)
	{
		this.beforeDoFun = beforeDoFun;
	}

	public String getKeys()
	{
		return keys;
	}

	public void setKeys(String keys)
	{
		this.keys = keys;
	}

	public String getDivIds()
	{
		return divIds;
	}

	public void setDivIds(String divIds)
	{
		this.divIds = divIds;
	}

	public String getDefaultYear()
	{
		return defaultYear;
	}

	public void setDefaultYear(String defaultYear)
	{
		
		 this.defaultYear = defaultYear;
	}

	public String getIsHomepage() {
		return isHomepage;
	}

	public void setIsHomepage(String isHomepage) {
		this.isHomepage = isHomepage;
	}
}



