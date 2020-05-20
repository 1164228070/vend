package com.haiyi.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.haiyi.domain.base.LoginInfo;
import com.haiyi.utils.JsonUtil;
import com.haiyi.utils.SessionUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.utils.StringUtil;

 
/**
 * 
  * @ClassName: PrivilegeInterceptor
  * @Company: 麦子科技
  * @Description: 权限过滤器
  * @author 技术部-谢维乐
  * @date 2016年4月6日 下午7:18:35
  *
 */
public class PrivilegeInterceptor implements HandlerInterceptor  {
	
	//登录请求
	private static final String LOGIN_URI="/session";
	//注册请求
	private static final String REGIST_URI="/register";
	
	//没有权限界面
	private final static String FORBIDDEN_PAGE = "WEB-INF/jsp/common/forbidden.jsp";
	
	//session过期
	private final static int RESPONSETYPE_SESSION_INVALID =0;
	
	//没有权限
 	private final static int RESPONSETYPE_FORBIDDEN =1;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
	         Method method = handlerMethod.getMethod();
	         AuthAnno authAnno = method.getAnnotation(AuthAnno.class);
			String requestURI=request.getRequestURI();
			requestURI = requestURI.substring(request.getContextPath().length());
			//System.out.println("初始==========="+requestURI);
	         if (authAnno != null ) {
				 //System.out.println("qqqqqqqqqqqq======"+requestURI);
	             boolean needLogin = authAnno.verifyLogin();
	             if (needLogin) {
	            	 //需要登录
	            	 LoginInfo loginInfo =  SessionUtil.getCurrentLoginInfo(request.getSession());
	            	 if(loginInfo!=null){
						 //System.out.println("不为空进来的url================"+requestURI);

	            		 if(requestURI.startsWith(LOGIN_URI) || requestURI.startsWith(REGIST_URI)){
	            		 	redirect(response,request.getContextPath());
	            		 	return false;
	            		 }
	            		 String methodToken =handlerMethod.getBean().getClass().getAnnotation(RequestMapping.class).value()[0] + (StringUtil.isEmpty(authAnno.token())?"":"/"+authAnno.token());
	            		 
	            		 //校验权限
	            		 if(authAnno.verifyToken()){
	            			 if(!loginInfo.hasMenuByMethodToken(methodToken)){
	            				 if("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))){
	            					 response.addHeader("sessionstatus", "timeOut");
	            					 Map<String, Object> result = new HashMap<String, Object>();  
	            					 result.put("success",false); 
	            					 result.put("msg","没有权限,请联系管理员!");
	            					 result.put("responseType",RESPONSETYPE_FORBIDDEN);
	            					 PrintWriter out = response.getWriter();  
	            					 out.print(JsonUtil.mapToJson(result));
	            					 if(out!=null){
	            						 out.flush();
	            						 out.close();
	            					 }
	            				 }else{
	            					 //转发到没有权限界面
	            					 forward(request, response, FORBIDDEN_PAGE);
	            				 }
	            				 return false;
	            			 }
	            		 }
	            	 }else{
	            		 //跳转到登录界面 或 返回 JSON提示数据
	            		 if("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))){
	            		 	response.addHeader("sessionstatus", "timeOut");
	            		 	Map<String, Object> result = new HashMap<String, Object>();  
	            		 	result.put("success",false); 
	            		 	result.put("msg","会话过期,请重新登录.");
	            		 	result.put("responseType",RESPONSETYPE_SESSION_INVALID);
	            		 	PrintWriter out = response.getWriter();  
	            		 	out.print(JsonUtil.mapToJson(result));
	            		 	if(out!=null){
	            		 		out.flush();
	            		 		out.close();
	            		 	}
	            		 }else{
	            			 redirect(response,request.getContextPath()+"/session/new");
	            		 }
	            		 return false;
	            	 }
	             }
	         }else{
				/* if(requestURI.startsWith("/session/new")){
					 System.out.println("wwwwwwwwwwwwww======"+requestURI);
					 return true;
				 }
				 if(requestURI.startsWith("/session/user/new")){
					 System.out.println("eeeeeeeeeeeeeee======"+requestURI);
					 return true;
				 }

	        	 //不需要登录
	        	 *//*String requestURI=request.getRequestURI();
        		 requestURI = requestURI.substring(request.getContextPath().length());*//*
        		 if(requestURI.startsWith(LOGIN_URI) || requestURI.startsWith(REGIST_URI)){
        		 	//不需要登录的,但是已经登陆了,又来登录
        			if(SessionUtil.getCurrentLoginInfo(request.getSession())!=null){
						System.out.println("tttttttttttttt======"+requestURI);
        				redirect(response,request.getContextPath()+"/");
        				return false;
        			}
        		 }*/
	         }
		}
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception exception)throws Exception {
	}
 

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2, ModelAndView arg3) throws Exception {
	}
	
	/**
	 *重定向   
	 * @param response
	 * @param URI
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirect(HttpServletResponse response,String URI) throws ServletException, IOException{
		response.sendRedirect(URI);
	}
	
	/**
	 * 转发
	 * @param request
	 * @param response
	 * @param URI
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forward(HttpServletRequest request,HttpServletResponse response,String URI) throws ServletException, IOException{
		request.getRequestDispatcher("/"+URI).forward(request, response);
	}
}
