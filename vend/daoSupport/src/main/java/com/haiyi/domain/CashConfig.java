package com.haiyi.domain;
import java.util.Date;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;
/**
  * 
  * @ClassName: CashConfig
  * @Company: 麦子科技
  * @Description: CashConfig实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="提现配置",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class CashConfig extends BaseDomain {
     //代理商Id
    private Integer agentId;                                     
    //类型
    private String type;                                      
    //收款号码
    private String accNo;                                      
    //收款人
    private String accUser;                                      
    //区域
    private String area;                                      
    //创建时间                             
    private Date createDate;
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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


	@Override
	public String toString() {
		return "CashConfig{" +
				"agentId=" + agentId +
				", type='" + type + '\'' +
				", accNo='" + accNo + '\'' +
				", accUser='" + accUser + '\'' +
				", area='" + area + '\'' +
				", createDate=" + createDate +
				", id=" + id +
				'}';
	}
}