package com.haiyi.netty.packet;

import com.haiyi.netty.packet.util.BasePacket;
import com.haiyi.netty.packet.util.Command;

import java.math.BigDecimal;
import java.util.Map;
/**
 * 余额响应数据包
 * @author Administrator
 *
 */
public class BalanceResponsePacket extends BasePacket {
    private String msg;
    private boolean success;


    private Map<String,Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public Byte getCommand() {
        return Command.BALANCE_INFO_RESPONSE;
    }
}
