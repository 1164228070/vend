package com.haiyi.domain;

import com.haiyi.domain.base.LoginInfo;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;

import java.math.BigDecimal;
import java.util.Date;
/**
  * 
  * @ClassName: Member
  * @Company: 麦子科技
  * @Description: Member实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="会员",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class Member extends LoginInfo {
	//会员账号
	private String account;

    //余额
    private BigDecimal balance;
     //积分
    private Integer score;
    //微信唯一标识
    private String openid;
    //会员备注信息
    private String memberMsg;
    //会员头像
    private String headimgurl;

    //注册时间
    private Date registeDate;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getMemberMsg() {
		return memberMsg;
	}

	public void setMemberMsg(String memberMsg) {
		this.memberMsg = memberMsg;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Date getRegisteDate() {
		return registeDate;
	}

	public void setRegisteDate(Date registeDate) {
		this.registeDate = registeDate;
	}


	@Override
	public String toString() {
		return "Member{" +
				"account='" + account + '\'' +
				", balance=" + balance +
				", score=" + score +
				", openid='" + openid + '\'' +
				", memberMsg='" + memberMsg + '\'' +
				", headimgurl='" + headimgurl + '\'' +
				", registeDate=" + registeDate +
				", name='" + name + '\'' +
				", loginName='" + loginName + '\'' +
				", password='" + password + '\'' +
				", id=" + id +
				", status=" + status +
				'}';
	}
}