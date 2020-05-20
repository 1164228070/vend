package com.haiyi.netty.packet;


import com.haiyi.netty.packet.util.BasePacket;
import com.haiyi.netty.packet.util.Command;

/**
 * 余额数据包
 * @author Administrator
 *
 */
public class FrothRequestPacket extends BasePacket {

    private Integer frothStatus;


    private String devNum;

    public String getDevNum() {
        return devNum;
    }

    public void setDevNum(String devNum) {
        this.devNum = devNum;
    }

    public Integer getFrothStatus() {
        return frothStatus;
    }

    public void setFrothStatus(Integer frothStatus) {
        this.frothStatus = frothStatus;
    }

    @Override
    public Byte getCommand() {
        return Command.FROTH_REQUEST;
    }



}