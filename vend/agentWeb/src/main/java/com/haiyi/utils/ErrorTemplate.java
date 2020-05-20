package com.haiyi.utils;

import com.haiyi.listener.InitListener;

public final class ErrorTemplate {
	
	private static final String [] TEMPLATE = {
		"%s<a href='"+InitListener.CONTEXT_PATH+"%s'>跳转</a>"
	};
	
	/**
	 * 获取错误信息
	 * @param params  
	 * @return
	 */
	public static String getError(String ...params){
		return String.format(TEMPLATE[0],params);
	}
}
