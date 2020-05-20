package com.haiyi.utils;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.haiyi.domain.Menu;
import com.haiyi.domain.base.LoginInfo;
/**
 * 
  * @ClassName: SessionUtil
  * @Company: 麦子科技
  * @Description: Session工具类
  * @author 技术部-谢维乐
  * @date 2015-12-28 下午2:01:03
  *
 */
public class SessionUtil {

	//保存session中用户的键
	public final static String LOGINED_SESSION_KEY="LoginInfo";

 
    /**
     * 
     * @Title: saveCurrentLoginInfo 
     * @Description:    保存管理员信息到Session中  
     * @param @param session
     * @param @param LoginInfo     
     * @return void     
     * @throws
     */
	public static void saveCurrentLoginInfo(HttpSession session,LoginInfo LoginInfo){
	    session.setAttribute(LOGINED_SESSION_KEY,LoginInfo);
	}
	
    /**
     * 
     * @Title: getCurrentLoginInfo 
     * @Description:    获取session中的管理员 
     * @param @param session
     * @param @return     
     * @return LoginInfo     
     * @throws
     */
	public static LoginInfo getCurrentLoginInfo(HttpSession session){
		return (LoginInfo) session.getAttribute(LOGINED_SESSION_KEY);
	}
	
	
    /**
     * @Title: getCurrentLoginInfoId 
     * @Description:     
     * @param @param session
     * @param @return     
     * @return String     
     * @throws
     */
	public static Integer getCurrentLoginInfoId(HttpSession session){
		LoginInfo LoginInfo =  ((LoginInfo) session.getAttribute(LOGINED_SESSION_KEY));
		return LoginInfo==null?null:LoginInfo.getId();
	}
 
	/**
	 * 
	 * @Title: getCurrentLoginInfoName 
	 * @Description:    获取session中的管理员名字  
	 * @param @param session
	 * @param @return     
	 * @return String     
	 * @throws
	 */
	public static String getCurrentLoginInfoName(HttpSession session){
		return ((LoginInfo) session.getAttribute(LOGINED_SESSION_KEY)).getName();
	}


}