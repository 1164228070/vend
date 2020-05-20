package com.haiyi.ftl.code;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;


/**
  * 
  * @ClassName: CodeController
  * @Company: 麦子科技
  * @Description: 代码工具控制器
  * @author 技术部-谢维乐
  * @date 2016年4月22日 下午1:14:51
  *
 */
 
public class CacheController {
	static Configuration cfg;
	static{
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        //模板的目录
        //cfg.setClassLoaderForTemplateLoading(CacheController.class.getClassLoader(),"com/haiyi/ftl");
		try {
			cfg.setDirectoryForTemplateLoading(new File("C:\\customer\\adminWeb\\src\\main\\java\\com\\haiyi\\ftl\\"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 
	/**
	 * @param args
	 * @throws Exception
	 */
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String url = "jdbc:mysql://116.62.67.49:3306/vend";
		String user = "kelvin";
		String password = "kelvinKELVIN123456_;;";
	    Connection conn = JdbcUtil.getConnection(url, user, password);
	    
	    String SQL = "select d.type,v.value,v.name from t_dict d , t_dictval v where d.id = v.dictId order by d.type asc,value asc";
	    PreparedStatement  preparedStatement  =  conn.prepareStatement(SQL);
	    
	    ResultSet resultSet =  preparedStatement.executeQuery();
	    
	    List<Map<String,Object>> caches = new ArrayList<Map<String,Object>>();
	    
	    int lastType = -1;
	    
	    Map<String,Object> itemMap = null;
	    while(resultSet.next()){
	    	
	    	if(lastType!=resultSet.getInt("type")){
	    		
	    		itemMap = new HashMap<String,Object>();
	    		
	    		List<Map<String,Object>> itemVals = new ArrayList<Map<String,Object>>();
	    		
	    		Map<String,Object> itemValMap = new HashMap<String,Object>();
	    		
	    		itemValMap.put("value",resultSet.getInt("value"));
	    		itemValMap.put("name",resultSet.getString("name"));
	    		
	    		itemVals.add(itemValMap);
	    		
	    		itemMap.put("type",resultSet.getInt("type"));
	    		itemMap.put("value",itemVals);
	    		
	    		lastType = resultSet.getInt("type");
	    		caches.add(itemMap);
	    	}else{
	    		
	    		List<Map<String,Object>> itemVals =  (List<Map<String, Object>>) itemMap.get("value");
	    		
                Map<String,Object> itemValMap = new HashMap<String,Object>();
	    		
	    		itemValMap.put("value",resultSet.getInt("value"));
	    		itemValMap.put("name",resultSet.getString("name"));
	    		
	    		itemVals.add(itemValMap);
	    	}
	    }
	    Map<String,List<Map<String,Object>>> dataModel = new HashMap<String,List<Map<String,Object>>>();
	    dataModel.put("cacheList",caches);
	    CacheFile("Cache.ftl",dataModel);
	}
	
 
	/**
	 * 生成Cache
	 * @param templateName
	 * @param data
	 * @throws Exception
	 */
	public static void CacheFile(String templateName,Map<String,List<Map<String,Object>>> data) throws Exception{
		Template template = cfg.getTemplate(templateName);
		FileWriter fileWriter = new FileWriter("D:\\cache.js");
		template.process(data,fileWriter);
	}
}