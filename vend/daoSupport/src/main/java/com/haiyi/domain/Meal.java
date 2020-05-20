package com.haiyi.domain;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;

import java.math.BigDecimal;

@ClassInfoAnno(msg="套餐",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class Meal extends BaseDomain {
    //套餐名
    private String name;
    //充值金额
    private BigDecimal memey;
    //赠送金额
    private BigDecimal gift;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMemey() {
        return memey;
    }

    public void setMemey(BigDecimal memey) {
        this.memey = memey;
    }

    public BigDecimal getGift() {
        return gift;
    }

    public void setGift(BigDecimal gift) {
        this.gift = gift;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "name='" + name + '\'' +
                ", memey=" + memey +
                ", gift=" + gift +
                ", id=" + id +
                '}';
    }
}
