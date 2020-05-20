package com.haiyi.domain;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;

import java.util.Date;

@ClassInfoAnno(msg="商户提现配置",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class UserCashConfig extends BaseDomain {
    //商户Id
    private Integer userId;
    //代理商Id
    private Integer agentId;
    //类型
    private String type;
    //收款号码
    private String accNo;
    //收款人
    private String accUser;
    //区域
    private String area;
    //创建时间
    private Date createDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccUser() {
        return accUser;
    }

    public void setAccUser(String accUser) {
        this.accUser = accUser;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "UserCashConfig{" +
                "userId=" + userId +
                ", agentId=" + agentId +
                ", type='" + type + '\'' +
                ", accNo='" + accNo + '\'' +
                ", accUser='" + accUser + '\'' +
                ", area='" + area + '\'' +
                ", createDate=" + createDate +
                ", id=" + id +
                '}';
    }
}
