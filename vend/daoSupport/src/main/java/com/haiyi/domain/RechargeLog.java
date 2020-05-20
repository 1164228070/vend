package com.haiyi.domain;

import com.haiyi.domain.base.BaseDomain;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeLog extends BaseDomain {
    //充值流水
    private String rechargelog;

    //会员id
    private Integer memberId;
    //会员账号
    private String memberAccount;
    //会员名
    private String memberName;
    //充值金额
    private BigDecimal recharge_momey;
    //赠送金额
    private BigDecimal gift;
    //充值后的余额
    private BigDecimal balance;
    //充值方式（6代表微信）
    private Byte payType;
    //充值状态
    private Byte payStatus;
    //充值备注
    private String remark;
    //充值时间
    private Date createDate;

    public BigDecimal getGift() {
        return gift;
    }

    public void setGift(BigDecimal gift) {
        this.gift = gift;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getRechargelog() {
        return rechargelog;
    }

    public void setRechargelog(String rechargelog) {
        this.rechargelog = rechargelog;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public BigDecimal getRecharge_momey() {
        return recharge_momey;
    }

    public void setRecharge_momey(BigDecimal recharge_momey) {
        this.recharge_momey = recharge_momey;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "RechargeLog{" +
                "rechargelog='" + rechargelog + '\'' +
                ", memberId=" + memberId +
                ", memberAccount='" + memberAccount + '\'' +
                ", memberName='" + memberName + '\'' +
                ", recharge_momey=" + recharge_momey +
                ", balance=" + balance +
                ", payType=" + payType +
                ", payStatus=" + payStatus +
                ", remark='" + remark + '\'' +
                ", createDate=" + createDate +
                ", id=" + id +
                '}';
    }
}
