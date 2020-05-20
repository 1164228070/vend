package com.haiyi.vo;

public class CountVo {
	private Integer num; //设备编号
	private Integer token;
	private Integer  op;//查询方式
	private String commodity;
	private Integer number;
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
	public Integer getOp() {
		return op;
	}
	public void setOp(Integer op) {
		this.op = op;
	}
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "CountVo [num=" + num + ", token=" + token + ", op=" + op + ", commodity=" + commodity + ", number="
				+ number + "]";
	}
}
