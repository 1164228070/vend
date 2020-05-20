package com.haiyi.domain;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;

@ClassInfoAnno(msg="ip管理",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class DevIP extends BaseDomain {
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    @Override
    public String toString() {
        return "DevIP{" +
                "ip='" + ip + '\'' +
                ", id=" + id +
                '}';
    }
}
