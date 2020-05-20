package com.maizi.utils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
/**
 * JSON 工具类
 * @author Administrator
 *
 */
public class JsonUtil {
	private static ObjectMapper objectMapper = null;
	static {
		objectMapper = new ObjectMapper();
		//设置 date 格式
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	}
	
	/**
	 * map to JSON 
	 * @param param
	 * @return
	 * @throws KPException 
	 */
	public static String mapToJson(Map param) throws KPException{
		try {
			return objectMapper.writeValueAsString(param);
		} catch (JsonProcessingException e) {
			throw new KPException(ExceptionKind.PARSE_E);
		}
	}
	 
	
    /**
     * 将JSON to map
     * @param jsonMap
     * @return
     * @throws KPException
     */
	public static Map<String,Object> jsonToMap(String jsonMap) throws KPException{
	    try {
	    	return objectMapper.readValue(jsonMap,Map.class);
	    } catch (Exception e) {
	    	throw new KPException(ExceptionKind.PARSE_E);
	    }
	}
	
	
	/**
     * 将JSON to list
     * @param jsonList
     * @return
     * @throws KPException
     */
	public static List<Map<String,Object>> jsonToList(String jsonList) throws KPException{
	    try {
	    	return objectMapper.readValue(jsonList,List.class);
	    } catch (Exception e) {
	    	throw new KPException(ExceptionKind.PARSE_E);
	    }
	}
	
	/**
	 * domain to JSON 
	 * @param <T>
	 * @param param
	 * @return
	 * @throws KPException 
	 */
	public static <T> String domainToJson(T t) throws KPException{
		try {
			return objectMapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			throw new KPException(ExceptionKind.PARSE_E);
		}
	}
}
