package com.haiyi.netty.packet;


import com.haiyi.netty.packet.util.BasePacket;
import com.haiyi.netty.packet.util.Command;

/**
 * 上报出货信息数据包
 * @author Administrator
 *
 */
public class ReportePacket extends BasePacket {

    private String devNum;
    private String orderId;
    private String success;

    public String getDevNum() {
        return devNum;
    }

    public void setDevNum(String devNum) {
        this.devNum = devNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public Byte getCommand() {
        return Command.REPORTE_REQUEST;
    }
}