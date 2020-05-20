package com.haiyi.utils;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.haiyi.domain.Menu;
/**
 * 
  * @ClassName: MenuUtil
  * @Company: 麦子科技
  * @Description: 权限工具类
  * @author 技术部-谢维乐
  * @date 2016-3-13 下午4:55:12
  *
 */
public class MenuUtil {
	
	/**
	 * 根据methodToken判断是否有权限
	 * @param session
	 * @param name
	 * @return
	 */
    public static boolean hasMenuByMethodToken(HttpSession session,String name) {
        List<Menu> menus =  SessionUtil.getCurrentLoginInfo(session).getMenus();
        for (Menu menu : menus) {
            if (name.equals(menu.getURL())) {
                return true;
            }
        }
        return false;
    }
     
    /**
     * 构造参数树
     * @param menus
     */
    public static void buildMenuTree(List<Menu> menus){
    	if(menus != null && !menus.isEmpty()){
    		Iterator<Menu> iterator = menus.iterator();
    		while(iterator.hasNext()){
    			Menu menu = iterator.next();
    			if(menu.getParentId()!=null){
    				//查找当前父菜单
    				Iterator<Menu> temp = menus.iterator();
    		        while (temp.hasNext()){
    		        	if(appendTreeNode(temp.next(),menu)){
    		        		iterator.remove();
    		        		break;
    		        	}
    		        }
    			}
    		}
    	}
    }
    
    /**
     * 追到到菜单树节点中
     * @param source
     * @param target
     * @return
     */
    private static boolean appendTreeNode(Menu source,Menu target){
    	if(source.getId().equals(target.getParentId())){
    		source.getChildren().add(target);
    		return true;
    	}else{
    		//判断是否在孩子中
    		Iterator<Menu> iterator = source.getChildren().iterator();
    		while(iterator.hasNext()){
    			Menu tempMenu = iterator.next();
    			if(appendTreeNode(tempMenu, target)){
    				return true;
    			}
    		}
    		return false;
    	}
    }
}