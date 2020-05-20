package com.haiyi.vo;

public class OrderVo {
	private Integer num; //设备编号
	private Integer token;
	private String  orderId;//订单编号
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "OrderVo [num=" + num + ", token=" + token + ", orderId=" + orderId + "]";
	}
}
