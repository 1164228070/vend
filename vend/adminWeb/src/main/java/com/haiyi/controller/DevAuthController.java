package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.DevAuth;
import com.haiyi.domain.DevIP;
import com.haiyi.domain.DictVal;
import com.haiyi.domain.User;
import com.haiyi.query.DevAuthQuery;
import com.haiyi.query.DevIPQuery;
import com.haiyi.query.UserQuery;
import com.haiyi.service.DevAuthService;
import com.haiyi.service.DevIPService;
import com.haiyi.service.DevService;
import com.haiyi.service.UserService;
import com.haiyi.utils.DictUtil;
import com.haiyi.utils.ErrorTemplate;
import com.haiyi.utils.RequestUtil;
import com.haiyi.utils.StatusConstant;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.JsonModel;
import com.maizi.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/devAuths")
@ControllerAnno(addUI = "/devAuths/save", detailUI = "/devAuths/detail", editUI = "/devAuths/edit", listUI = "/devAuths/list")
public class DevAuthController extends BaseController<DevAuth,DevAuthQuery>{

    @Autowired
    UserService userService;

    @Autowired
    DevService  devService;
    @Autowired
    DevAuthService devAuthService;
    @Autowired
    DevIPService devIPService;

    @Autowired
    public void setDevAuthService(DevAuthService devAuthService) {
        this.baseService = devAuthService;
    }
    @Override
    public DevAuth beforeSave(ModelMap modelMap, DevAuth t) throws KPException {
        if(devAuthService.findDevAuthByDevNum(t.getDevNum())!=null){
            throw new KPException(ExceptionKind.PARAM_E,"设备号已经存在!");
        }
        return t;
    }

    @Override
    public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
		/*UserQuery userQuery = new UserQuery();
		userQuery.setPagination(false);
		userQuery.setStatus(StatusConstant.AGENT_STATUS_VERIFIED);
		//查找
		List<User> users = userService.findBySelective(userQuery).getList();
		if(users.isEmpty()){
			throw new KPException(ExceptionKind.BUSINESS_E,ErrorTemplate.getError("当前平台未有,请先创建代理商","users"));
		}
		modelMap.addAttribute("users", users);*/
        /*List<DevIP> devIPs=devIPService.findBySelective(new DevIPQuery()).getList();
        List<String> ips=new ArrayList<>();
        for(DevIP devIP:devIPs){
            ips.add(devIP.getIp());
        }
        modelMap.addAttribute("devIPs",devIPs);*/
    }

    @Override
    protected void beforeDelete(String[] ids) throws KPException {
    }

    /**
     * 添加设备授权
     */
    @RequestMapping(value="/",method = { RequestMethod.POST})
    @AuthAnno(verifyLogin = true)
    @Override
    public String add(DevAuth t,ModelMap modelMap,Errors errors) throws KPException {
        t=beforeSave(modelMap,t);
        String content = RequestUtil.getRequest().getParameter("devNum");
        if(StringUtils.isEmpty(content)){
            throw new KPException(ExceptionKind.PARAM_E,"设备号不能为空!");
        }
        //t.setStatus(StatusConstant.DEVAUTH_STATUS_UNUSED);
        //((DevAuthService)this.baseService).add(t);
        ((DevAuthService)this.baseService).batchAddCardNum(content.split("\r\n"),t);
        return "redirect:" + URI;
    }


    /**
     * 根据userId获取token
     * 方法用途: <br>
     * @param roleId
     * @return
     */
    @RequestMapping(value="/{userId}/initDevAuth",method = RequestMethod.GET)
    public @ResponseBody JsonModel initDevAuth(@PathVariable Integer userId){
        JsonModel jsonModel = new JsonModel();
        List<DevAuth> result = ((DevAuthService)this.baseService).findDevAuthByUserId(userId);
        jsonModel.setData(result);
        jsonModel.setSuccess(true);
        return jsonModel;
    }


    @RequestMapping(value={"selectStatus"},method=RequestMethod.POST)
    @AuthAnno(verifyLogin=true)
    public @ResponseBody JsonModel selectUserId() throws KPException{
        List<DictVal> dictVals = DictUtil.getDictValListByType(1);
        JsonModel jsonModel = new JsonModel();
        jsonModel.setData(dictVals);
        jsonModel.setSuccess(true);
        return jsonModel;
    }



    @RequestMapping("/toUpdateIP/{ids}")
    @AuthAnno
    public String toUpdateIP(@PathVariable("ids") String ids,ModelMap modelMap, Model model){
        List<DevIP> devIPs=devIPService.findBySelective(new DevIPQuery()).getList();
        List<String> ips=new ArrayList<>();
        for(DevIP devIP:devIPs){
            ips.add(devIP.getIp());
        }
        modelMap.addAttribute("devIPs",devIPs);
        model.addAttribute("ids",ids);
        return "/devAuths/updateIP";
    }



    @RequestMapping(value={"/updateIP/{ids}"}, method={RequestMethod.PUT})
    @AuthAnno
    public @ResponseBody JsonModel updateIP(@PathVariable("ids") String ids,String ip){
        JsonModel jsonModel = new JsonModel();
        try {
            String id[] = ids.split(",");
            int result=0;
            if (id.length > 1) {
                // 批量删除
                for(String newId : id){

                    result = devAuthService.updateIPById(ip,newId);
                    result=result+1;
                }
            } else {
                //单个删除
                result = devAuthService.updateIPById(ip,ids);
            }
            if(result==0){
                jsonModel.setSuccess(false);
                jsonModel.setMsg("更新失败");
            }else{
                jsonModel.setSuccess(true);
                jsonModel.setMsg("成功更新" + result + "条记录");
            }
        } catch (Exception e) {
            LogUtils.logError(e.toString());
            jsonModel.setSuccess(false);
            jsonModel.setMsg("更新失败,请重新刷新数据再更新");
        }
        return jsonModel;
    }







}