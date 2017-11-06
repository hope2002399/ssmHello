package com.iris.egrant.web.struts2.tags;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.utility.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import util.JsonUtils;

/**
 * 饼状图标签.
 * 
 * 
 * 
 */
public class PieChartTag extends BodyTagSupport {

	private static final long serialVersionUID = 1077194066541229822L;
	// html中的id标记
	private String id = "";
	// 对应cpt_pie_chart的id
	private String key = "";
	// 数据对象
	private String dataObj = "";// {data:[55, 20, 13, 32, 5,
								// 10],name:["%%.%%-aaaaaa","%%.%%-b","%%.%%-c","%%.%%-d","%%.%%-e","%%.%%-f"],href:[]};
	// 内容对象
	private String contentObj = "";// {x:320,y:240,size:100};
	// 标题对象
	private String titleObj = "";// {x:320,y:100,name:"Interactive Pie Chart"};
	// 国际化
	private String locale = "";
	// 文字显示的位置
	private String direction = "";// 文字内容的位置

	// sql参数值
	private String sqlParamVal = "";

	public String getDataObj() {
		return dataObj;
	}

	public void setDataObj(String dataObj) {
		this.dataObj = dataObj;
	}

	public String getContentObj() {
		return contentObj;
	}

	public void setContentObj(String contentObj) {
		this.contentObj = contentObj;
	}

	public String getTitleObj() {
		return titleObj;
	}

	public void setTitleObj(String titleObj) {
		this.titleObj = titleObj;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getLocale() {
		 
		if (StringUtils.isBlank(locale)){
			return LocaleContextHolder.getLocale().toString();
		}
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public int doStartTag() throws JspException {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(pageContext.getServletContext());
		// ServiceFactory serviceFactory = (ServiceFactory) ctx.getBean("serviceFactory");
		Configuration cptFreemarkereConfiguration = (Configuration) ctx.getBean("cptFreemarkereConfiguration");

		ResourceBundleMessageSource messageSource = (ResourceBundleMessageSource) ctx.getBean("messageSource");
		try {
			 
			if (!StringUtils.isBlank( titleObj)) {
				// json字符串转为map
				Map<String, Object> titleObjMap = JsonUtils.getMap4Json(titleObj);
				String tiltleNameKey = (String) titleObjMap.get("name");
				if (!StringUtils.isBlank(tiltleNameKey)) {
					// 替换国际化
					tiltleNameKey = messageSource.getMessage(tiltleNameKey, null, tiltleNameKey,
							LocaleContextHolder.getLocale());
					// 还原为json字符串
					titleObjMap.put("name", tiltleNameKey);
					titleObj = JsonUtils.getJsonString4JavaPOJO(titleObjMap);
				}

			}

			if (!StringUtils.isBlank(dataObj)) {
				// json字符串转为map
				Map<String, Object> dataObjMap = JsonUtils.getMap4Json(dataObj);
				JSONArray dataObjKeyAry = (JSONArray) dataObjMap.get("name");
				// 替换name
				for (int i = 0; i < dataObjKeyAry.size(); i++) {
					// %%-taglib.pieChart.data5
					String dataObjKey = dataObjKeyAry.getString(i);

					if (!StringUtils.isBlank(dataObjKey)) {

						if (dataObjKey.contains("-")) {
							// 替换国际化
							String dataObjKeyTmp = messageSource.getMessage(
									StringUtils.substringAfter(dataObjKey, "-"), null,
									StringUtils.substringAfter(dataObjKey, "-"), LocaleContextHolder.getLocale());

							dataObjKeyAry.set(i, StringUtils.substringBefore(dataObjKey, "-") + "-" + dataObjKeyTmp);
						} else {
							// 替换国际化
							dataObjKey = messageSource.getMessage(dataObjKey, null, dataObjKey,
									LocaleContextHolder.getLocale());

							dataObjKeyAry.set(i, dataObjKey);
						}

					}

				}
				// 还原为json字符串
				dataObjMap.put("name", dataObjKeyAry);

				// 获取饼图的所有的链接，可能和上面name数组数量不一致
				dataObjKeyAry = (JSONArray) dataObjMap.get("href");
				// 替换name
				for (int i = 0; i < dataObjKeyAry.size(); i++) {
					String dataObjKey = dataObjKeyAry.getString(i);
					if (!StringUtils.isBlank(dataObjKey) && dataObjKey.contains(".asp")) {

						String url = "/Include/cas.asp?roleId=" + 3 + "&target_url="
								+ java.net.URLEncoder.encode(dataObjKeyAry.getString(i).trim(), "UTF-8");
						dataObjKeyAry.set(i, url);
					}

				}
				// 还原为json字符串
				dataObjMap.put("href", dataObjKeyAry);

				dataObj = JsonUtils.getJsonString4JavaPOJO(dataObjMap);

			}

			String res = (String) pageContext.getAttribute("res");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("key", key);
			params.put("id", id);
			params.put("sqlParamVal", sqlParamVal);

			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put("res", res);
			rootMap.put("locale", getLocale());
			rootMap.put("id", id);
			rootMap.put("key", key);
			rootMap.put("params", JSONSerializer.toJSON(params).toString());
			rootMap.put("dataObj", dataObj);
			rootMap.put("contentObj", contentObj);	
			rootMap.put("titleObj", titleObj);
			rootMap.put("direction", direction);

			Template template = cptFreemarkereConfiguration.getTemplate("pieChart.ftl", "utf-8");

			template.process(rootMap, pageContext.getOut());

		} catch (Exception e) {
			throw new JspException(e);
		}

		return SKIP_BODY;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSqlParamVal() {
		return sqlParamVal;
	}

	public void setSqlParamVal(String sqlParamVal) {
		this.sqlParamVal = sqlParamVal;
	}

}
