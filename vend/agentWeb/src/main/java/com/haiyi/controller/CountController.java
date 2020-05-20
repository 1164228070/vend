package com.haiyi.controller;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.ComsumeLog;
import com.haiyi.domain.InsideComsume;
import com.haiyi.query.ComsumeLogQuery;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.service.ComsumeLogService;
import com.haiyi.service.InsideComsumeService;
import com.haiyi.utils.AgentUtil;
import com.haiyi.utils.SessionUtil;
import com.haiyi.utils.UserUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.DateUtil;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    ComsumeLogService comsumeLogService;
    @Autowired
    InsideComsumeService insideComsumeService;

    @RequestMapping(value="", method= RequestMethod.GET)
    @AuthAnno
    public String list(HttpServletRequest request, ModelMap modelMap) throws KPException {
        String type= SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
        if("user".equals(type)){
            modelMap.addAttribute("userId",SessionUtil.getCurrentLoginInfoId(request.getSession()));
        }else{
            modelMap.addAttribute("agentId",SessionUtil.getCurrentLoginInfoId(request.getSession()));
        }
        return "counts/statistics";
    }

    @RequestMapping(value={"/getComsumeMoneyWeekCounts"}, method={RequestMethod.POST})
    public @ResponseBody
    JsonModel getComsumeMoneyWeekCounts(ComsumeLogQuery e, HttpServletRequest request){
        System.out.println(e.toString());
        JsonModel jsonModel = new JsonModel();
        String type= SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
        if("user".equals(type)){
            e.setUserId(UserUtil.getUserId(request.getSession()));
        }else{
            e.setAgentId(AgentUtil.getAgentId(request.getSession()));
        }
        e.setPayStatus((byte) 1);
        e.setPagination(false);

        PageInfo<ComsumeLog> comsumeLogPageInfo = comsumeLogService.findBySelective(e);
        List<ComsumeLog> comsumeLogs = comsumeLogPageInfo.getList();
        Map<String,List<BigDecimal>> counts=new HashMap<>();
        List<BigDecimal> weekMoneys=new ArrayList<>();
        for(int i=0;i<7;i++){
            weekMoneys.add(new BigDecimal(0));
        }
        Date currentDate = DateUtil.getCurrentDate();
        for(ComsumeLog comsumeLog:comsumeLogs){
            int interval = DateUtil.getInterval(comsumeLog.getCreateDate(), currentDate);
            if(interval==0){
                weekMoneys.set(0,weekMoneys.get(0).add(comsumeLog.getPayAmount()));
            }else if(interval==1){
                weekMoneys.set(1,weekMoneys.get(1).add(comsumeLog.getPayAmount()));
            }else if(interval==2){
                weekMoneys.set(2,weekMoneys.get(2).add(comsumeLog.getPayAmount()));
            }else if(interval==3){
                weekMoneys.set(3,weekMoneys.get(3).add(comsumeLog.getPayAmount()));
            }else if(interval==4){
                weekMoneys.set(4,weekMoneys.get(4).add(comsumeLog.getPayAmount()));
            }else if(interval==5){
                weekMoneys.set(5,weekMoneys.get(5).add(comsumeLog.getPayAmount()));
            }else if(interval==6){
                weekMoneys.set(6,weekMoneys.get(6).add(comsumeLog.getPayAmount()));
            }
        }
        counts.put("weekMoneys",weekMoneys);
        jsonModel.setSuccess(true);
        jsonModel.setMsg("周统计成功");
        jsonModel.setData(counts);
        return jsonModel;
    }


    @RequestMapping(value={"/getComsumesCounts"}, method={RequestMethod.POST})
    public @ResponseBody
    JsonModel getComsumesCounts(InsideComsumeQuery e, HttpServletRequest request){
        JsonModel jsonModel = new JsonModel();
        String type= SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
        if("user".equals(type)){
            e.setUserId(UserUtil.getUserId(request.getSession()));
        }else{
            e.setAgentId(AgentUtil.getAgentId(request.getSession()));
        }
        e.setPayStatus((byte) 1);
        e.setPagination(false);
        System.out.println(e.toString());
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


    @RequestMapping(value={"/test"}, method={RequestMethod.GET})
    public void test(){
        Date currentDate = DateUtil.getCurrentDate();
        System.out.println("currentDate=================="+currentDate);
        Date afterDate = DateUtil.getAfterDate(currentDate, (long) 1);
        System.out.println("afterDate=============="+afterDate);
        Date date = DateUtil.strToDate("2020-03-24", "yyyy-MM-dd");
        System.out.println("date================");
        int interval = DateUtil.getInterval(date, currentDate);
        System.out.println("interval================="+interval);
    }









}
