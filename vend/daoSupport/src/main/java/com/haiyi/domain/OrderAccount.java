package com.haiyi.domain;
import java.math.BigDecimal;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;
/**
  * 
  * @ClassName: OrderAccount
  * @Company: 麦子科技
  * @Description: OrderAccount实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="订单月统计",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class OrderAccount extends BaseDomain {
     //代理Id
    private Integer agentId;                                    
    //代理名称
    private String agentName;                                                                     
     //支付宝
    private Integer finishCount;                                    
     //支付宝
    private Integer errorCount;                                    
    //订单金额
    private BigDecimal payAmount;  
    //统计时间
    private String createDate;
    
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
	
	public Integer getFinishCount() {
		return finishCount;
	}
	public void setFinishCount(Integer finishCount) {
		this.finishCount = finishCount;
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}    
}