package com.haiyi.utils;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.StringUtil;
/**
 * 通用工具类
 * @author Administrator
 *
 */
public class CommonUtil {
	
	/**
	 * 获取应用根目录
	 * @return
	 */
	public static String getContextPath(){
		return getRequest().getContextPath();
	}
	
	
	
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	/**
	 * 判断是否ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}
	
	/**
	 * 获取请求参数
	 * @param param
	 * @return
	 */
	public static Object getParam(String param){
		return getRequest().getParameter(param);
	}
	
	public static boolean isInteger(String param){
	    String regexp = "[0-9]+";	
   	    return param.matches(param);
	}
	
	/**
	 * 判断是否匹配排序
	 * @param param
	 * @return
	 */
	public static boolean isMatchSort(String param){
		if(StringUtil.isEmpty(param)){
			return false;
		}else{
			String regexp = "sort_[a-zA-Z]+_(desc|asc)";	
			return param.matches(regexp);
		}
	}
	
	/**
	 * 解析排序条件
	 * @param param
	 * @return
	 */
	public static String parseSort(String param){
		if(isMatchSort(param)){
			String [] temp = param.split("_");
			return temp[1]+" "+temp[2];
		}
	    return null;	
	}
	
	public static void copy(BaseDomain newObj,BaseDomain oldObj,String[]props){
		BeanUtils.copyProperties(newObj,oldObj,props);
	}
	 
	public static void copyProperties(Object source, Object target, String[] ignoreProperties) throws KPException {

		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target.getClass());
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object sourceValue = readMethod.invoke(source);
						Object targetValue = readMethod.invoke(target);
						if (sourceValue != null && targetValue != null && targetValue instanceof Collection) {
							Collection collection = (Collection) targetValue;
							collection.clear();
							collection.addAll((Collection) sourceValue);
						} else {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, sourceValue);
						}
					} catch (Throwable ex) {
						throw new KPException(null);
					}
				}
			}
		}
	}
	
	/**
	 * 属性复制
	 * @param source
	 * @param target
	 * @param properties    需要复制的属性数组
	 * @throws KPException
	 */
	public static void copySomeProperties(Object source, Object target, String[] properties) throws KPException{
	    if(properties==null){
	    	return;
	    }
	    PropertyDescriptor targetPd;
	    try {
			for(String property : properties){
				targetPd = BeanUtils.getPropertyDescriptor(target.getClass(), property);
				if(targetPd.getWriteMethod()!=null){
					PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
					if (sourcePd != null && sourcePd.getReadMethod() != null) {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						
						Object sourceValue = readMethod.invoke(source);
						Object targetValue = readMethod.invoke(target);
						
						
						if (sourceValue != null && targetValue != null && targetValue instanceof Collection) {
							Collection collection = (Collection) targetValue;
							collection.clear();
							collection.addAll((Collection) sourceValue);
						} else {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, sourceValue);
						}
						
					}
				}
			}
		} catch (BeansException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new KPException(ExceptionKind.INVOCATION_E,e,"属性复制异常");
		}
	}
	
	/**
	 * 
	 * @Title: decodeSecond 
	 * @Description:    二次UTF-8编码
	 * @param @param param
	 * @param @return
	 * @param @throws KPException     
	 * @return String     
	 * @throws
	 */
	public static String decodeSecond(String param) throws KPException{
		if(!StringUtil.isEmpty(param)){
			try {
				return URLDecoder.decode(URLDecoder.decode(param, "utf-8"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				throw new KPException(ExceptionKind.PARAM_E);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: encode 
	 * @Description:    解码  
	 * @param @param param
	 * @param @return
	 * @param @throws KPException     
	 * @return String     
	 * @throws
	 */
	public static String encode(String param) throws KPException{
		if(!StringUtil.isEmpty(param)){
			try {
				return URLEncoder.encode(param, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new KPException(ExceptionKind.PARAM_E);
			}
		}
		return null;
	}
	
	

	
	/**
	 * 获取 1<=x<=max 的随机数
	 * @param max 最大值
	 * @return
	 */
	public static int getRandom(int max){
		Random random = new Random();
		return random.nextInt(max);
	}
	
	/**
	 * 获取随机数
	 * @param size
	 * @return
	 */
	public static String getRandomStr(int size){
		StringBuilder SB = new StringBuilder();
		Random random = new Random();
		for(int i=0;i<size;i++){
			SB.append(random.nextInt(10));
		}
		return SB.toString();
	}
	
	/**
	 * 判断List<T>是否为空 包括 null
	 * @param <T>
	 * @param listParam
	 * @return
	 */
	public static <T> boolean listIsEmpty(List<T> listParam){
		if(listParam!=null && !listParam.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
    /**
     * 判断map 是否为空
     * @param mapParam
     * @return
     */
	public static boolean mapIsEmpty(Map<String,Object> mapParam){
		if(mapParam!=null && !mapParam.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 从cookie中得到某个 key的取值
	 * @param cookies       cookie 集合
	 * @param cookieName    需要获取的key
	 * @param defaultValue  获取不到的默认值
	 * @return
	 */
	public static String getCookieValue(Cookie[] cookies, String cookieName,String defaultValue) {
		String result = defaultValue;
		if (cookies != null) {
			int size = cookies.length;
			for (int i = 0; i < size; i++) {
				Cookie cookie = cookies[i];
				if (cookieName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return result;
	}
}