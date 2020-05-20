package com.haiyi.dao;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.PhotoGallery;
import com.haiyi.query.PhotoGalleryQuery;

import java.util.List;

public interface PhotoGalleryMapper extends BaseDao<PhotoGallery, PhotoGalleryQuery> {
    List<PhotoGallery> findAll();
}
