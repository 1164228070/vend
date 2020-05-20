package com.haiyi.controller;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.ComsumeLog;
import com.haiyi.domain.InsideComsume;
import com.haiyi.domain.RechargeLog;
import com.haiyi.query.ComsumeLogQuery;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.query.RechargeLogQuery;
import com.haiyi.service.*;
import com.maizi.anno.AuthAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.DateUtil;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/counts")
public class CountController {
    @Autowired
    MemberService memberService;
    @Autowired
    AgentService agentService;
    @Autowired
    UserService userService;
    @Autowired
    DevService devService;
    @Autowired
    RechargeLogService rechargeLogService;
    @Autowired
    ComsumeLogService comsumeLogService;
    @Autowired
    InsideComsumeService insideComsumeService;

    @RequestMapping(value="", method= RequestMethod.GET)
    @AuthAnno
    public String list() throws KPException {
        return "counts/statistics";
    }
    @RequestMapping(value="manageCounts", method= RequestMethod.POST)
    @AuthAnno
    public @ResponseBody  JsonModel manageCounts() throws KPException {
        JsonModel jsonModel=new JsonModel();
        Map<String,Integer> counts=new HashMap<>();
        //int members = memberService.findAll().size();
        int agents = agentService.findAll().size();
        int users = userService.findAll().size();
        int devs = devService.findAll().size();
        //counts.put("members",members);
        counts.put("agents",agents);
        counts.put("users",users);
        counts.put("devs",devs);
        jsonModel.setSuccess(true);
        jsonModel.setMsg("数目查询成功");
        jsonModel.setData(counts);
        return jsonModel;
    }



    @RequestMapping(value={"/getMemberWeekCounts"}, method={RequestMethod.POST})
    public @ResponseBody
    JsonModel getMemberWeekCounts(RechargeLogQuery e, HttpServletRequest request){
        JsonModel jsonModel = new JsonModel();
        e.setPagination(false);
        e.setPayStatus((byte)1);
        PageInfo<RechargeLog> rechargeLogPageInfo = rechargeLogService.findBySelective(e);
        List<RechargeLog> rechargeLogs = rechargeLogPageInfo.getList();
        Map<String, List<BigDecimal>> counts=new HashMap<>();
        List<BigDecimal> weekMoneys=new ArrayList<>();
        for(int i=0;i<7;i++){
            weekMoneys.add(new BigDecimal(0));
        }
        Date currentDate = DateUtil.getCurrentDate();
        for(RechargeLog rechargeLog:rechargeLogs){
            int interval = DateUtil.getInterval(rechargeLog.getCreateDate(), currentDate);
            if(interval==0){
                weekMoneys.set(0,weekMoneys.get(0).add(rechargeLog.getRecharge_momey()));
            }else if(interval==1){
                weekMoneys.set(1,weekMoneys.get(1).add(rechargeLog.getRecharge_momey()));
            }else if(interval==2){
                weekMoneys.set(2,weekMoneys.get(2).add(rechargeLog.getRecharge_momey()));
            }else if(interval==3){
                weekMoneys.set(3,weekMoneys.get(3).add(rechargeLog.getRecharge_momey()));
            }else if(interval==4){
                weekMoneys.set(4,weekMoneys.get(4).add(rechargeLog.getRecharge_momey()));
            }else if(interval==5){
                weekMoneys.set(5,weekMoneys.get(5).add(rechargeLog.getRecharge_momey()));
            }else if(interval==6){
                weekMoneys.set(6,weekMoneys.get(6).add(rechargeLog.getRecharge_momey()));
            }
        }
        counts.put("weekMoneys",weekMoneys);
        jsonModel.setSuccess(true);
        jsonModel.setMsg("周统计成功");
        jsonModel.setData(counts);
        return jsonModel;
    }
    @RequestMapping(value={"/getMemberMonthCounts"}, method={RequestMethod.POST})
    public @ResponseBody
    JsonModel getMemberMonthCounts(RechargeLogQuery e, HttpServletRequest request){
        JsonModel jsonModel = new JsonModel();
        e.setPagination(false);
        e.setPayStatus((byte)1);
        PageInfo<RechargeLog> rechargeLogPageInfo = rechargeLogService.findBySelective(e);
        List<RechargeLog> rechargeLogs = rechargeLogPageInfo.getList();
        Map<String, List<BigDecimal>> counts=new HashMap<>();
        List<BigDecimal> monthMoneys=new ArrayList<>();
        for(int i=0;i<5;i++){
            monthMoneys.add(new BigDecimal(0));
        }
        Date currentDate = DateUtil.getCurrentDate();
        for(RechargeLog rechargeLog:rechargeLogs){
            int interval = DateUtil.getInterval(rechargeLog.getCreateDate(), currentDate);
            if(interval>=28 && interval<35){
                monthMoneys.set(0,monthMoneys.get(0).add(rechargeLog.getRecharge_momey()));
            }else if(interval>=21 && interval<28){
                monthMoneys.set(1,monthMoneys.get(1).add(rechargeLog.getRecharge_momey()));
            }else if(interval>=14 && interval<21){
                monthMoneys.set(2,monthMoneys.get(2).add(rechargeLog.getRecharge_momey()));
            }else if(interval>=7 && interval<14){
                monthMoneys.set(3,monthMoneys.get(3).add(rechargeLog.getRecharge_momey()));
            }else if(interval>=0 && interval<7){
                monthMoneys.set(4,monthMoneys.get(4).add(rechargeLog.getRecharge_momey()));
            }
        }
        counts.put("monthMoneys",monthMoneys);
        jsonModel.setSuccess(true);
        jsonModel.setMsg("周统计成功");
        jsonModel.setData(counts);
        return jsonModel;
    }


    @RequestMapping(value={"/getComsumesCounts"}, method={RequestMethod.POST})
    public @ResponseBody
    JsonModel getComsumesCounts(InsideComsumeQuery e, HttpServletRequest request){
        JsonModel jsonModel = new JsonModel();
        e.setPagination(false);
        e.setPayStatus((byte)1);
        PageInfo<InsideComsume> comsumeLogPageInfo = insideComsumeService.findBySelective(e);
        List<InsideComsume> comsumeLogs = comsumeLogPageInfo.getList();
        Map<String,Object> counts=new HashMap<>();

        List<String> times=new ArrayList<>();
        List<Integer> orders=new ArrayList<>();
        List<BigDecimal> totalMoneys=new ArrayList<>();
        List<BigDecimal> totalProfits=new ArrayList<>();
        Date date = DateUtil.strToDate(e.getStart(), "yyyy-MM-dd");
        String strDate=DateUtil.dateToString(date,"yyyy-MM-dd");
        times.add(strDate);
        Integer order=0;
        BigDecimal totalMoney=new BigDecimal(0);
        BigDecimal totalProfit=new BigDecimal(0);
        Integer allOrder=0;
        BigDecimal allMoney=new BigDecimal(0);
        BigDecimal allProfit=new BigDecimal(0);
        int i=0;
        for(InsideComsume comsumeLog:comsumeLogs){
            allOrder++;
            allMoney=allMoney.add(comsumeLog.getPrice());
            allProfit=allProfit.add(comsumeLog.getPrice().subtract(comsumeLog.getCost()));
            int interval = DateUtil.getInterval(date, comsumeLog.getCreateDate());
            if(interval==0){
                order++;
                totalMoney=totalMoney.add(comsumeLog.getPrice());
                totalProfit=totalProfit.add(comsumeLog.getPrice().subtract(comsumeLog.getCost()));
            }else if(interval==1){
                date=DateUtil.getAfterDate(date, (long) 1);
                strDate=DateUtil.dateToString(date,"yyyy-MM-dd");
                times.add(strDate);
                orders.add(order);
                totalMoneys.add(totalMoney);
                totalProfits.add(totalProfit);
                order=1;
                totalMoney=comsumeLog.getPrice();
                totalProfit=comsumeLog.getPrice().subtract(comsumeLog.getCost());
            }else {
                orders.add(order);
                totalMoneys.add(totalMoney);
                totalProfits.add(totalProfit);
                for(i=interval;i>0;i--){
                    date=DateUtil.getAfterDate(date, (long) 1);
                    strDate=DateUtil.dateToString(date,"yyyy-MM-dd");
                    times.add(strDate);
                    if(i==1){
                        order=1;
                        totalMoney=comsumeLog.getPrice();
                        totalProfit=comsumeLog.getPrice().subtract(comsumeLog.getCost());
                    }else {
                        orders.add(0);
                        totalMoneys.add(BigDecimal.valueOf(0));
                        totalProfits.add(BigDecimal.valueOf(0));
                    }

                }
            }

        }
        orders.add(order);
        totalMoneys.add(totalMoney);
        totalProfits.add(totalProfit);
        Date endDate = DateUtil.strToDate(e.getEnd(), "yyyy-MM-dd");
        int endDay = DateUtil.getInterval(date, endDate);
        if(endDay!=0){
            for(i=0;i<endDay;i++){
                date=DateUtil.getAfterDate(date, (long) 1);
                strDate=DateUtil.dateToString(date,"yyyy-MM-dd");
                times.add(strDate);
                orders.add(0);
                totalMoneys.add(BigDecimal.valueOf(0));
                totalProfits.add(BigDecimal.valueOf(0));
            }
        }
        counts.put("times",times);
        counts.put("orders",orders);
        counts.put("totalMoneys",totalMoneys);
        counts.put("totalProfits",totalProfits);
        counts.put("allOrder",allOrder);
        counts.put("allMoney",allMoney);
        counts.put("allProfit",allProfit);
        jsonModel.setSuccess(true);
        jsonModel.setMsg("统计成功");
        jsonModel.setData(counts);
        return jsonModel;
    }




}
