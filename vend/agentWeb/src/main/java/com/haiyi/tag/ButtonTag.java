package com.haiyi.tag;
import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.maizi.utils.StringUtil;
/**
 * 
 * @ClassName: InTag
 * @Company: 麦子科技
 * @Description: In标签[用于检查当前Id是否在Ids中]
 * @author 技术部-谢维乐
 * @date 2016-1-16 下午5:26:15
 *
*/
public class ButtonTag extends SimpleTagSupport {
	//权限组
	private String parentName;
	
	//js对象
	private String jsObj;
	
	//不包含的jsFunction对应的Button
	private String []discluJSFunc;

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public void setJsObj(String jsObj) {
		this.jsObj = jsObj;
	}

	public void setDiscluJSFunc(String[] discluJSFunc) {
		this.discluJSFunc = discluJSFunc;
	}

	@Override
	public void doTag() throws JspException, IOException {
		StringBuilder SB = new StringBuilder();
		// 取得request对象
		PageContext pageContext = (PageContext) this.getJspContext();
		HttpSession session = pageContext.getSession();
		JspWriter jspWriter = pageContext.getOut();
		
		if(StringUtil.isEmpty(parentName)){
			//参数为空(不输出任何东西)
			jspWriter.write("");
		}else{
			/*List<Privilege> privileges = SessionUtil.getCurrentPrivilege(session);
			if(privileges!=null){
				if(StringUtil.isEmpty(discluJSFunc)){
					for(Privilege privilege : privileges){
						if(parentName.equalsIgnoreCase(privilege.getParentName())){
							//说明有权限拼接对应的Button 
							SB.append("<a href='#' class='easyui-linkbutton' data-options=\"iconCls:'"+privilege.getIconCls()+"'\" onclick='"+jsObj+'.'+privilege.getJSFunction()+"();' plain='true'>"+privilege.getText()+"</a>");
						}   
					}
				}else{
					for(Privilege privilege : privileges){
						if(parentName.equalsIgnoreCase(privilege.getParentName())){
							for(String jfFunc:discluJSFunc){
								if(!jfFunc.equals(privilege.getJSFunction())){
									//说明有权限拼接对应的Button 
									SB.append("<a href='#' class='easyui-linkbutton' data-options=\"iconCls:'"+privilege.getIconCls()+"'\" onclick='"+jsObj+'.'+privilege.getJSFunction()+"();' plain='true'>"+privilege.getText()+"</a>");
								}
							}
						}   
					}
				}
			}*/
			jspWriter.write(SB.toString());
		}
	}
}