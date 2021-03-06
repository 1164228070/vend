package com.haiyi.utils;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * 
  * @ClassName: RequestUtil
  * @Company: 麦子科技
  * @Description: Session工具类
  * @author 技术部-谢维乐
  * @date 2015-12-28 下午2:01:03
  *
 */
public class RequestUtil {
	/**
	 * 获取当前request
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes.getRequest();
	}
}