package com.maizi.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
/**
 * 时间处理工具类
 * 
 * @author Administrator
 * 
 */
public class DateUtil {
	private static final String [] ALL_WEEKS = {"w0","w1","w2","w3","w4","w5","w6"};
	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * 获取当前系统时间之后几天的时间
	 * 
	 * @return
	 */
	public static Date getAfterDate(Date date , Long number){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, (int) +number);//今天的时间加number天
		date = calendar.getTime();
		return date;
	}
	
	
	/**
	 * 将时间转为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date,String format){
		String result = null;
		if(date!=null){
			format = StringUtil.isEmpty(format) == true ?  "yyyy-MM-dd HH:mm:ss" : format;
			 SimpleDateFormat sdf = new SimpleDateFormat(format);
			 result =  sdf.format(date);
		}
		return result;
	}
	
	
	/**
	 * 将字符串转为date
	 * @param param
	 * @param format
	 * @return
	 * @throws KPException
	 */
	public static Date strToDate(String param,String...format) throws KPException{
		SimpleDateFormat sdf;
		if(StringUtil.isEmpty(format)){
			sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else{
			sdf= new SimpleDateFormat(format[0]);
		}
		try {
			return sdf.parse(param);
		} catch (ParseException e) {
			throw new KPException(ExceptionKind.PARSE_E);
		}
	}
	
	/**
	 * 将毫秒时间值装字符串
	 * @param time 时间的毫秒值
	 * @return
	 */
	public static String longDateToString(long time){
		Date tempDate = new Date(time);
		return dateToString(tempDate,null);
	}
	
	
    /**
     * 字符串时间转毫秒值
     * @param strDate
     * @return
     * @throws KPException 
     */
	public static long strDateToLong(String strDate) throws KPException{
	    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    try {
			return formatter.parse(strDate).getTime();
		} catch (ParseException e) {
			throw new KPException(ExceptionKind.PARSE_E);
		}  
	}
	
	/**
	 * 返回毫秒
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}
	
	 
    /**
     * 判断两个日期是否同天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1,Date date2) {
    	 Calendar cal1 = Calendar.getInstance();
         cal1.setTime(date1);

         Calendar cal2 = Calendar.getInstance();
         cal2.setTime(date2);

         boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
         boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
         boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
         return isSameDate;
    }
	
    
    /**
     * 功能:两个日期相隔天数
     * @param startDate
     * @param endDate
     * @return
     * @throws KPException
     */
	public static int getInterval(Date startDate, Date endDate) throws KPException{
	    try {
			Date start = DateUtil.strToDate(DateUtil.dateToString(startDate,"yyyy-MM-dd"),"yyyy-MM-dd");
			Date end = DateUtil.strToDate(DateUtil.dateToString(endDate,"yyyy-MM-dd"),"yyyy-MM-dd");
			return (int) ((end.getTime() - start.getTime()) / (24 * 3600 * 1000));
		} catch (KPException e) {
			throw e;
		}
	}
	
	/**
	 * 功能:两个日期相隔小时[采用四舍五入]
	 * @param startDate  
	 * @param endDate
	 * @return
	 * @throws KPException
	 */
	/*public static float getIntervalHour(Date startDate, Date endDate) throws KPException{
		try {
			Date start = DateUtil.strToDate(DateUtil.dateToString(startDate,"yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss");
			Date end = DateUtil.strToDate(DateUtil.dateToString(endDate,"yyyy-MM-dd  HH:mm:ss"),"yyyy-MM-dd  HH:mm:ss");
			double result =  ArithUtil.div(end.getTime() - start.getTime(),60 * 60 * 1000,1);
		    return (float) result;
		} catch (KPException e) {  
			throw e;
		}
	}*/
	
	/**
	 * 功能:两个日期相隔分钟
	 * @param startDate  
	 * @param endDate
	 * @return
	 * @throws KPException
	 */
	public static int getIntervalMini(Date startDate, Date endDate) throws KPException{
		try {
			Date start = DateUtil.strToDate(DateUtil.dateToString(startDate,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");
			Date end = DateUtil.strToDate(DateUtil.dateToString(endDate,"yyyy-MM-dd  HH:mm"),"yyyy-MM-dd  HH:mm");
			return (int) ((end.getTime() - start.getTime()) / (24 * 60 * 1000));
		} catch (KPException e) {  
			throw e;
		}
	}
	
	
	/**
	 * 功能:两个日期相隔自然时间
	 * @param startDate  开始日期
	 * @param endDate 结束日期
	 * @return 返回相减后的日期
	 */
	public static String diffNatureTime(Date startDate, Date endDate) {
		String result;
		long diffMins = (getMillis(endDate) - getMillis(startDate)) / (60 * 1000);
		if(diffMins/1440>0){
			result = diffMins/1440+"天";
		}else if(diffMins/60>0){
			result = diffMins/60+"小时";
		}else{  
			result = (diffMins==0?"1":diffMins)+"分钟";   
		}
		return result;   
	}
 
	/**
	 * 指定日期加上天数
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 指定日期减去指定天数
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date diffDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) - ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}
	
	
	/**
	 * 获取时间序列
	 * @param currentDate
	 * @param count
	 * @return
	 */
	public static Map<String,String> getDateSearise(Date currentDate,int count){
		Map<String,String> result = new HashMap<String,String>();
		
		result.put("今天",dateToString(getCurrentDate(),"yyyy-MM-dd"));
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		for(int i = 0;i<count;i++){
			calendar.add(Calendar.DAY_OF_MONTH,1);//今天的时间加number天
			System.out.println(dateToString(calendar.getTime(),"yyyy-MM-dd"));
		}
		return result;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getChinaWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == 0) {
			return 7;
		} else {
			return week;
		}
	}
}
