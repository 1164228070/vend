package com.haiyi.domain.base;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.haiyi.domain.Menu;
import com.maizi.utils.StringUtil;

public class LoginInfo extends BaseDomain{
	
	private String type;    //

	public String name;
	
	public String loginName;
	
	public String password;
	
    private List<Menu> menus = new ArrayList<Menu>();
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	
	/**
	 * 根据菜单名字查看是否有权限
	 * @param name
	 * @return
	 */
	public boolean hasMenuByName(String name){
		if(menus==null || StringUtil.isEmpty(name)){
			return false;
		}
		for(Menu menu : menus){
            if(menu.getName().equals(name)){
            	return true;
            }
		}
		return false;
	}
	
	/**
	 * 根据methodToken判断是否有权限
	 * @param methodToken
	 * @return
	 */
	public boolean hasMenuByMethodToken(String methodToken){
		if(menus==null || StringUtil.isEmpty(methodToken)){
			return false;
		}
		for(Menu menu : menus){
			if(!StringUtil.isEmpty(menu.getURL())){
				if(methodToken.equals(menu.getURL()) || methodToken.equals(menu.getURL()+"UI")){
					return true;
				}
			}
		}
		return false;
	}
}