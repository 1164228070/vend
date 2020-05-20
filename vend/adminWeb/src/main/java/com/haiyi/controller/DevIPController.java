package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.DevIP;
import com.haiyi.query.DevIPQuery;
import com.haiyi.service.DevIPService;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/devIPs")
@ControllerAnno(addUI = "/devIPs/save", detailUI = "/devIPs/detail", editUI = "/devIPs/save", listUI = "/devIPs/list")
public class DevIPController extends BaseController<DevIP, DevIPQuery> {

    @Autowired
    public void setDevIPService(DevIPService devIPService) {
        this.baseService = devIPService;
    }

    @Override
    public DevIP beforeSave(ModelMap modelMap, DevIP devIP) throws KPException {
        return devIP;
    }

    @Override
    public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {

    }

    @Override
    protected void beforeDelete(String[] ids) throws KPException {

    }
}
