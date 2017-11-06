package util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * 
 * json工具类.
 * 
 * @author lj
 */
public class JsonUtils {

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象.
	 * 
	 * @param jsonString
	 *            　　
	 * @param pojoCalss
	 *            　　
	 * @return 　　
	 */

	@SuppressWarnings("rawtypes")
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * 　　 从json HASH表达式中获取一个map，改map支持嵌套功能.
	 * 
	 * @param jsonString
	 *            　　
	 * @return 　　
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;

	}

	/**
	 * 　　 从json数组中得到相应java数组 .　　
	 * 
	 * @param jsonString
	 *            　　
	 * @return 　　
	 */

	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();
	}

	/**
	 * 　　从json对象集合表达式中得到一个java对象列表 　.　
	 * 
	 * @param jsonString
	 *            　　
	 * @param pojoClass
	 *            　　
	 * @return 　　
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getList4Json(String jsonString, Class pojoClass) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;
		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);
		}
		return list;

	}

	/**
	 * 从json数组中解析出java字符串数组.
	 * 
	 * @param jsonString
	 * 
	 * @return
	 * 
	 *         　　
	 */

	public static String[] getStringArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);

		String[] stringArray = new String[jsonArray.size()];

		for (int i = 0; i < jsonArray.size(); i++) {

			stringArray[i] = jsonArray.getString(i);

		}
		return stringArray;

	}

	/**
	 * 从json数组中解析出javaLong型对象数组.
	 * 
	 * @param jsonString
	 * 
	 * @return
	 * 
	 *         　　
	 */

	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);

		Long[] longArray = new Long[jsonArray.size()];

		for (int i = 0; i < jsonArray.size(); i++) {

			longArray[i] = jsonArray.getLong(i);

		}

		return longArray;

	}

	/**
	 * 从json数组中解析出java Integer型对象数组.
	 * 
	 * @param jsonString
	 * 
	 * @return
	 * 
	 *         　　
	 */
	public static Integer[] getIntegerArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);

		Integer[] integerArray = new Integer[jsonArray.size()];

		for (int i = 0; i < jsonArray.size(); i++) {

			integerArray[i] = jsonArray.getInt(i);

		}

		return integerArray;

	}

	/**
	 * 从json数组中解析出java Date 型对象数组，使用本方法必须保证.
	 * 
	 * @param jsonString
	 * 
	 * @return
	 * 
	 *         　　
	 * 
	 * @throws ParseException
	 */

	public static Date[] getDateArray4Json(String jsonString, String dataFormat) throws ParseException {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);

		Date[] dateArray = new Date[jsonArray.size()];

		String dateString;

		Date date;

		for (int i = 0; i < jsonArray.size(); i++) {

			dateString = jsonArray.getString(i);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			date = df.parse(dateString);

			dateArray[i] = date;

		}

		return dateArray;

	}

	/**
	 * 从json数组中解析出java Integer型对象数组.
	 * 
	 * @param jsonString
	 * 
	 * @return
	 * 
	 *         　　
	 */

	public static Double[] getDoubleArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);

		Double[] doubleArray = new Double[jsonArray.size()];

		for (int i = 0; i < jsonArray.size(); i++) {

			doubleArray[i] = jsonArray.getDouble(i);

		}

		return doubleArray;

	}

	/**
	 * 将java对象转换成json字符串.
	 * 
	 * @param javaObj
	 * 
	 * @return
	 * 
	 *         　　
	 */

	public static String getJsonString4JavaPOJO(Object javaObj) {

		JSONObject json;

		json = JSONObject.fromObject(javaObj);

		return json.toString();

	}

	/**
	 * 配置json-lib需要的excludes不需要转换的属性数组.
	 * 
	 * @param excludes
	 *            根据excludes生成的jsonConfig，用于write
	 * @return
	 */
	public static JsonConfig configJson(String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		return jsonConfig;
	}

	/**
	 * 将JSON串转换成LIST.
	 * 
	 * <pre>
	 * eg:
	 * String str = "[{assignId:1000,result:1,pubAddrs:[{orgId:1001,result:2,confmName:'peking univ'},{orgId:1002,result:1,confmName:'beijing univ'}]}]";
	 * Map<String, Class> m = new HashMap<String, Class>();
	 * m.put("pubAddrs", PubConfirmAddr.class);
	 * List<PubConfirm> list = JsonUtils.covertToList(str, PubConfirm.class, m);
	 * </pre>
	 * 
	 * @param arrayStr
	 * @param objectClass
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List covertToList(String arrayStr, Class objectClass, Map<String, Class> map) {

		List list = new ArrayList();
		if (StringUtils.isBlank(arrayStr)) {
			return list;
		}
		if (!net.sf.json.util.JSONUtils.mayBeJSON(arrayStr)) {
			return list;
		}
		JSONArray ay = JSONArray.fromObject(arrayStr);
		if (ay.size() == 0) {
			return list;
		}
		for (int i = 0; i < ay.size(); i++) {
			JSONObject obj = (JSONObject) ay.get(i);
			if (map != null) {
				list.add(JSONObject.toBean(obj, objectClass, map));
			} else {
				list.add(JSONObject.toBean(obj, objectClass));
			}
		}
		return list;
	}

	private static final String dateformater = "yyyy-MM-dd hh:mm:ss";

	/**
	 * 
	 * <pre>
	 *       Description:
	 *       	统一方法调用	
	 *       @param obj
	 *       @return
	 *       @throws Exception
	 *       Arlon.Yang created this method at 2011-2-4 22:52:29
	 * </pre>
	 */
	@SuppressWarnings("rawtypes")
	public static String FormatToJsonstr(Object obj) {
		StringBuffer sbf = null;
		try {
			if (obj == null) {
				return "\"\"";
			} else if (obj instanceof String || obj instanceof Integer || obj instanceof Double || obj instanceof Float
					|| obj instanceof Long || obj instanceof Boolean || obj instanceof Character || obj instanceof Byte) {
				return "\"" + string2Json(obj.toString()) + "\"";
			} else if (obj instanceof BigDecimal) {
				return "\"" + string2Json(obj.toString()) + "\"";
			}

			else if (obj instanceof Date) {
				Date d = (Date) obj;
				SimpleDateFormat sdf = new SimpleDateFormat(dateformater);
				return "\"" + sdf.format(d) + "\"";
			} else if (obj instanceof java.sql.Date) {
				java.sql.Date d = (java.sql.Date) obj;
				SimpleDateFormat sdf = new SimpleDateFormat(dateformater);
				return "\"" + sdf.format(d) + "\"";
			} else if (obj instanceof Timestamp) {
				Timestamp ts = (Timestamp) obj;
				SimpleDateFormat sdf = new SimpleDateFormat(dateformater);
				return "\"" + sdf.format(new Date(ts.getTime())) + "\"";
			} else if (obj instanceof Collection) {
				sbf = FormatCollection((Collection) obj);
			}

			else if (obj instanceof Map) {
				sbf = FormatMap((Map) obj);
			}

			else if (obj instanceof Object[]) {
				sbf = FormatObjectAttr((Object[]) obj);
			} else {
				sbf = FormatPojo(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sbf.toString();
	}

	/**
	 * JSON字符串特殊字符处理，比如：“\A1;1300”
	 * 
	 * @param s
	 * @return String
	 */
	public static String string2Json(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 *      Description:
	 *      		格式化 List,Set 以及其实现子类的数据(支持深度赋值)
	 *      @param col
	 *      @return
	 *      @throws Exception
	 *      Arlon.Yang created this method at 2011-2-4 22:52:52
	 * </pre>
	 */
	@SuppressWarnings("rawtypes")
	private static StringBuffer FormatCollection(Collection col) throws Exception {
		StringBuffer sbf = new StringBuffer();
		if (col == null || col.size() == 0) {
			sbf.append("[]");
			return sbf;
		}
		sbf.append("[");
		for (Object obj : col) {
			sbf.append(FormatToJsonstr(obj));
			sbf.append(",");
		}
		if (sbf.length() != -1) {
			sbf.deleteCharAt(sbf.length() - 1).append("]");
		} else {
			sbf.append("]");
		}
		return sbf;
	}

	/**
	 * <pre>
	 *      Description:
	 *      		格式化Map数据
	 *      @param map
	 *      @return
	 *      @throws Exception
	 *      Arlon.Yang created this method at 2011-2-4 22:53:04
	 * </pre>
	 */
	@SuppressWarnings("rawtypes")
	private static StringBuffer FormatMap(Map map) throws Exception {
		StringBuffer sbf = new StringBuffer();
		sbf.append("{");
		for (Object obj : map.keySet()) {
			sbf.append("\"" + obj + "\":");
			sbf.append(FormatToJsonstr(map.get(obj)));
			sbf.append(",");
		}

		if (sbf.length() != 1) {
			sbf.deleteCharAt(sbf.length() - 1).append("}");
		} else {
			sbf.append("}");
		}
		return sbf;
	}

	/**
	 * <pre>
	 *      Description:
	 *      		格式化单个的pojo(利用snitf工具生成格式的Pojo其余的适应)
	 *      @param obj
	 *      @return
	 *      Arlon.Yang created this method at 2011-2-4 22:53:15
	 * </pre>
	 */
	@SuppressWarnings("rawtypes")
	private static StringBuffer FormatPojo(Object obj) {
		StringBuffer sbf = new StringBuffer();
		Class c = null;
		Object resultobject = null;
		sbf.append("{");
		try {
			c = obj.getClass();
			/** Snitf 类中的Pojo */
			/** 获取所有的字段 */
			for (Field field : c.getDeclaredFields()) {
				field.setAccessible(true);
				resultobject = field.get(obj);
				sbf.append("\"" + field.getName() + "\":" + FormatToJsonstr(resultobject) + ",");
			}

			if (sbf.length() != 1) {
				sbf.deleteCharAt(sbf.length() - 1).append("}");
			} else {
				sbf.deleteCharAt(sbf.length() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resultobject = null;
			c = null;
		}
		return sbf;
	}

	/**
	 * <pre>
	 *      Description:
	 *      		格式化数组对象
	 *      @param objattr
	 *      @return
	 *      @throws Exception
	 *      Arlon.Yang created this method at 2011-2-4 22:53:42
	 * </pre>
	 */
	private static StringBuffer FormatObjectAttr(Object[] objattr) throws Exception {
		StringBuffer sbf = new StringBuffer();
		sbf.append("[");
		for (Object obj : objattr) {
			sbf.append(FormatToJsonstr(obj));
			sbf.append(",");
		}
		if (sbf.length() != 1) {
			sbf.deleteCharAt(sbf.length() - 1).append("]");
		} else {
			sbf.append("]");
		}
		return sbf;
	}

	/** 判断是否为json字符串 */
	public static boolean isJson(String jsonString) {
		if (jsonString == null) {
			return false;
		}
		try {
			JSONObject.fromObject(jsonString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
