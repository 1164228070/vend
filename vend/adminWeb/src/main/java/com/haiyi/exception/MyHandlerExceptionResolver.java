package com.haiyi.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.maizi.exception.KPException;
/**
 * 
  * @ClassName: MyHandlerExceptionResolver
  * @Company: 麦子科技
  * @Description: 自定义全局异常处理类
  * @author 技术部-谢维乐
  * @date 2016年5月7日 下午8:14:22
  *
 */
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(MyHandlerExceptionResolver.class);
	private final static String DEFALUT_MSG = "系统繁忙,请稍后再试吧!";
	private final static String AJAX_PAGE = "ajaxError";
	private final static String ERROR_PAGE = "common/500";

	@Override
	public ModelAndView resolveException(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) {
		
		ex.printStackTrace();
		logger.error(ex.getMessage());
		
		String message = null;
		if (ex instanceof KPException) {
			message=((KPException) ex).getSelfMessage();
		} else {
			message=DEFALUT_MSG;
		}
		 
		ModelAndView modelAndView = new ModelAndView();
		 		 
		modelAndView.addObject("message", message);
		
		 boolean ajax = "XMLHttpRequest".equals( request.getHeader("X-Requested-With") );
	     String ajaxFlag = request.getParameter("ajax")==null ?  "false": request.getParameter("ajax") ;
	     boolean isAjax = ajax || ajaxFlag.equalsIgnoreCase("true");
		if(isAjax){
		 	Map<String, Object> result = new HashMap<String, Object>();  
		 	result.put("success",false); 
		 	result.put("msg",message);
		 	result.put("responseType",1);
		 	
		 	PrintWriter out;
			try {
				response.setContentType("text/html;charset=UTF-8");  
				out = response.getWriter();
				
				//out.print(JsonUtil.mapToJson(result));
				if(out!=null){
					out.flush();
					out.close();
				}
			} catch (IOException | KPException e) {
				// TODO 
				e.printStackTrace();
			}  
			//modelAndView.setViewName(AJAX_PAGE);
			return null;
		}else{
			modelAndView.setViewName(ERROR_PAGE);  
			return modelAndView;
		}
	}

}
