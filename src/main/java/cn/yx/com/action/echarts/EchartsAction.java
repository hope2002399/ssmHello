package cn.yx.com.action.echarts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yx.com.service.echarts.PieChartService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.JsonUtils;
import util.ServletUtils;
 
@Controller
public class EchartsAction {

	@Autowired
	private PieChartService pieChartService ;
		
	@RequestMapping(value="/showEcharts")
	public  String  echartTest(){
		
  
		return "echarts/showEcharts" ;
	}
	@RequestMapping(value="/cpt/ajaxload-piechart")
	public String pieChart(HttpServletRequest req ,HttpServletResponse response) {
		String id = req.getParameter("id");
		String sqlParamVal = req.getParameter("sqlParamVal");
		if (!StringUtils.isBlank(id)) {

			List<String> result = null;// (List<String>) componentWebCache.get(getPiechartKey() + "_" + id);

			if (result == null) {
				 
				Map<String, Object> map = new HashMap<String, Object>();
			 
			 
				if (!StringUtils.isBlank(sqlParamVal)) {
					Map map2 = JsonUtils.getMap4Json(sqlParamVal);
					map.putAll(map2);
				}
				result = pieChartService.findPieChartDataById(id, map);
				 
			}
			 
			 response.setCharacterEncoding("UTF-8");  
			 response.setContentType("application/json; charset=utf-8"); 
			 //将实体对象转换为JSON Object转换  
			 JSONArray responseJSONObject =   JSONArray.fromObject(result ) ;  
			 PrintWriter out = null;  
			    try {  
			        out = response.getWriter();  
			        out.append(responseJSONObject.toString());  
			         
			    } catch (IOException e) {  
			        e.printStackTrace();  
			    } finally {  
			        if (out != null) {  
			            out.close();  
			        }  
			    }  
			 
 		}
		return null;
	}
	
}
