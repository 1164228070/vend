package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.PayMode;
import com.haiyi.query.PayModeQuery;
import com.haiyi.service.PayModeService;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payModes")
@ControllerAnno(addUI = "/payModes/save", detailUI = "/payModes/detail", editUI = "/payModes/save", listUI = "/payModes/list")
public class PayModeController extends BaseController<PayMode, PayModeQuery> {

    @Autowired
    public void setPayModeService(PayModeService payModeService) {
        this.baseService = payModeService;
    }

    @Override
    public PayMode beforeSave(ModelMap modelMap, PayMode payMode) throws KPException {
        return payMode;
    }

    @Override
    public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {

    }

    @Override
    protected void beforeDelete(String[] ids) throws KPException {

    }
}
