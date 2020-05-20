package com.haiyi.query;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.haiyi.query.base.BaseQuery;
import com.maizi.utils.DateUtil;
public class ComsumeLogQuery extends BaseQuery{
	
	private static final String EXP = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) - [1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

	private Integer memberId;
	private Byte payStatus;
	private String memberName;
	private String userName;
	private String agentName;

	private Integer productId;
	private String productName;

	private String comsumeLog;
	private String orderId;
	private String devNum;
	//设备所属商户id
	private Integer userId;
	//商户所属代理商id
	private Integer agentId;
	
	private String start;
	private String end;
	 
	private String createDateRange;




	public void setCreateDateRange(String createDateRange) {
		/*try {
			createDateRange = new String(createDateRange.getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/

		if(createDateRange.matches(EXP)){
			String[] results = createDateRange.split(" - ");
			if(results[0].compareTo(results[1])!=1){
				start =  results[0];
				end =  results[1];
			}
		}
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}


	public Byte getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Byte payStatus) {
		this.payStatus = payStatus;
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

	public String getDevNum() {
		return devNum;
	}

	public void setDevNum(String devNum) {
		this.devNum = devNum;
	}


	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
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
		return "ComsumeLogQuery{" +
				"memberId=" + memberId +
				", payStatus=" + payStatus +
				", memberName='" + memberName + '\'' +
				", userName='" + userName + '\'' +
				", agentName='" + agentName + '\'' +
				", productId=" + productId +
				", productName='" + productName + '\'' +
				", comsumeLog='" + comsumeLog + '\'' +
				", orderId='" + orderId + '\'' +
				", devNum='" + devNum + '\'' +
				", userId=" + userId +
				", agentId=" + agentId +
				", start='" + start + '\'' +
				", end='" + end + '\'' +
				", createDateRange='" + createDateRange + '\'' +
				'}';
	}
}
