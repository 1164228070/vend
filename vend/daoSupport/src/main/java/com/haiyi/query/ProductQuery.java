package com.haiyi.query;

import com.haiyi.query.base.BaseQuery;


public class ProductQuery extends BaseQuery {


    //名称
    private String name;
    //商品编号
    private String productNum;
    //商品组
    private Integer productGroupId;
    private String productGroupName;

    private Integer agentId;
    private String agentName;
    private Integer userId;
    private Integer userName;
    //商品货道
    private Integer orderNum;
    private Integer status;
    private Integer countStatus;


    public Integer getCountStatus() {
        return countStatus;
    }

    public void setCountStatus(Integer countStatus) {
        this.countStatus = countStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public Integer getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(Integer productGroupId) {
        this.productGroupId = productGroupId;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserName() {
        return userName;
    }

    public void setUserName(Integer userName) {
        this.userName = userName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String getOrder() {
        return null;
    }

    @Override
    public void addQuery(String condition, Object... param) {

    }

    @Override
    public String toString() {
        return "ProductQuery{" +
                "name='" + name + '\'' +
                ", productNum='" + productNum + '\'' +
                ", productGroupId=" + productGroupId +
                ", productGroupName='" + productGroupName + '\'' +
                ", agentId=" + agentId +
                ", agentName='" + agentName + '\'' +
                ", userId=" + userId +
                ", userName=" + userName +
                ", orderNum=" + orderNum +
                ", status=" + status +
                ", countStatus=" + countStatus +
                '}';
    }
}
