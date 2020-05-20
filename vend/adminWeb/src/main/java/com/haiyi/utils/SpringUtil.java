package com.haiyi.utils;

import javax.servlet.ServletContext;

import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringUtil {
	
	private static ServletContext servletContext;
	
	/**
	 * 只要调用一次就可以
	 * @param servletContext
	 */
	public static void init(ServletContext servletContext){
		SpringUtil.servletContext = servletContext;
	}
	

	/**
	 *根据名字获取Bean
	 * @param name
	 * @return
	 */
	public static <T> T getBean(String name){
		//容器
		return (T) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(name);
	}
}
