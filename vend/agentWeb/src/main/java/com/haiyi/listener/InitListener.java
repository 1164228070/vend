package com.haiyi.listener;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.haiyi.domain.Dict;
import com.haiyi.domain.Menu;
import com.haiyi.service.DictService;
import com.haiyi.service.MenuService;
import com.haiyi.utils.DictUtil;
import com.maizi.utils.FilePathUtil;

@Component
public class InitListener implements ApplicationListener<ContextRefreshedEvent>{
	
	public static String CONTEXT_PATH;
	
	public static final String LEVEL_MENUS = "levelMenus";
	
	public static final String IMAGE_SERVER = "imageServer";
	
	
	@Autowired
	MenuService menuService;

	@Autowired
	DictService dictService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		XmlWebApplicationContext context = (XmlWebApplicationContext) event.getApplicationContext();
		ServletContext servlertContext = context.getServletContext();
		
		servlertContext.setAttribute(IMAGE_SERVER,FilePathUtil.getValue("imageServer"));

		CONTEXT_PATH = servlertContext.getContextPath()+"/";
		List<Menu> levelMenus = menuService.findLevelMenu();

		servlertContext.setAttribute(LEVEL_MENUS,levelMenus);

		List<Dict> dicts = dictService.selectDict();
		DictUtil.init(dicts);
	}
}
