package com.haiyi.query;

import com.haiyi.query.base.BaseQuery;

public class PhotoGalleryQuery extends BaseQuery {
    private String photoName;

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    @Override
    public String toString() {
        return "PhotoGalleryQuery{" +
                "photoName='" + photoName + '\'' +
                '}';
    }

    @Override
    public String getOrder() {
        return null;
    }

    @Override
    public void addQuery(String condition, Object... param) {

    }
}
