package com.haiyi.service.impl;

import com.haiyi.dao.PhotoGalleryMapper;
import com.haiyi.domain.PhotoGallery;
import com.haiyi.query.PhotoGalleryQuery;
import com.haiyi.service.PhotoGalleryService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoGalleryServiceImpl extends BaseServiceImpl<PhotoGallery, PhotoGalleryQuery> implements PhotoGalleryService {
    @Autowired
    public void setPhotoGalleryMapper(PhotoGalleryMapper photoGalleryMapper){
        this.daoMapper = photoGalleryMapper;
    }

}
