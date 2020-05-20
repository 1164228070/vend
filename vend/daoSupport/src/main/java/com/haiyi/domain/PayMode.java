package com.haiyi.domain;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;

@ClassInfoAnno(msg="支付模式",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class PayMode extends BaseDomain {
    private Byte mode;

    public Byte getMode() {
        return mode;
    }

    public void setMode(Byte mode) {
        this.mode = mode;
    }
}
