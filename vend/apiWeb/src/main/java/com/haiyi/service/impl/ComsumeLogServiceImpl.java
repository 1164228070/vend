package com.haiyi.service.impl;

import com.alipay.api.AlipayApiException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haiyi.dao.ComsumeLogMapper;
import com.haiyi.domain.ComsumeLog;
import com.haiyi.domain.InsideComsume;
import com.haiyi.domain.Product;
import com.haiyi.domain.User;
import com.haiyi.netty.packet.BuyPushResponsePacket;
import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.haiyi.pay.AlipayResp;
import com.haiyi.pay.GateWayService;
import com.haiyi.query.ComsumeLogQuery;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.service.*;
import com.haiyi.state.StateConfig;
import com.haiyi.utils.StatusConstant;
import com.maizi.exception.KPException;
import com.maizi.utils.LogUtils;
import com.maizi.utils.SnowflakeIdFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ComsumeLogServiceImpl implements ComsumeLogService {

    @Autowired
    ComsumeLogMapper comsumeLogMapper;


    @Autowired
    MemberService memberService;

    @Autowired
    DevService devService;

    @Autowired
    AccountLogService accountLogService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    InsideComsumeService insideComsumeService;



    private static SnowflakeIdFactory snow = new SnowflakeIdFactory(1, 2);

    /**
     * 分页查找
     * @param e
     * @return
     */
    public PageInfo<ComsumeLog> findBySelective(ComsumeLogQuery e) {
        if (e.isPagination()) {
            PageHelper.startPage(e.getPageStart(), e.getPageSize());
            Page<ComsumeLog> list = (Page<ComsumeLog>) comsumeLogMapper.findBySelective(e);
            return list.toPageInfo();
        }
        return new PageInfo<ComsumeLog>(this.comsumeLogMapper.findBySelective(e));
    }


    @Override
    public ComsumeLog findByOrderId(String orderId) {
        return this.comsumeLogMapper.findByOrderId(orderId);
    }

    /*
     *//**
     *  统一下单接口
     * @param memberId
     * @param orderNums
     * @param counts
     * @param devNum
     * @return
     * @throws KPException
     *//*
    @Override
    public Map<String, Object> unifiedorder(String memberId, Integer[] orderNums, Integer[] counts, String devNum) throws KPException {
        Dev dev = devService.findByNum(devNum);
        if (dev == null) {
            throw new KPException(ExceptionKind.BUSINESS_E, "当前设备不存在");
        }
        if (StatusConstant.DEV_STATUS_ONLINE != dev.getState()) {
            throw new KPException(ExceptionKind.BUSINESS_E, "当前设备不可用");
        }
        Member member = null;
        if (!StringUtil.isEmpty(memberId)) {
            member = memberService.findById(memberId);
            if (member == null) {
                throw new KPException(ExceptionKind.BUSINESS_E, "当前用户[" + memberId + "]不存在");
            }
        }
        ComsumeLog comsumeLog = new ComsumeLog();
        List<Product> productList = productService.findByOrderNums(devNum,orderNums);

        //总共购买商品的种类
        int productTypeCount = orderNums.length;
        if (productTypeCount != productList.size()) {
            throw new KPException(ExceptionKind.BUSINESS_E, "商品参数错误");
        }

        comsumeLog.setOrderId(String.valueOf(snow.nextId()));
        //TODO
        byte type = 2;
        comsumeLog.setTradeType(type);

        BigDecimal payAmount = BigDecimal.ZERO;
        for (int i = 0; i < productTypeCount; i++) {
            Product product = productList.get(i);
            //判断商品库存
            if (StatusConstant.PRODUCT_STATUS_ONLINE != product.getStatus()) {
                throw new KPException(ExceptionKind.BUSINESS_E, "当前商品已下架");
            }
            if (product.getStoreCount() < counts[i]) {
                throw new KPException(ExceptionKind.BUSINESS_E, "当前商品库存不足");
            }
            BigDecimal productPrice = product.getPrice();
            //扣除商品
            Product tempProduct = new Product();
            tempProduct.setId(product.getId());
            tempProduct.setStoreCount(product.getStoreCount() - counts[i]);     //目前一次只能购买一件商品
            tempProduct.setAgentId(product.getAgentId());
            //更新商品
            productService.updateStoreCount(tempProduct);
            payAmount = payAmount.add(productPrice.multiply(new BigDecimal(counts[i] + "")));
        }
        comsumeLog.setCreateDate(DateUtil.getCurrentDate());

        if (member != null) {
            comsumeLog.setMemberName(member.getName());
        }
        comsumeLog.setPayAmount(payAmount);
        comsumeLog.setPayStatus(StatusConstant.PAY_STATUS_PAYING);

        comsumeLog.setDevName(dev.getName());       //设备名称
        comsumeLog.setDevNum(dev.getNum() + "");      //设备编号

        this.comsumeLogMapper.add(comsumeLog);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("orderId", comsumeLog.getOrderId());
        result.put("payAmount", comsumeLog.getPayAmount());
        return result;
    }*/

    /**
     * 更新订单状态
     *
     * @param orderId
     * @param payStatus
     * @return
     * @throws KPException
     */
    @Override
    public int updateStatus(String orderId, Byte payStatus) throws KPException {
        return comsumeLogMapper.updateStatus(orderId, payStatus);
    }

    @Override
    public int update(ComsumeLog comsumeLog) {
        return comsumeLogMapper.update(comsumeLog);
    }

    @Override
    public int add(ComsumeLog comsumeLog) {
        return comsumeLogMapper.add(comsumeLog);
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

        ComsumeLog comsumeLog = findByOrderId(orderId);
        if(comsumeLog==null){
            LogUtils.logWarn("微信推送支付成功信息,在本地却找不到该订单[{}]信息",orderId);
            return GateWayService.NOTIFY_FAIL;
        }else{
            if(StateConfig.PAYSTATUS_SUCCESS==comsumeLog.getPayStatus() || StateConfig.PAYSTATUS_UNDONE==comsumeLog.getPayStatus()){
                return GateWayService.NOTIFY_SUCCESS;
            }else{

                String transaction_id = notifyParam.get("transaction_id");

                //TODO
                // comsumeLog.setComsumeLog(out_transaction_id);//微信订单ID

                //设置外部表支付状态
                comsumeLog.setPayType(StateConfig.PAYTYPE_XW);
                comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNDONE);
                comsumeLog.setComsumeLog(transaction_id);
                update(comsumeLog);
                //设置内部表支付状态
                InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
                insideComsumeQuery.setOrderId(orderId);
                InsideComsume insideComsume = insideComsumeService.findBySelective(insideComsumeQuery).getList().get(0);
                insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNDONE);
                insideComsumeService.update(insideComsume);

                if(comsumeLog.getReqType()==StateConfig.TCP_REQUEST){
                    //购买推送信息
                    BuyPushResponsePacket buyPushResponsePacket=new BuyPushResponsePacket();
                    Product product=productService.findByDevNumAndProductId(Integer.valueOf(comsumeLog.getDevNum()),comsumeLog.getProductId());
                    //修改库存
                    product.setStoreCount(product.getStoreCount()-1);
                    if(product.getStoreCount()<=product.getThreshold()){
                        product.setCountStatus(StatusConstant.PRODUCT_COUNT_STOCKOUT);
                    }else{
                        product.setCountStatus(StatusConstant.PRODUCT_COUNT_ENOUGH);
                    }
                    productService.update(product);

                    Map<String,Object> param = new HashMap<>();
                    param.put("buyCount",1);
                    param.put("orderId",comsumeLog.getOrderId());
                    param.put("price",comsumeLog.getPayAmount());
                    param.put("orderNum",product.getOrderNum());
                    buyPushResponsePacket.setData(param);
                    buyPushResponsePacket.setSuccess(true);
                    SessionUtil.dispatcherBuyInfo(SessionUtil.getChannel(comsumeLog.getDevNum()), buyPushResponsePacket);
                }


                //获取交易号


                User user = userService.findById(comsumeLog.getUserId());
                user.setLeftMoney(user.getLeftMoney().add(comsumeLog.getPayAmount()));
                userService.update(user);
                //更新订单状态 TODO
                // update(comsumeLog);

                //TODO 处理并发问题
                return GateWayService.NOTIFY_SUCCESS;
            }
        }
    }

    /**
     * 支付宝异步通知
     * @param params
     * @param alipayResp
     * @return
     * @throws AlipayApiException
     * @throws Exception
     */
    @Override
    public String nofificationAliPay(Map<String,String> params, AlipayResp alipayResp) {
        LogUtils.logInfo("支付宝异步通知开始,参数为{}", params);
        LogUtils.logInfo("支付宝异步通知======="+alipayResp);
        String result = "fail";
		/*boolean verify_result = false;
		try {
			verify_result = checkRSA(params);
		} catch (Exception e) {
			LogUtils.logError("校验异常[{}]",e);
		}*/

        // 验证成功
        if (true) {
            if (alipayResp.getTrade_status().equals("TRADE_SUCCESS") || alipayResp.getTrade_status().equals("TRADE_FINISHED")) {
                LogUtils.logInfo("支付宝异步通知成功");
                // 更新订单支付状态
                String orderId = alipayResp.getOut_trade_no(); // 我方订单ID
                LogUtils.logInfo("订单号============"+orderId);
                ComsumeLog comsumeLog = findByOrderId(orderId);
                LogUtils.logInfo("消费记录查询成功============"+comsumeLog);
                if (comsumeLog != null) {
                    if(StateConfig.PAYSTATUS_SUCCESS==comsumeLog.getPayStatus()){
                        LogUtils.logInfo("订单已经支付过=======");
                        return "success";
                    }else{
                        LogUtils.logInfo("开始更新消费信息===========");

                        String comsumeLogId = alipayResp.getTrade_no();
                        comsumeLog.setComsumeLog(comsumeLogId);//支付宝订单ID
                        //设置支付方式
                        comsumeLog.setPayType(StateConfig.PAYTYPE_ZFB);
                        comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNDONE);
                        //total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                        update(comsumeLog);

                        //设置内部表支付状态
                        InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
                        insideComsumeQuery.setOrderId(orderId);
                        InsideComsume insideComsume = insideComsumeService.findBySelective(insideComsumeQuery).getList().get(0);
                        insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNDONE);
                        insideComsumeService.update(insideComsume);

                        if(comsumeLog.getReqType()==StateConfig.TCP_REQUEST){
                            //购买推送信息
                            BuyPushResponsePacket buyPushResponsePacket=new BuyPushResponsePacket();
                            Product product=productService.findByDevNumAndProductId(Integer.valueOf(comsumeLog.getDevNum()),comsumeLog.getProductId());
                            //修改库存
                            product.setStoreCount(product.getStoreCount()-1);
                            if(product.getStoreCount()<=product.getThreshold()){
                                product.setCountStatus(StatusConstant.PRODUCT_COUNT_STOCKOUT);
                            }else{
                                product.setCountStatus(StatusConstant.PRODUCT_COUNT_ENOUGH);
                            }
                            productService.update(product);
                            Map<String,Object> param = new HashMap<>();
                            param.put("buyCount",1);
                            param.put("orderId",comsumeLog.getOrderId());
                            param.put("price",comsumeLog.getPayAmount());
                            param.put("orderNum",product.getOrderNum());
                            buyPushResponsePacket.setData(param);
                            buyPushResponsePacket.setSuccess(true);
                            SessionUtil.dispatcherBuyInfo(SessionUtil.getChannel(comsumeLog.getDevNum()), buyPushResponsePacket);
                        }


                        //支付宝订单ID

                        User user = userService.findById(comsumeLog.getUserId());
                        user.setLeftMoney(user.getLeftMoney().add(comsumeLog.getPayAmount()));
                        userService.update(user);
                        //TODO 处理并发问题
                        return "success";
                    }
                }else{
                    LogUtils.logWarn("微信推送支付成功信息,在本地却找不到该订单[{}]信息",orderId);
                    return "fail";
                }
            }else {
                result = "fail";
            }

        } else{
            result = "fail";
        }
        LogUtils.logInfo("支付宝异步通知结束,返回值为",result);
        return result;
    }

    /**
     * 中信连微信异步通知
     * @param notifyParam
     * @return
     */
    @Override
    public String nofificationZX_WXPay(Map<String,String> notifyParam) {

        String orderId = notifyParam.get("out_trade_no");

        ComsumeLog comsumeLog = findByOrderId(orderId);
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

                LogUtils.logInfo("开始更新消费信息===========");
                if(comsumeLog.getReqType()==StateConfig.TCP_REQUEST){
                    BuyPushResponsePacket buyPushResponsePacket=new BuyPushResponsePacket();
                    Product product=productService.findById(comsumeLog.getProductId());
                    //修改库存
                    product.setStoreCount(product.getStoreCount()-1);
                    if(product.getStoreCount()<=product.getThreshold()){
                        product.setCountStatus(StatusConstant.PRODUCT_COUNT_STOCKOUT);
                    }else{
                        product.setCountStatus(StatusConstant.PRODUCT_COUNT_ENOUGH);
                    }
                    productService.update(product);
                    Map<String,Object> param = new HashMap<>();
                    param.put("buyCount",1);
                    param.put("orderId",comsumeLog.getOrderId());
                    param.put("price",comsumeLog.getPayAmount());
                    param.put("orderNum",product.getOrderNum());
                    buyPushResponsePacket.setData(param);
                    buyPushResponsePacket.setSuccess(true);
                    SessionUtil.dispatcherBuyInfo(SessionUtil.getChannel(comsumeLog.getDevNum()), buyPushResponsePacket);
                }


                //获取交易号
                //TODO
                // comsumeLog.setComsumeLog(out_transaction_id);//微信订单ID
                //设置支付方式
                comsumeLog.setComsumeLog(out_transaction_id);
                comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNDONE);
                update(comsumeLog);
                User user = userService.findById(comsumeLog.getUserId());
                user.setLeftMoney(user.getLeftMoney().add(comsumeLog.getPayAmount()));
                userService.update(user);

                //设置内部表支付状态
                InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
                insideComsumeQuery.setOrderId(orderId);
                InsideComsume insideComsume = insideComsumeService.findBySelective(insideComsumeQuery).getList().get(0);
                insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNDONE);
                insideComsumeService.update(insideComsume);

                return GateWayService.NOTIFY_SUCCESS;
            }
        }
    }


}