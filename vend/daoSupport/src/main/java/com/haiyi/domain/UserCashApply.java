package com.haiyi.domain;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;

import java.math.BigDecimal;
import java.util.Date;

/**
  * 
  * @ClassName: CashApply
  * @Company: 麦子科技
  * @Description: CashApply实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="用户提现申请",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class UserCashApply extends BaseDomain {
    //提现金额
    private BigDecimal money;  
    //到账金额
    private BigDecimal income;
    //代理商金额
    private BigDecimal agentMoney;
    //运营商金额
    private BigDecimal adminMoney;
     //商户Id
    private Integer userId;
     //代理商Id
    private Integer agentId;
    //商户名
    private String userName;
    //代理商名
    private String agentName;
    //卡类型
    private String typeName;                                      
    //收款号码
    private String accNo;                                      
    //收款人
    private String accUser;                                      
    //区域
    private String area;                                      
    //申请时间]                               
    private Date createDate;                                        
    //处理时间]                               
    private Date handlerDate;                                        
    //备注
    private String remark;                                      
    //状态(0:待审核1:驳回2:已完成)
	public BigDecimal getMoney() {
		return money;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getAgentMoney() {
		return agentMoney;
	}

	public void setAgentMoney(BigDecimal agentMoney) {
		this.agentMoney = agentMoney;
	}

	public BigDecimal getAdminMoney() {
		return adminMoney;
	}

	public void setAdminMoney(BigDecimal adminMoney) {
		this.adminMoney = adminMoney;
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

	public void setMoney(BigDecimal money) {
		this.money = money;
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccUser() {
		return accUser;
	}
	public void setAccUser(String accUser) {
		this.accUser = accUser;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getHandlerDate() {
		return handlerDate;
	}
	public void setHandlerDate(Date handlerDate) {
		this.handlerDate = handlerDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}