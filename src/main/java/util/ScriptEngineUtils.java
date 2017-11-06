package util;

import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
/**
 * @author changsheng
 * @see 动态脚本执行帮助类
 * */
public class ScriptEngineUtils {
	//生成引擎  
   private static ScriptEngineManager manager = null;  
	
   private static void install(){
	   if(manager==null)
	   manager = new ScriptEngineManager();
   }
   
   /**
    * @param script javaScript脚本
    * @param params 参数Map
    * @return 脚本执行结果 （String）
    * */
   public static String exectureScriptByJavaScript(String script,Map<String,Object> params){
	   try {
	   install();
	   ScriptEngine engine  = manager.getEngineByName("JavaScript");
	   Bindings bindingsContext = engine.createBindings();  
	   if(params!=null && !params.isEmpty()){
		   bindingsContext.putAll(params);
	    } 
		Object result = engine.eval(script,bindingsContext);
		return String.valueOf(result);
	   } catch (ScriptException e) {
		 e.printStackTrace();
		 return null;
	   }  
	   
   }
   

}
