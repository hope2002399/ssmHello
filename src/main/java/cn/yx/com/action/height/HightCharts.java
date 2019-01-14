package cn.yx.com.action.height;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HightCharts {
	
	
	@RequestMapping(value="/showHeightcharts")
	 public String showHeightcharts(){
		return "/hightCharts/hightCharts";
		 
	 }
}
