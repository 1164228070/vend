package com.haiyi.tag;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.haiyi.utils.MenuUtil;
import com.maizi.utils.StringUtil;
/**
  * 
  * @ClassName: MenuTag
  * @Company: 麦子科技
  * @Description: 权限标签
  * @author 技术部-谢维乐
  * @date 2015-12-16 下午3:48:08
  *
 */
public class MenuTag extends TagSupport {
	private String menuURL;      //菜单URL
	
	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}
	 
	public int doStartTag(){
		boolean result = false;
		if(!StringUtil.isEmpty(menuURL)){
			result = hasMenuByURL(menuURL);
		}
		if(result){
			return TagSupport.EVAL_BODY_INCLUDE;
		}else{
            return TagSupport.SKIP_BODY;
		}
	}

	public int doEndTag() throws JspTagException {
		return Tag.EVAL_PAGE;
	}
	
	/**
	 * 
	 * @Title: hasMenuByCode 
	 * @Description:    根据菜单名称判断是否对该资源有操作权限    
	 * @param @param menuName
	 * @param @return     
	 * @return boolean     
	 * @throws
	 */
	private boolean hasMenuByURL(String menuURL){
		HttpSession session =  this.pageContext.getSession();
	    return MenuUtil.hasMenuByMethodToken(session,menuURL);
	}
}