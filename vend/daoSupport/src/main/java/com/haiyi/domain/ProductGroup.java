package com.haiyi.domain;
import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;
import sun.management.resources.agent;

/**
  * 
  * @ClassName: ProductGroup
  * @Company: 麦子科技
  * @Description: ProductGroup实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="商品组",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class ProductGroup extends BaseDomain {
    //组名
    private String name;                                      
    //自编号
    private String num;                                      
     //关联的设备
    private Dev dev;
    //设备
    private String devNum;                                      
     //所属代理
    private Agent agent;
    //代理
    private String agentName;                                      
     //所属代理
    private User user;
    //代理
    private String userName;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public Dev getDev() {
		return dev;
	}
	public void setDev(Dev dev) {
		this.dev = dev;
	}
	public String getDevNum() {
		return devNum;
	}
	public void setDevNum(String devNum) {
		this.devNum = devNum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@Override
	public String toString() {
		return "ProductGroup{" +
				"name='" + name + '\'' +
				", num='" + num + '\'' +
				", dev=" + dev +
				", devNum='" + devNum + '\'' +
				", agent=" + agent +
				", agentName='" + agentName + '\'' +
				", user=" + user +
				", userName='" + userName + '\'' +
				", id=" + id +
				", status=" + status +
				'}';
	}
}