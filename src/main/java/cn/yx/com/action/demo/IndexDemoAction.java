package cn.yx.com.action.demo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yx.com.service.demo.IndexService;

@Controller
public class IndexDemoAction {
	
	@Autowired
	IndexService indexService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexDemoAction.class);

	
	/**
	 * 数据展示
	 * @return
	 */
	@RequestMapping(value="/indexDemo")
	public String indexDemo(HttpServletRequest request){
		LOGGER.info("进入action");
		Map<String, Object> data = indexService.getData();
		request.setAttribute("data", data); 
		return "index/showdata" ;
	}

	@RequestMapping(value="/showFileup")
	public String showFileup(){
		return "fileUp/showFileup";
	}
	@RequestMapping(value="/fileUpBatch")
	public String fileUpBatch(){
		return "fileUp/showFileupBatch";
	}
	
}
