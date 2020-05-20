package com.haiyi.netty.packet;

import com.haiyi.netty.packet.util.BasePacket;
import com.haiyi.netty.packet.util.Command;

import java.util.Map;

/**
 * 余额响应数据包
 * @author Administrator
 *
 */
public class ReporteResponsePacket extends BasePacket {
    private String msg;
    private boolean result;


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

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public Byte getCommand() {
        return Command.REPORTE_RESPONSE;
    }
}
