package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.Dev;
import com.haiyi.domain.DevAuth;
import com.haiyi.domain.DictVal;
import com.haiyi.domain.User;
import com.haiyi.query.DevQuery;
import com.haiyi.service.DevAuthService;
import com.haiyi.service.DevService;
import com.haiyi.service.DictService;
import com.haiyi.service.UserService;
import com.haiyi.utils.*;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.DateUtil;
import com.maizi.utils.JsonModel;
import com.maizi.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/devs")
@ControllerAnno(addUI = "/devs/save", detailUI = "/devs/detail", editUI = "/devs/save", listUI = "/devs/list")
public class DevController extends BaseController<Dev,DevQuery>{

    @Autowired
    DictService dictService;

    @Autowired
    UserService userService;
    @Autowired
    DevAuthService devAuthService;

    @Autowired
    public void setDevService(DevService devService) {
        this.baseService = devService;
    }

    @Override
    @AuthAnno
    public Map<String, Object> list(DevQuery e, HttpServletRequest request) throws KPException {
        String type=SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
        if("user".equals(type)){
            e.setUserId(UserUtil.getUserId(request.getSession()));
        }else{
            e.setAgentId(AgentUtil.getAgentId(request.getSession()));
        }
        return super.list(e, request);
    }

    @Override
    public Dev beforeSave(ModelMap modelMap, Dev t) throws KPException {
        Integer userId = UserUtil.getUserId(RequestUtil.getRequest().getSession());
        User user = userService.findById(userId + "");

        t.setUserId(userId);

        t.setBeatTime(DateUtil.getCurrentDate());
        DevAuth devAuth = devAuthService.findDevAuthByDevNum(t.getNum() + "");
        if(devAuth!=null){
            Byte devAuthStatus = devAuth.getStatus();
            if(devAuthStatus==2){

                devAuth.setUserId(userId);
                devAuth.setUserName(user.getName());
                devAuthService.updateById(devAuth);
                t.setAgentId(user.getAgentId());
                t.setUserName(UserUtil.getUserName(RequestUtil.getRequest().getSession()));
                t.setDevStatus(StatusConstant.DEV_STATUS_FREE);
                t.setFrothStatus(StatusConstant.FROTH_STATUS_ENOUGH);
                t.setState(StatusConstant.DEV_STATUS_OFFLINE);
            }else {
                if(t.getId()==null){
                    throw new KPException(ExceptionKind.PARAM_E,"设备已经被使用!");
                }
            }
        }else {
            throw new KPException(ExceptionKind.PARAM_E,"该设备未被授权!");
        }
        return t;
    }

    @Override
    public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
        List<DictVal> dictVals = DictUtil.getDictValListByType(4);
        modelMap.addAttribute("dictVals",dictVals);

		/*List<DictVal> payTypes = DictUtil.getDictValListByType(5);
		modelMap.addAttribute("payTypes",payTypes);

		List<DictVal> tradeWays = DictUtil.getDictValListByType(13);
		modelMap.addAttribute("tradeWays",tradeWays);

		List<DictVal> tfType = DictUtil.getDictValListByType(3);
		modelMap.addAttribute("tfType",tfType);*/
    }

    @Override
    protected void beforeDelete(String[] ids) throws KPException {
    }


    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    @RequestMapping(value={"/{ids}"}, method={RequestMethod.DELETE},headers="X-Requested-With=XMLHttpRequest")
    @AuthAnno(token="delete")
    public @ResponseBody JsonModel delete(@PathVariable("ids") String ids){
        JsonModel jsonModel = new JsonModel();
        try {
            String id[] = ids.split(",");
            beforeDelete(id);
            int result;
            if (id.length > 1) {
                // 批量删除
                result = ((DevService)this.baseService).deleteByIds(UserUtil.getUserId(RequestUtil.getRequest().getSession()), id);
            } else {
                //单个删除
                result = ((DevService)this.baseService).deleteById(UserUtil.getUserId(RequestUtil.getRequest().getSession()),ids);
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

    /**
     *修改设备状态
     * @param
     * @param sign
     * @return
     */
    @RequestMapping(value={"/{devId}/status"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
    @AuthAnno
    public @ResponseBody JsonModel updateStatus(@PathVariable("devId") Integer devId,@RequestParam(required=true) String sign){
        JsonModel jsonModel = new JsonModel();

        if("y".equals(sign)){
            jsonModel.setSuccess(((DevService)this.baseService).onLine(devId,UserUtil.getUserId(RequestUtil.getRequest().getSession())));
            jsonModel.setMsg("开锁成功");
        }else if("n".equals(sign)){
            jsonModel.setSuccess(((DevService)this.baseService).offLine(devId,UserUtil.getUserId(RequestUtil.getRequest().getSession())));
            jsonModel.setMsg("关锁成功");
        }
        return jsonModel;
    }

    /**
     * 更新设备状态是否启用接口
     * @param devId
     * @param token
     * @return
     */
    @RequestMapping(value={"/{devId}/live"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
    @AuthAnno
    public @ResponseBody JsonModel live(@PathVariable Integer devId,@RequestParam(required=true) String sign){
        JsonModel jsonModel = new JsonModel();
        if("y".equals(sign)){
            jsonModel.setSuccess(((DevService)this.baseService).onLive(devId,UserUtil.getUserId(RequestUtil.getRequest().getSession())));
            jsonModel.setMsg("在线成功");
        }else if("n".equals(sign)){
            jsonModel.setSuccess(((DevService)this.baseService).offLive(devId,UserUtil.getUserId(RequestUtil.getRequest().getSession())));
            jsonModel.setMsg("离线成功");
        }
        return jsonModel;
    }

    /**
     * 转发到设备的统计状态界面
     * @return
     */
    @RequestMapping(value={"/panel/status"},method={RequestMethod.GET})
    @AuthAnno(verifyToken=false)
    public String devPanel(){
        return "devs/status";
    }

    /**
     * 获取设备的统计状态
     * @return
     */
    @RequestMapping(value={"/{devId}/status"}, method={RequestMethod.GET},headers="X-Requested-With=XMLHttpRequest")
    @AuthAnno(verifyToken=false)
    public @ResponseBody JsonModel status(){
        JsonModel jsonModel = new JsonModel();
        return jsonModel;
    }
}