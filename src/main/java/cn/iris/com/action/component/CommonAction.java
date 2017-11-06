package cn.iris.com.action.component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.iris.com.service.echarts.PieChartService;
import net.sf.json.JSONObject;
import util.JsonUtils;
 
/**
 * 基础组件action.	
 * 
 * @author lj
 * 
 */
 @Controller
public class CommonAction  {
 

	protected final Logger logger = LoggerFactory.getLogger(getClass());

   @Autowired
   PieChartService  piechartService ;

 
  
	 
	 

}
