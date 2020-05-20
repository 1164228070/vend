package com.haiyi.query;

import com.haiyi.query.base.BaseQuery;

public class RechargeLogQuery extends BaseQuery {

    private String memberId;
    private String memberAccount;
    private String memberName;
    private String rechargelog;
    private Byte payStatus;
    private String createDate;

    public String getRechargelog() {
        return rechargelog;
    }

    public void setRechargelog(String rechargelog) {
        this.rechargelog = rechargelog;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public Byte getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    @Override
    public String toString() {
        return "RechargeLogQuery{" +
                "memberId='" + memberId + '\'' +
                ", memberAccount='" + memberAccount + '\'' +
                ", memberName='" + memberName + '\'' +
                ", payStatus=" + payStatus +
                ", createDate='" + createDate + '\'' +
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
