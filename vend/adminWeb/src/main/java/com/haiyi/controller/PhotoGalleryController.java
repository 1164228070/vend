package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.PhotoGallery;
import com.haiyi.query.PhotoGalleryQuery;
import com.haiyi.service.PhotoGalleryService;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/photoGallery")
@ControllerAnno(addUI = "/photoGallery/save", detailUI = "/photoGallery/detail", editUI = "/photoGallery/save", listUI = "/photoGallery/list")
public class PhotoGalleryController extends BaseController<PhotoGallery, PhotoGalleryQuery> {

    @Autowired
    public void setPhotoGalleryService(PhotoGalleryService photoGalleryService) {
        this.baseService = photoGalleryService;
    }


    @Override
    public PhotoGallery beforeSave(ModelMap modelMap, PhotoGallery photoGallery) throws KPException {
        return photoGallery;
    }

    @Override
    public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {

    }

    @Override
    protected void beforeDelete(String[] ids) throws KPException {

    }

    @Override
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    @AuthAnno
    public String findById(@PathVariable String id, ModelMap modelMap){
        PhotoGallery photoGallery = ((PhotoGalleryService) baseService).findById(id);
        System.out.println(photoGallery.toString());
        modelMap.put("photoGallery",photoGallery);
        return detailUI;
    }

}
