package com.haiyi.vo;

public class QueryBalanceVo {
	private Integer num;     //设备编号
	private Integer token;
	private String  cardNum;//会员卡芯片ID
	private String op;      //查询类型(1为金币2为彩票3为积分4为门票5为年票)
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
	@Override
	public String toString() {
		return "QueryBalanceVo [num=" + num + ", token=" + token + ", cardNum=" + cardNum + ", op=" + op + "]";
	}
}