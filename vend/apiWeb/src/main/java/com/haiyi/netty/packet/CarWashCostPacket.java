package com.haiyi.netty.packet;

import com.haiyi.netty.packet.util.BasePacket;
import com.haiyi.netty.packet.util.Command;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 洗车结算数据包
 * @author Administrator
 *
 */
public class CarWashCostPacket extends BasePacket {
    private String devNum;
    private String account;
    private String loginName;
    private BigDecimal consumption;
    private Integer time;
    private String orderId;

    public String getDevNum() {
        return devNum;
    }

    public void setDevNum(String devNum) {
        this.devNum = devNum;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public Byte getCommand() {
        return Command.CARWASH_COST_REQUEST;
    }
}
