package com.haiyi.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haiyi.dao.RechargeLogMapper;
import com.haiyi.domain.ComsumeLog;
import com.haiyi.domain.Member;
import com.haiyi.domain.RechargeLog;
import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.haiyi.pay.GateWayService;
import com.haiyi.query.RechargeLogQuery;
import com.haiyi.service.MemberService;
import com.haiyi.service.RechargeLogService;
import com.haiyi.state.StateConfig;
import com.haiyi.utils.StatusConstant;
import com.maizi.exception.KPException;
import com.maizi.utils.LogUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RechargeLogServiceimpl implements RechargeLogService {
    @Autowired
    RechargeLogMapper rechargeLogMapper;
    @Autowired
    MemberService memberService;

    @Override
    public PageInfo<RechargeLog> findBySelective(RechargeLogQuery e) {
        if (e.isPagination()) {
            PageHelper.startPage(e.getPageStart(), e.getPageSize());
            Page<RechargeLog> list = (Page<RechargeLog>) rechargeLogMapper.findBySelective(e);
            return list.toPageInfo();
        }
        return new PageInfo<RechargeLog>(this.rechargeLogMapper.findBySelective(e));
    }

    @Override
    public RechargeLog findById(String id) {
        return this.rechargeLogMapper.findById(id);
    }

    @Override
    public RechargeLog findByRechargelog(String rechargelog) {
        return this.rechargeLogMapper.findByRechargelog(rechargelog);
    }

    @Override
    public List<RechargeLog> findByMemberId(Integer memberId) {
        return this.rechargeLogMapper.findByMemberId(memberId);
    }

    @Override
    public int updateStatus(Integer id, Byte payStatus) throws KPException {
        return this.rechargeLogMapper.updateStatus(id, payStatus);
    }

    @Override
    public int update(RechargeLog rechargeLog) {
        return this.rechargeLogMapper.update(rechargeLog);
    }

    @Override
    public int add(RechargeLog rechargeLog) {
        return this.rechargeLogMapper.add(rechargeLog);
    }


    //----------------------------------支付回调-----------------------------------------//
    /**
     * 微信异步通知
     * @param notifyParam
     * @return
     */
    @Override
    public String nofificationWXPay(Map<String,String> notifyParam) {

        String orderId = notifyParam.get("out_trade_no");

        RechargeLog comsumeLog = findByRechargelog(orderId);
        if(comsumeLog==null){
            LogUtils.logWarn("微信推送支付成功信息,在本地却找不到该订单[{}]信息",orderId);
            return GateWayService.NOTIFY_FAIL;
        }else{
            if(StatusConstant.PAY_STATUS_PAIED==comsumeLog.getPayStatus()){
                return GateWayService.NOTIFY_SUCCESS;
            }else{
                //获取交易号
                String out_transaction_id = notifyParam.get("out_transaction_id");

                //TODO
                // comsumeLog.setComsumeLog(out_transaction_id);//微信订单ID

                comsumeLog.setPayStatus(StateConfig.PAYSTATUS_SUCCESS);
                //设置支付方式
                comsumeLog.setPayType(StateConfig.PAYTYPE_XW);

                //更新订单状态 TODO
               // update(comsumeLog);
                update(comsumeLog);
                Member member = memberService.findByAccount(comsumeLog.getMemberAccount());

                //TODO 处理并发问题
                //修改余额
                memberService.updateBalance(comsumeLog.getBalance(),member.getAccount());

                return GateWayService.NOTIFY_SUCCESS;
            }
        }
    }


}
