package com.haiyi.controller.base;


import java.lang.reflect.ParameterizedType;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.haiyi.domain.base.BaseDomain;
import com.haiyi.query.base.BaseQuery;
import com.haiyi.service.base.BaseService;
import com.haiyi.utils.SysConfigUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.ConstantUtil;
import com.maizi.utils.JsonModel;
import com.maizi.utils.LogUtils;
import com.maizi.utils.Message;
import com.maizi.validator.AddGroup;

/**
  * 
  * @ClassName: BaseController
  * @Company: 麦子科技
  * @Description: 公共控制器
  * @author 技术部-谢维乐
  * @date 2016年4月6日 下午6:54:59
  *
  * @param <T>
 */
public abstract class BaseController <T extends BaseDomain,E extends BaseQuery> {
    
	protected BaseService<T,E> baseService;  
	
	protected String msg;                       //提示信息
	protected String idName;                    //主键名称
	                                            
	protected String listUI;                    //列表页面
	protected String addUI;                     //添加页面
	protected String editUI;                    //编辑页面
	protected String detailUI;                  //详情页面
	
	protected String URI;                       //模块
	
	protected String [] filterFields;
	
	protected Class<T> clazz;
	
	protected static String NO_DATA = "noData";    //暂无数据
	 
	  
	public BaseController() {
	    ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
	    clazz= (Class<T>) pt.getActualTypeArguments()[0];
	   // queryClazz=  (Class) pt.getActualTypeArguments()[1];
	    if(clazz.isAnnotationPresent(ClassInfoAnno.class)){
	        this.msg=((ClassInfoAnno) clazz.getAnnotation(ClassInfoAnno.class)).msg();
	    	this.idName=((ClassInfoAnno) clazz.getAnnotation(ClassInfoAnno.class)).resourceId();
	    }
	    URI = this.getClass().getAnnotation(RequestMapping.class).value()[0];
	    
	    //处理UI
	    ControllerAnno controllerAnno = this.getClass().getAnnotation(ControllerAnno.class);
	    if(controllerAnno!=null){
	    	this.listUI = controllerAnno.listUI();
	    	this.addUI = controllerAnno.addUI();
	    	this.editUI = controllerAnno.editUI();
	    	this.detailUI = controllerAnno.detailUI();
	    }
	}
	
	/**
	 * 添加瞬时消息
	 * @param redirectAttributes
	 * @param message
	 */
	protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
		if (redirectAttributes != null && message != null) {
			redirectAttributes.addFlashAttribute(ConstantUtil.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
	/**
	 * 检查是否存在
	 * @param id
	 * @param name
	 * @param extra
	 * @return
	 * @throws KPException
	 */
	@RequestMapping(value="/check",method = RequestMethod.GET)
	@AuthAnno(verifyLogin=true)
	public @ResponseBody Map<String,Boolean> check(HttpServletRequest request) throws KPException{
		Map<String,Object> param =new HashMap<String,Object>();
		
		Enumeration<String> enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
		String paramName=(String)enu.nextElement();  
		param.put(paramName,request.getParameter(paramName));
		}  
		int count = this.baseService.check(param);
		
		Map<String,Boolean> result = new HashMap<String, Boolean>();
		result.put("valid",count==0?true:false);
		return result;
	}
	
    /**
     * 分页查询[返回JSON]
     * @param offset
     * @param limit
     * @param length
     * @param request
     * @return
     * @throws KPException
     */
	@RequestMapping(value="", method=RequestMethod.GET, headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyLogin=true)
	public @ResponseBody Map<String,Object> list(E e, HttpServletRequest request) throws KPException{
		 
		Map<String,Object> result = new HashMap<String,Object>();
		
		//参考
		/*T t = RequestParamRegister.register(request, clazz,this.filterFields);
		String order = RequestParamRegister.getOrderBy(request, clazz);
		
		Integer page = null;
		if(offset!=null && limit !=null){
			page = offset/limit+1;
		}*/
		System.out.println(e.toString());
		PageInfo<T> results = baseService.findBySelective(e);
		//开始查询
		result.put("success", true);
		result.put("msg","查询"+msg+"成功");  
		
		//result.put("draw", offset);
		//result.put("recordsFiltered", results.getEndRow()>results.getTotal()?results.getTotal():results.getEndRow());
		result.put(SysConfigUtil.getValue("json-total")+"", results.getTotal());
		result.put(SysConfigUtil.getValue("json-data")+"", results.getList());
		return result;  
	}



 
		
    /**
     * 跳转显示页面
     * @param request
     * @param modelMap
     * @return
     * @throws KPException
     */
	@RequestMapping(value="", method=RequestMethod.GET)
	@AuthAnno(verifyLogin=true)
    public String list(HttpServletRequest request,ModelMap modelMap) throws KPException{
		/*if(queryClazz!=null){
			try {
				modelMap.addAttribute(this.queryClazz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				throw new KPException(ExceptionKind.ACCESS_E,e.getMessage());
			}
		}*/
		
		return listUI;
	}
		
	/**
	 * 根据ID查询  
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.GET,headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyLogin=true)
	public String findById(@PathVariable String id,ModelMap modelMap){
		T t = baseService.findById(id);
		if(t==null){
			//重定向到列表页面
			return "redirect:"+URI;
		}
		modelMap.addAttribute(t);
		return this.detailUI;
	}
	
	//保存前方法
	public abstract T beforeSave(ModelMap modelMap,T t) throws KPException;
	
	//打开界面前
	public abstract void beforeSaveUI(ModelMap modelMap,String id) throws KPException;
	
	
	/**
	 * @throws KPException 
	 * 
	 * @Title: addUI 
	 * @Description:    添加 
	 * @param @param modelMap
	 * @param @return     
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value={"/new"}, method={RequestMethod.GET})
	@AuthAnno(verifyLogin=true)
	public String addUI(ModelMap modelMap) throws KPException {
		try {
			modelMap.addAttribute(this.clazz.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			throw new KPException(ExceptionKind.ACCESS_E,e.getMessage());
		}
		beforeSaveUI(modelMap,null);
		return this.addUI;
	}

	/**
	 * 
	 * @Title: add 
	 * @Description:    保存方法  
	 * @param @param t
	 * @param @param modelMap
	 * @param @return
	 * @param @throws KPException     
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/",method = { RequestMethod.POST })
	@AuthAnno(verifyLogin = true)
	public String add(@Validated(value={AddGroup.class}) T t,ModelMap modelMap,Errors errors) throws KPException {
		//校验处理
		//获取校验错误信息

		if(errors.hasErrors()){
			List<FieldError> fieldErrors= errors.getFieldErrors();
			modelMap.addAttribute("error",fieldErrors);
			return URI+"/new";
		}
		t=beforeSave(modelMap,t);
		try {
			//BeanUtils.setProperty(t, this.idName, CommonUtil.getUUID());
			//TODO 
		} catch (Exception e) {
			throw new KPException(ExceptionKind.SYS_E,t.toString());
		}
		this.baseService.add(t);
		//addFlashMessage(redirectAttributes,new Message(Message.Type.success,"添加"+msg+"成功"));
		return "redirect:" + URI;
	}



	 
	
    /**
     * 
     * @Title: editUI 
     * @Description:    编辑界面 
     * @param @param id
     * @param @param modelMap
     * @param @return
     * @param @throws KPException     
     * @return String     
     * @throws
     */
	@RequestMapping(value = {"/{id}/edit"}, method = { RequestMethod.GET })
	@AuthAnno(verifyLogin = true)
	public String editUI(@PathVariable("id") String id, ModelMap modelMap) throws KPException {
		if (StringUtil.isEmpty(id)) {
			throw new KPException(ExceptionKind.PARAM_E,"查询信息时参数id为空");
		}
		//TODO 
		//beforeSaveUI(modelMap,id);
		BaseDomain t = (BaseDomain) this.baseService.findById(id);
		if (t == null){
			return NO_DATA;
		}
		modelMap.addAttribute(t);
		beforeSaveUI(modelMap,id);
		return this.editUI;
	}

	/**
	 * 
	 * @Title: edit 
	 * @Description:    保存方法 
	 * @param @param id
	 * @param @param modelMap
	 * @param @param t
	 * @param @return
	 * @param @throws KPException     
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value={"/{id}"}, method={RequestMethod.PUT})
	@AuthAnno(verifyLogin=true)
	public String edit(@PathVariable("id") String id, ModelMap modelMap, T t)throws KPException{
		if (StringUtil.isEmpty(id)) {
			throw new KPException(ExceptionKind.PARAM_E);
		}
		t = beforeSave(modelMap, t);
		this.baseService.updateById(t);
		return "redirect:" + URI;
	}

	protected abstract void beforeDelete(String [] ids) throws KPException;
	
	/**
	 * 
	 * @Title: delete 
	 * @Description:    单个删除
	 * @param @param id
	 * @param @param date
	 * @param @param attr
	 * @param @return
	 * @param @throws KPException     
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value={"/{ids}"}, method={RequestMethod.DELETE},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno
	public @ResponseBody JsonModel ajaxDelete(@PathVariable("ids") String ids){
		JsonModel jsonModel = new JsonModel();
		try {
			String id[] = ids.split(",");
			beforeDelete(id);
			int result;
			if (id.length > 1) {
				// 批量删除
				result = this.baseService.deleteByIds(id);
			} else {
				//单个删除
				result = this.baseService.deleteById(ids);
			}
			if(result==0){
				jsonModel.setSuccess(false);
				jsonModel.setMsg("删除失败");
			}else{
				jsonModel.setSuccess(true);
				jsonModel.setMsg("成功删除" + result + "条记录");
			}
		} catch (Exception e) {
			LogUtils.logError(e.toString());
			jsonModel.setSuccess(false);
			jsonModel.setMsg("删除" + msg + "失败,请重新刷新数据再删除");
		}
		return jsonModel;
	}
}