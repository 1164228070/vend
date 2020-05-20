package com.haiyi.vo;

public class UploadVo {
	private Integer num; //设备编号
	private Integer token;
	private String  cardNum;//会员卡芯片ID
	private String op;//查询类型(1为金币2为彩票3为积分4为门票5为年票)
	private String commodity;//商品Id
	private String orderId;//订单编号
	private Integer number;//产品数量
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getToken() {
		return token;
	}
	public void setToken(Integer token) {
		this.token = token;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "UploadVo [num=" + num + ", token=" + token + ", cardNum=" + cardNum + ", op=" + op + ", commodity="
				+ commodity + ", orderId=" + orderId + ", number=" + number + "]";
	}
}
