package com.haiyi.domain;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @ClassName: ComsumeLog
 * @Company: 麦子科技
 * @Description: ComsumeLog实体
 * @author 技术部-谢维乐
 * @date 2016年5月1日 下午1:38:35
 *
 */
@ClassInfoAnno(msg="消费记录",resourceId="id",dbId = "id")
public class InsideComsume extends BaseDomain {
    //订单号
    private String orderId;
    //设备名称
    private String devName;
    //设备号
    private String devNum;
    //设备所属商户id
    private Integer userId;
    private String userName;
    private String agentName;
    //商户所属代理商id
    private Integer agentId;

    private Integer productId;
    private String productName;
    //消费金额
    private BigDecimal price;
    //商品成本
    private BigDecimal cost;

    //交易时间
    private Date createDate;

    //支付状态(1消费完成2异常 3未支付 4未完成)
    private Byte payStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevNum() {
        return devNum;
    }

    public void setDevNum(String devNum) {
        this.devNum = devNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Byte getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public String toString() {
        return "InsideComsume{" +
                "orderId='" + orderId + '\'' +
                ", devName='" + devName + '\'' +
                ", devNum='" + devNum + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", agentName='" + agentName + '\'' +
                ", agentId=" + agentId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                ", createDate=" + createDate +
                ", payStatus=" + payStatus +
                ", id=" + id +
                '}';
    }
}
