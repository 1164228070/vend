package com.haiyi.domain;
import java.math.BigDecimal;
import java.util.Date;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;
/**
  * 
  * @ClassName: AccountLog
  * @Company: 麦子科技
  * @Description: AccountLog实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="账单",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class AccountLog extends BaseDomain {
    //流水号
    private String accountLog;                                      
     //员工Id
    private Integer memberId;                                    
    //员工名称
    private String memberName;                                      
    //积分
    private Byte moneyType;                                      
    //彩票
    private BigDecimal money;  
    //来源(手机\电脑\设备)
    private String dataSrc;                                      
    //拨款时间]                               
    private Date createDate;                                        
    //交易类型(1.充值 2消费 3 退货 4进 5出 6 退款)
    private Byte tradeType;                                      
    //交易号
    private String comsumeLog;
	public String getAccountLog() {
		return accountLog;
	}
	public void setAccountLog(String accountLog) {
		this.accountLog = accountLog;
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
	public Byte getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(Byte moneyType) {
		this.moneyType = moneyType;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Byte getTradeType() {
		return tradeType;
	}
	public void setTradeType(Byte tradeType) {
		this.tradeType = tradeType;
	}
	public String getComsumeLog() {
		return comsumeLog;
	}
	public void setComsumeLog(String comsumeLog) {
		this.comsumeLog = comsumeLog;
	}
    
    
	 
}