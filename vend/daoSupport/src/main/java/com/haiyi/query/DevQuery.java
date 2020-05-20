package com.haiyi.query;
import com.haiyi.query.base.BaseQuery;
public class DevQuery extends BaseQuery{
	
	private Integer userId;
	private String name;
	private Integer agentId;
	private String num;       //设备编号
	private Integer shortNum;
	private String userName;
	
	private Integer state;    //设备状态

	private Integer devStatus;

	private Integer frothStatus;
	
	private boolean tokened;  //是否授权


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String getOrder() {
		return null;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Integer getShortNum() {
		return shortNum;
	}

	public void setShortNum(Integer shortNum) {
		this.shortNum = shortNum;
	}

	public boolean isTokened() {
		return tokened;
	}

	public void setTokened(boolean tokened) {
		this.tokened = tokened;
	}

	public Integer getState() {
		return state;
	}

	public Integer getDevStatus() {
		return devStatus;
	}

	public void setDevStatus(Integer devStatus) {
		this.devStatus = devStatus;
	}

	public Integer getFrothStatus() {
		return frothStatus;
	}

	public void setFrothStatus(Integer frothStatus) {
		this.frothStatus = frothStatus;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "DevQuery{" +
				"userId=" + userId +
				", name='" + name + '\'' +
				", agentId=" + agentId +
				", num='" + num + '\'' +
				", shortNum=" + shortNum +
				", userName='" + userName + '\'' +
				", state=" + state +
				", devStatus=" + devStatus +
				", tokened=" + tokened +
				'}';
	}

	@Override
	public void addQuery(String condition, Object... param) {
	}
}
