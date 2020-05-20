package com.haiyi.tag;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
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
		boolean result = true;
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
	 * 根据URL判断是否有权限
	 * @param menuURL
	 * @return
	 */
	private boolean hasMenuByURL(String menuURL){
		return true;
	}
}