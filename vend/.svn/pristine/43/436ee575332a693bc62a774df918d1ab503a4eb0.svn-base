package com.maizi.utils;

import java.util.UUID;

/**
 * 字符串工具类
 * @author Administrator
 *
 */
public class StringUtil {
	
	/**
	 * 判断字符串是否在字符串数组中
	 * @param array
	 * @param param
	 * @return
	 */
	public static boolean inArray(String [] array,String param){
		if(array==null || array.length==0 || param == null || param==""){
			return false;
		}
		for(String temp : array){
			if(temp.equals(param)){
				return true;
			}
		}
		return false;
	}
    
	/**
	 * 判断字符串是否为空
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(String param){
		if(param!=null && !"".equals(param.trim())){
			return false;
		}else{
			return true;
		}
	}
	
    /**
     * 判断数组是否为空
     * @param params
     * @return
     */
	public static boolean isEmpty(Object [] params){
		if(params!=null && params.length>0){
			return false;
		}else{
			return true;
		}
	}
	
    /**
     * 截取字符串 
     * @param params
     * @return
     */
	public static String sub(String s,String start){
	    if(!isEmpty(s)){
	        return s.substring(start.length());
	    }
	    return s;
	}
	
	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID(){
		String idStr = UUID.randomUUID().toString();
		idStr = idStr.replace("-","");
		return idStr;
	}
}