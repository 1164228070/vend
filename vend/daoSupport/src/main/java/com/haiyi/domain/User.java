package com.haiyi.domain;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.haiyi.domain.base.LoginInfo;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;
/**
  * 
  * @ClassName: User
  * @Company: 麦子科技
  * @Description: User实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="管理员",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class User extends LoginInfo {
	
	@NotBlank
    private String name;
	
	@NotBlank
    private Integer age;
	
	@NotBlank
    @Email(message = "邮箱格式错误")
    private String email;
    
    private Boolean admin;

	private String openId;



    //资金
    @NotBlank
    private BigDecimal leftMoney;
    //冻结资金
    @NotBlank
    private BigDecimal lockMoney;

    private BigDecimal rate;


     //积分
    @NotBlank
    private Integer score;                                    

    
    @NotBlank
    private Integer agentId;
    @NotBlank
    private String agentName;
    
    private List<Role> roles = new ArrayList<Role>();


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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	 
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
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


	public BigDecimal getLockMoney() {
		return lockMoney;
	}

	public void setLockMoney(BigDecimal lockMoney) {
		this.lockMoney = lockMoney;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				", email='" + email + '\'' +
				", admin=" + admin +
				", openId='" + openId + '\'' +
				", leftMoney=" + leftMoney +
				", lockMoney=" + lockMoney +
				", rate=" + rate +
				", score=" + score +
				", agentId=" + agentId +
				", agentName='" + agentName + '\'' +
				", roles=" + roles +
				", loginName='" + loginName + '\'' +
				", password='" + password + '\'' +
				", id=" + id +
				", status=" + status +
				'}';
	}
}