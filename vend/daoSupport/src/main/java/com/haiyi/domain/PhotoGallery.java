package com.haiyi.domain;

import com.haiyi.domain.base.BaseDomain;

public class PhotoGallery extends BaseDomain {
    private String photoName;
    private String img;

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getimg() {
        return img;
    }

    public void setimg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "photoGallery{" +
                "photoName='" + photoName + '\'' +
                ", img='" + img + '\'' +
                ", id=" + id +
                '}';
    }
}
