package com.haiyi.domain;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.haiyi.domain.base.LoginInfo;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;
import com.maizi.validator.AddGroup;
import com.maizi.validator.EditGroup;
/**
  * 
  * @ClassName: Agent
  * @Company: 麦子科技
  * @Description: Agent实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="设备授权",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class Agent extends LoginInfo {
    //名称
	@NotBlank(groups={AddGroup.class},message="姓名不能为空")
    private String name;
    private String openId;


	private BigDecimal rate;
                                  
    //联系人名
    private String linkName;                                      
    //联系电话
    private String linkPhone;                                      
    //地址
    private String address;                                      
    //余额
	
    private BigDecimal leftMoney;  
    //冻结资金
	
    private BigDecimal lockMoney;
     //积分
	
    private Integer score;

    //支付模式（1:直连微信支付宝 2：连中信转接支付）
	private Byte payMode;


     //0:新建1:审核通过 -1:审核不通过 -2:禁用
    private Integer agentRoleId;
    private String agentRoleName;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	 
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BigDecimal getLeftMoney() {
		return leftMoney;
	}
	public void setLeftMoney(BigDecimal leftMoney) {
		this.leftMoney = leftMoney;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getAgentRoleId() {
		return agentRoleId;
	}
	public void setAgentRoleId(Integer agentRoleId) {
		this.agentRoleId = agentRoleId;
	}
	public String getAgentRoleName() {
		return agentRoleName;
	}
	public void setAgentRoleName(String agentRoleName) {
		this.agentRoleName = agentRoleName;
	}
	public BigDecimal getLockMoney() {
		return lockMoney;
	}
	public void setLockMoney(BigDecimal lockMoney) {
		this.lockMoney = lockMoney;
	}

	public Byte getPayMode() {
		return payMode;
	}

	public void setPayMode(Byte payMode) {
		this.payMode = payMode;
	}

	@Override
	public String toString() {
		return "Agent{" +
				"name='" + name + '\'' +
				", openId='" + openId + '\'' +
				", rate=" + rate +
				", linkName='" + linkName + '\'' +
				", linkPhone='" + linkPhone + '\'' +
				", address='" + address + '\'' +
				", leftMoney=" + leftMoney +
				", lockMoney=" + lockMoney +
				", score=" + score +
				", payMode=" + payMode +
				", agentRoleId=" + agentRoleId +
				", agentRoleName='" + agentRoleName + '\'' +
				", loginName='" + loginName + '\'' +
				", password='" + password + '\'' +
				", id=" + id +
				", status=" + status +
				'}';
	}
}