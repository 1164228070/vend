package com.haiyi.utils;

import com.haiyi.weChat.Button;
import com.haiyi.weChat.Menu;
import com.haiyi.weChat.ViewButton;
import org.apache.http.ParseException;
import org.json.JSONObject;

import java.io.IOException;


/**   
*    
* 项目名称：CarWash   
* 类名称：MenuUtil   
* 类描述：   
* 创建人：乐   
* 创建时间：2019年10月17日 下午2:37:18   
* 修改人：乐   
* 修改时间：2019年10月17日 下午2:37:18   
* 修改备注：   
* @version    
*    
*/
public class MenuUtil {

	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/**
	 * 组装菜单
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		ViewButton button11 = new ViewButton();
		button11.setName("代理商入口");
		button11.setType("view");
		button11.setUrl("http://www.peshion.com/apiWeb/weChat/auth?role=agent");

		ViewButton button21 = new ViewButton();
		button21.setName("商户入口");
		button21.setType("view");
		button21.setUrl("http://www.peshion.com/apiWeb/weChat/auth?role=user");

		/*ClickButton button31 = new ClickButton();
		button31.setName("扫码事件");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");
		
		Button button = new Button();
		button.setName("菜单");
		button.setSub_button(new Button[]{button31,button32});*/
		
		menu.setButton(new Button[]{button11,button21});
		return menu;
	}
	
	public static int createMenu(String token,String menu) throws ParseException, IOException{
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = WeChatUtil.doPostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	/**
	 * 查询菜单
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	
	public static JSONObject queryMenu(String token) throws ParseException, IOException{
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = WeChatUtil.doGetStr(url);
		return jsonObject;
	}
	/**
	 * 删除菜单
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int deleteMenu(String token) throws ParseException, IOException{
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = WeChatUtil.doGetStr(url);
		int result = 0;
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
}
