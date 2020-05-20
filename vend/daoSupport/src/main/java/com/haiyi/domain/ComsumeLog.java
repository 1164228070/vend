package com.haiyi.domain;
import java.math.BigDecimal;
import java.util.Date;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
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
//@FilterInfoAnno(fields = {""},sorts={""})
public class ComsumeLog extends BaseDomain {
	 //消费记录
    private String comsumeLog;                                      
    //订单号
    private String orderId;
     //会员ID
    private Integer memberId;                                    
    //会员名称
    private String memberName;                                      
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

     //支付方式（微信、支付宝）
    private Byte payType;
    //消费金额
    private BigDecimal payAmount;
    //商品成本
	private BigDecimal cost;
    //洗车耗时
    private String time;

    //交易时间]
    private Date createDate;
    //请求方式（1：tcp请求 2：http请求）
	private Byte reqType;
	//交易类型(01.充值 02消费 03 退货 04进 05出 06 退款)
    private Byte tradeType;
     //支付状态(1消费完成2异常 3未支付 4未完成)
    private Byte payStatus;                                    
     //哪个代理下消费
    
    //订单修改时间
    private Date modifyTime;

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
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

	public String getComsumeLog() {
		return comsumeLog;
	}

	public void setComsumeLog(String comsumeLog) {
		this.comsumeLog = comsumeLog;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public Byte getPayType() {
		return payType;
	}

	public void setPayType(Byte payType) {
		this.payType = payType;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Byte getReqType() {
		return reqType;
	}

	public void setReqType(Byte reqType) {
		this.reqType = reqType;
	}

	public Byte getTradeType() {
		return tradeType;
	}

	public void setTradeType(Byte tradeType) {
		this.tradeType = tradeType;
	}

	public Byte getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Byte payStatus) {
		this.payStatus = payStatus;
	}


	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ComsumeLog{" +
				"comsumeLog='" + comsumeLog + '\'' +
				", orderId='" + orderId + '\'' +
				", memberId=" + memberId +
				", memberName='" + memberName + '\'' +
				", devName='" + devName + '\'' +
				", devNum='" + devNum + '\'' +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", agentName='" + agentName + '\'' +
				", agentId=" + agentId +
				", productId=" + productId +
				", productName='" + productName + '\'' +
				", payType=" + payType +
				", payAmount=" + payAmount +
				", cost=" + cost +
				", time='" + time + '\'' +
				", createDate=" + createDate +
				", reqType=" + reqType +
				", tradeType=" + tradeType +
				", payStatus=" + payStatus +
				", modifyTime=" + modifyTime +
				", id=" + id +
				'}';
	}
}