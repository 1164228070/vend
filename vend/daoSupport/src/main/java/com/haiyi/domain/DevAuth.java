package com.haiyi.domain;
import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;
/**
  * 
  * @ClassName: DevAuth
  * @Company: 麦子科技
  * @Description: DevAuth实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="设备授权",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class DevAuth extends BaseDomain {
     //代理商Id
    private Integer userId;
    private String userName;
    //设备号
    private String devNum;
    private DevIP devIP;
    private String ip;


    //0:未使用1:已使用


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

	public String getDevNum() {
		return devNum;
	}
	public void setDevNum(String devNum) {
		this.devNum = devNum;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public DevIP getDevIP() {
		return devIP;
	}

	public void setDevIP(DevIP devIP) {
		this.devIP = devIP;
	}

	@Override
	public String toString() {
		return "DevAuth{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", devNum='" + devNum + '\'' +
				", devIP=" + devIP +
				", ip='" + ip + '\'' +
				", id=" + id +
				", status=" + status +
				'}';
	}
}