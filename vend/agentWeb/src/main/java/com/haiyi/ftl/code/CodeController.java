package com.haiyi.ftl.code;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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

public class CodeController {
    static Configuration cfg;
    static{
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        //模板的目录
        //  cfg.setClassLoaderForTemplateLoading(CodeController.class.getClassLoader(),"com/haiyi/ftl");

        try {
            cfg.setDirectoryForTemplateLoading(new File("F:/HaiYi/adminWeb/src/main/java/com/haiyi/ftl"));
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

        String tableName = "customer";
        String smallClassName = "customer";
        String msg = "消费明细";    //

        //获取表结构的信息
        List<Object[]> fieldList = TableUtil.getTableMetaData(tableName);
        //创建数据模型
        Map<String,Object> root = new HashMap<String,Object>();		       //创建数据模型

        String className = smallClassName.substring(0,1).toUpperCase()+smallClassName.substring(1);
        root.put("className",className);

        root.put("smallClassName",smallClassName);

        //TODO 类简单名
        root.put("objectDao","com.haiyi.dao."+className+"Mapper");

        //包名
        root.put("objectPackageName", "com.haiyi.domain");
        //类简单名
        root.put("objectName", "com.haiyi.domain."+className);
        //TODO 类简单名
        root.put("objectTable",tableName);		                                 //表名
        //TODO 类简单名
        root.put("objectPrimaryKey","id");
        //TODO 类简单名
        root.put("objectAllowNull","true");
        //是否有空的字段
        root.put("fieldList", fieldList);

        //query
        root.put("queryName","com.haiyi.query."+className+"Query");

        //req
        root.put("req",smallClassName+"s");

        root.put("msg", msg);
        Mapper("mapperMysqlTemplate.ftl",root);


        //Controller("Controller.ftl", root);


        //IDao("IMapper.ftl",root);
        //IService("IService.ftl",root);
        //ServiceImpl("ServiceImpl.ftl",root);
        //Query("Query.ftl",root);

       // Domain("Domain.ftl",root);


        //List("List.ftl", root);
        //Save("Save.ftl", root);
    }

    /**
     * 生成IDao
     * @param templateName
     * @param dataModel
     * @throws Exception
     */
    public static void IDao(String templateName,  Map<String,Object> dataModel) throws Exception{
        Template template = cfg.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter("F:\\HaiYi\\daoSupport\\src\\main\\java\\com\\haiyi\\dao\\"+dataModel.get("className")+"Mapper.java");
        template.process(dataModel,fileWriter);
    }

    /**
     * 生成IService
     * @param templateName
     * @param dataModel
     * @throws Exception
     */
    public static void IService(String templateName,  Map<String,Object> dataModel) throws Exception{
        Template template = cfg.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter("F://HaiYi//agentWeb//src/main/java/com/haiyi/service/"+dataModel.get("className")+"Service.java");
        template.process(dataModel,fileWriter);
    }

    /**
     * 生成ServiceImpl
     * @param templateName
     * @param dataModel
     * @throws Exception
     */
    public static void ServiceImpl(String templateName,  Map<String,Object> dataModel) throws Exception{
        Template template = cfg.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter("F://HaiYi//agentWeb//src/main/java/com/haiyi/service/impl/"+dataModel.get("className")+"ServiceImpl.java");
        template.process(dataModel,fileWriter);
    }


    /**
     * 生成Mapper
     * @param templateName
     * @param dataModel
     * @throws Exception
     */
    public static void Mapper(String templateName,  Map<String,Object> dataModel) throws Exception{
        Template template = cfg.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter("D:\\mapper\\"+dataModel.get("className")+"Mapper.xml");
        template.process(dataModel,fileWriter);
    }

    /**
     * 生成Query
     * @param templateName
     * @param dataModel
     * @throws Exception
     */
    public static void Query(String templateName,  Map<String,Object> dataModel) throws Exception{
        Template template = cfg.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter("F:\\HaiYi\\daoSupport\\src\\main\\java\\com\\haiyi\\query\\"+dataModel.get("className")+"Query.java");
        template.process(dataModel,fileWriter);
    }

    /**
     * 生成Controller
     * @param templateName
     * @param dataModel
     * @throws Exception
     */
    public static void Controller(String templateName,  Map<String,Object> dataModel) throws Exception{
        Template template = cfg.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter("F://HaiYi//agentWeb//src/main/java/com/haiyi/controller/"+dataModel.get("className")+"Controller.java");
        template.process(dataModel,fileWriter);
    }

    /**
     * 生成save
     * @param templateName
     * @param dataModel
     * @throws Exception
     */
    public static void Save(String templateName,  Map<String,Object> dataModel) throws Exception{

        List<String> execlude = Arrays.asList("id");
        List<Object[]> fieldList = (List<Object[]>) dataModel.get("fieldList");

        Iterator<Object[]> iterator =fieldList.iterator();

        while(iterator.hasNext()){
            if(iterator.next()[0].equals("id")){
                iterator.remove();
            }
        }

        Template template = cfg.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter("F://HaiYi//agentWeb//src/main/webapp/WEB-INF/jsp/"+dataModel.get("smallClassName")+"s/save.jsp");
        template.process(dataModel,fileWriter);
    }
    /**
     * 生成list
     * @param templateName
     * @param dataModel
     * @throws Exception
     */
    public static void List(String templateName,  Map<String,Object> dataModel) throws Exception{

        List<String> execlude = Arrays.asList("id");
        List<Object[]> fieldList = (List<Object[]>) dataModel.get("fieldList");

        Iterator<Object[]> iterator =fieldList.iterator();

        while(iterator.hasNext()){
            if(iterator.next()[0].equals("id")){
                iterator.remove();
            }
        }

        Template template = cfg.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter("F://HaiYi//agentWeb//src/main/webapp/WEB-INF/jsp/"+dataModel.get("smallClassName")+"s/list.jsp");
        template.process(dataModel,fileWriter);
    }


    /**
     * 生成Domain
     * @param templateName
     * @param dataModel
     * @throws Exception
     */
    public static void Domain(String templateName,  Map<String,Object> dataModel) throws Exception{
        List<String> execlude = Arrays.asList("id");
        List<Object[]> fieldList = (List<Object[]>) dataModel.get("fieldList");

        Iterator<Object[]> iterator =fieldList.iterator();

        while(iterator.hasNext()){
            if(execlude.contains(iterator.next()[0])){
                iterator.remove();
            }
        }

        Template template = cfg.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter("F:\\HaiYi\\daoSupport\\src\\main\\java\\com\\haiyi\\domain\\"+dataModel.get("className")+".java");
        template.process(dataModel,fileWriter);
    }
}