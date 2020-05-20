package com.haiyi.convert;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * 字符串转Date转换器
 * @author Administrator
 *
 */
public class StringToDateConverter implements Converter<String, Date> {
	
	public StringToDateConverter(){
		
	}
	
	//yyyy-mm-dd
	private String YYYY_MM_DD = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
	
	//yyyy/mm/dd
	private String YYYY_MM_DD2 = "((19|20)[0-9]{2})/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])";
	
	//yyyy-mm-dd hh:mm
	private String YYYY_MM_DD_HH_MM = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) "
			+ "([01]?[0-9]|2[0-3]):[0-5][0-9]";
	
	//yyyy-mm-dd hh:mm:ss
	private String YYYY_MM_DD_HH_MM_SS = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) "
			+ "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
 
	@Override
	public Date convert(String source) {
		if(!StringUtils.hasLength(source)) {
			return null;
		}
		DateFormat df = null;
		if(source.matches(YYYY_MM_DD)){
			df = new SimpleDateFormat("yyyy-MM-dd");
		}else if(source.matches(YYYY_MM_DD2)){  
			df = new SimpleDateFormat("yyyy/MM/dd");
		}else if(source.matches(YYYY_MM_DD_HH_MM)){
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}else if(source.matches(YYYY_MM_DD_HH_MM_SS)){
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else{
			return null;
		}
		try {
			return df.parse(source);
		} catch (ParseException e) {
			throw new IllegalArgumentException(String.format("类型转换失败，需要格式%s，但格式是[%s]", "yyyy-MM-dd HH:mm:ss或yyyy-MM-dd HH:mm或yyyy-MM-dd",source)); 
		}
	}
}