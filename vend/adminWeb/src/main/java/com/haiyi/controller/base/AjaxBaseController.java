package com.haiyi.controller.base;

import java.lang.reflect.ParameterizedType;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.bridge.Message;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import com.haiyi.utils.RequestParamRegister;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.ConstantUtil;
import com.maizi.utils.JsonModel;
import com.maizi.utils.LogUtils;
/**
 * AJAX基础控制类
 * @author Administrator
 *
 * @param <T>
 */
public abstract class AjaxBaseController <T extends BaseDomain,E extends BaseQuery> {
    
	protected BaseService<T,E> baseService;
	
	protected String msg;       //提示信息
	protected String idName;    //主键名称
	
	protected String [] filterFields;
	
	protected Class<T> clazz;
	  
	public AjaxBaseController() {
	    ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
	    clazz= (Class<T>) pt.getActualTypeArguments()[0];
	    if(clazz.isAnnotationPresent(ClassInfoAnno.class)){
	    	  this.msg=((ClassInfoAnno) clazz.getAnnotation(ClassInfoAnno.class)).msg();
	    	  this.idName=((ClassInfoAnno) clazz.getAnnotation(ClassInfoAnno.class)).resourceId();
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
	 * @
	 */
	@RequestMapping(value="/check",method = RequestMethod.GET,headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyLogin=true)
	public @ResponseBody Map<String,Boolean> check(HttpServletRequest request) {
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
     * 跳转显示页面
     * @param request
     * @param modelMap
     * @return
     * @throws KPException
     */
	@RequestMapping(value="", method=RequestMethod.GET)
	@AuthAnno(verifyLogin=true)
    public String list(HttpServletRequest request,ModelMap modelMap) throws KPException{
		return "list";
	}
 
	/**
	 * 分页查询数据
	 * @param offset
	 * @param limit
	 * @param request
	 * @return
	 */
	@RequestMapping(value="",method = RequestMethod.GET,headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyLogin=true)
	public @ResponseBody JsonModel findBySelective(Integer offset,Integer limit,HttpServletRequest request) {
		//解析多条件查询
		JsonModel jsonModel=new JsonModel();
		T t;
		try {
			t = RequestParamRegister.register(request,clazz,this.filterFields);
		} catch (KPException e) {
			
			
			LogUtils.logError(e.getMessage());
			jsonModel.setSuccess(false);
			jsonModel.setMsg("查询失败");
			return jsonModel;
		}
		String order = RequestParamRegister.getOrderBy(request, clazz);
		Integer page = null;
		if(offset!=null && limit !=null){
			page = offset/limit+1;
		}
	    PageInfo<T> results = (PageInfo<T>) baseService.findBySelective(null);
		jsonModel.setMsg("查询"+msg+"信息");
		
		jsonModel.setData(results.getList());
	    jsonModel.setTotal((int) results.getTotal());
	    jsonModel.setSuccess(true);
	    return jsonModel;
	}
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value="{id}/",method = RequestMethod.GET,headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyLogin=true)
	public @ResponseBody JsonModel findById(@PathVariable String id){
		JsonModel jsonModel=new JsonModel();
		T t = baseService.findById(id);
		jsonModel.setMsg("成功查询"+this.msg+"信息");
		jsonModel.setData(t);
		jsonModel.setSuccess(true);
		return jsonModel;
	}
	
	//保存前方法
	public abstract T beforeSave(T t) ;
	
	/**
	 * TODO 添加方法
	 * @param t
	 * @param bindingResult
	 * @return
	 * @
	 */
	@RequestMapping(value="",method = RequestMethod.POST,headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyLogin=true)
	public @ResponseBody JsonModel add(T t){
		JsonModel jsonModel=new JsonModel();
		
		//String result = CommonUtil.valid(validator,t,AddValidGroup.class);
		String result = null;
		if(StringUtil.isEmpty(result)){
			int count = 0;
			//反射添加Id,最后修改时间
	    	try {
	    		t = beforeSave(t);
				//BeanUtils.setProperty(t,idName,CommonUtil.getUUID());
				count = baseService.add(t);
			} catch (KPException e) {
				LogUtils.logError(e.getMessage());
				jsonModel.setMsg(ExceptionKind.ADD_E.getMessage());
			}  
			jsonModel.setMsg("新增"+msg + (count==1?"成功":"失败"));
			jsonModel.setSuccess(count==1?true:false);
		}else{
			jsonModel.setMsg(result);
			jsonModel.setSuccess(false);
			return jsonModel;
		}
		return jsonModel;
	}
	
    /**
     * 编辑操作
     * @param t
     * @param bindingResult
     * @return
     */
	@RequestMapping(value="{id}",method = RequestMethod.PUT,headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyLogin=true)
	//public @ResponseBody JsonModel edit(@Validated(value={EditValidGroup.class}) T t,BindingResult bindingResult){
	public @ResponseBody JsonModel edit(T t,BindingResult bindingResult){
		JsonModel jsonModel = new JsonModel();

		String result = null;
	//	String result = CommonUtil.valid(validator,t,EditValidGroup.class);
		if(StringUtil.isEmpty(result)){
			int count = 0;
	    	try {
	    		t = beforeSave(t);
	    		count = baseService.updateById(t);
			} catch (KPException e) {
				LogUtils.logError(e.getMessage());
				jsonModel.setMsg(ExceptionKind.UPDATE_E.getMessage());
			}  
			jsonModel.setMsg("更新"+msg + (count==1?"成功":"失败"));
			jsonModel.setSuccess(count==1?true:false);
		}else{
			jsonModel.setMsg(result);
			jsonModel.setSuccess(false);
			return jsonModel;
		}
		return jsonModel;
	}
	
	public abstract void beforeDelete(String ...id);
    
	/**
	 * 删除操作
	 * @param ids
	 * @return
	 */
	@RequestMapping(value={"/{ids}"}, method={RequestMethod.DELETE},headers="X-Requested-With=XMLHttpRequest")
	public @ResponseBody JsonModel delete(@PathVariable("ids") String ids){
		beforeDelete();
		
		JsonModel jsonModel = new JsonModel();
		try {
			String id[] = ids.split(",");
			int result;
			if (id.length > 1) {
				// 批量删除
				beforeDelete(id);
				result = this.baseService.deleteByIds(id);
			} else {
				//单个删除
				beforeDelete(id);
				result = this.baseService.deleteById(ids);
			}
			jsonModel.setSuccess(true);
			jsonModel.setMsg("成功删除" + result + "条记录");
		} catch (KPException e) {
			LogUtils.logError(e.toString());
			jsonModel.setSuccess(false);
			jsonModel.setMsg("删除" + msg + "失败,请重新刷新数据再删除");
		}
		return jsonModel;
	}
}