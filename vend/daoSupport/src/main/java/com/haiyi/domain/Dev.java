package com.haiyi.domain;
import java.util.Date;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;
/**
  * 
  * @ClassName: Dev
  * @Company: 麦子科技
  * @Description: Dev实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="设备",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class Dev extends BaseDomain {
	
	//设备名字
	private String name;
     //设备编号
    private Integer num;                                    
     //设备自编号
    private Integer shortNum;                                    
     //Token令牌
    private Integer token;                                    
    //设备关联的商户名
    private String userName;
    //客服电话
    private String phone;
     //购买速度
    private Integer speed1;                                    
     //退货速度
    private Integer speed2;                                    
     //购买脉冲
    private Integer KD;                                    
     //退货脉冲
    private Integer KD2;                                    
     //感应扣款金额
    private float CSL;
     //是否可退货(1为可退，2为不可退）
    private Byte TF;
     //退货模式
    private Byte TFType;
     //退货比例
    private Integer TBPercentage;                                    
     //购买比例
    private Integer TFMS;                  
     //绑定的商品数量
    private Integer commodity;                                    
    //支付类型(1为金币、2为彩票、3为积分、4为门票、5为年票、6为微信、7为支付宝  //手机使用
    private String payType;
    
    //金币 1 金币彩票 2  金币积分 3  彩票 4   积分 5   年票 6  门柰 7  //售货机使用
    private Integer tradeWay;

     //设备在线离线状态
    private Integer state;                                    
     //关联的商户
    private Integer userId;
    //商户所属代理
    private Integer agentId;
     //获取参数开关
    private Integer Obtain;
    //设备空闲忙碌状态
    private Integer devStatus;
    //设备泡沫状态（1为充足，2为不足）
    private Integer frothStatus;

    //设备添加时间
    private Date beatTime;


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getShortNum() {
		return shortNum;
	}
	public void setShortNum(Integer shortNum) {
		this.shortNum = shortNum;
	}
	public Integer getToken() {
		return token;
	}
	public void setToken(Integer token) {
		this.token = token;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getSpeed1() {
		return speed1;
	}
	public void setSpeed1(Integer speed1) {
		this.speed1 = speed1;
	}
	public Integer getSpeed2() {
		return speed2;
	}
	public void setSpeed2(Integer speed2) {
		this.speed2 = speed2;
	}
	public Integer getKD() {
		return KD;
	}
	public void setKD(Integer kD) {
		KD = kD;
	}
	public Integer getKD2() {
		return KD2;
	}
	public void setKD2(Integer kD2) {
		KD2 = kD2;
	}
	public float getCSL() {
		return CSL;
	}
	public void setCSL(float cSL) {
		this.CSL = cSL;
	}
	public Byte getTF() {
		return TF;
	}
	public void setTF(Byte tF) {
		TF = tF;
	}
	public Byte getTFType() {
		return TFType;
	}
	public void setTFType(Byte tFType) {
		TFType = tFType;
	}
	public Integer getTBPercentage() {
		return TBPercentage;
	}
	public void setTBPercentage(Integer tBPercentage) {
		TBPercentage = tBPercentage;
	}
	public Integer getTFMS() {
		return TFMS;
	}
	public void setTFMS(Integer tFMS) {
		TFMS = tFMS;
	}
	public Integer getCommodity() {
		return commodity;
	}
	public void setCommodity(Integer commodity) {
		this.commodity = commodity;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Integer getTradeWay() {
		return tradeWay;
	}
	public void setTradeWay(Integer tradeWay) {
		this.tradeWay = tradeWay;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDevStatus() {
		return devStatus;
	}

	public void setDevStatus(Integer devStatus) {
		this.devStatus = devStatus;
	}

	public Integer getObtain() {
		return Obtain;
	}
	public void setObtain(Integer obtain) {
		Obtain = obtain;
	}


	public Integer getFrothStatus() {
		return frothStatus;
	}

	public void setFrothStatus(Integer frothStatus) {
		this.frothStatus = frothStatus;
	}

	public Date getBeatTime() {
		return beatTime;
	}

	public void setBeatTime(Date beatTime) {
		this.beatTime = beatTime;
	}

	@Override
	public String toString() {
		return "Dev{" +
				"name='" + name + '\'' +
				", num=" + num +
				", shortNum=" + shortNum +
				", token=" + token +
				", userName='" + userName + '\'' +
				", speed1=" + speed1 +
				", speed2=" + speed2 +
				", KD=" + KD +
				", KD2=" + KD2 +
				", CSL=" + CSL +
				", TF=" + TF +
				", TFType=" + TFType +
				", TBPercentage=" + TBPercentage +
				", TFMS=" + TFMS +
				", commodity=" + commodity +
				", payType='" + payType + '\'' +
				", tradeWay=" + tradeWay +
				", state=" + state +
				", userId=" + userId +
				", agentId=" + agentId +
				", Obtain=" + Obtain +
				", devStatus=" + devStatus +
				", beatTime=" + beatTime +
				", id=" + id +
				'}';
	}
}