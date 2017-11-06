package cn.iris.com.service.demo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.iris.com.action.demo.IndexDemoAction;
import cn.iris.com.dao.demo.base.IndexDemoMybatisDao;

@Service(value="indexService")
public class IndexServiceImpl implements IndexService {
	
	@Autowired
	IndexDemoMybatisDao indexDemoMybatisDao ;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexDemoAction.class);

	
	@Override
	public Map<String, Object> getData() {
		// TODO Auto-generated method stub
		LOGGER.debug("servicedebug");
		LOGGER.info("servicedebuginfo");
		LOGGER.warn("servicedebugwarn");
		LOGGER.error("servicedebuginfo");
		return  indexDemoMybatisDao.getData();
 	}

	
}
