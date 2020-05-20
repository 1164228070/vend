package com.haiyi.netty.packet;


import com.haiyi.netty.packet.util.BasePacket;
import com.haiyi.netty.packet.util.Command;

/**
 * 余额数据包
 * @author Administrator
 *
 */
public class BalancePacket extends BasePacket {

    private String loginName;

    private String password;

    private String devNum;

    public String getDevNum() {
        return devNum;
    }

    public void setDevNum(String devNum) {
        this.devNum = devNum;
    }

    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Byte getCommand() {
        return Command.BALANCE_INFO_REQUEST;
    }
}