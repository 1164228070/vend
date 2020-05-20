package com.maizi.utils;  
  


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

  
public class LogUtils {  
    
    /** 
     * 记录一直 info信息 
     *  
     * @param message 
     */  
    public static void logInfo(String message,Object ...params) { 
    	LoggerFactory.getLogger(getClassName()).info(message,params);  
    }  
  
    public static void logInfo(String message, Throwable e) {  
        StringBuilder s = new StringBuilder();  
        s.append(("exception : -->>"));  
        s.append((message));  
        LoggerFactory.getLogger(getClassName()).info(s.toString(), e);  
    }  
  
    public static void logWarn(String message,Object ...params) {  
        StringBuilder s = new StringBuilder();  
        s.append((message));  
  
        LoggerFactory.getLogger(getClassName()).warn(s.toString(),params);  
    }  
  
    public static void logWarn(String message, Throwable e) {  
        StringBuilder s = new StringBuilder();  
        s.append(("exception : -->>"));  
        s.append((message));  
        LoggerFactory.getLogger(getClassName()).warn(s.toString(), e);  
    }  
  
    public static void logDebug(String message,Object ...params) {  
        StringBuilder s = new StringBuilder();  
        s.append((message));  
        LoggerFactory.getLogger(getClassName()).debug(s.toString(),params);  
    }  
  
    public static void logDebug(String message, Throwable e) {  
        StringBuilder s = new StringBuilder();  
        s.append(("exception : -->>"));  
        s.append((message));  
        LoggerFactory.getLogger(getClassName()).debug(s.toString(), e);  
    }  
  
    public static void logError(String message,Object ...params) {  
        StringBuilder s = new StringBuilder();  
        s.append(message);  
        LoggerFactory.getLogger(getClassName()).error(s.toString(),params);  
    }  
  
    /** 
     * 记录日志错误信息 
     *  
     * @param message 
     * @param e 
     */  
    public static void logError(String message, Throwable e) {  
        StringBuilder s = new StringBuilder();  
        s.append(("exception : -->>"));  
        s.append((message));  
        LoggerFactory.getLogger(getClassName()).error(s.toString(), e);  
    }  
     
    
 	private static String getClassName() {
 		return new SecurityManager() {
 			public String getClassName() {
 				return getClassContext()[3].getName();
 			}
 		}.getClassName();
 	}
}  