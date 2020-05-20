package com.haiyi.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.maizi.utils.LogUtils;

@Aspect
public class LogAop {
	
	@Pointcut("execution(* com.haiyi.service..*Impl.*(..))")
	public void pointcut(){
	}
	
	@Around(value = "pointcut()")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String className = proceedingJoinPoint.getTarget().getClass().getName(); 
	    String methodName = proceedingJoinPoint.getSignature().getName(); 
	 
	    Object[] args = proceedingJoinPoint.getArgs(); 
	
	    LogUtils.logInfo("开始service[{}]的[{}]方法,参数[{}]",className,methodName,Arrays.toString(args));
	  
		Object result = null;
		try {
			result = proceedingJoinPoint.proceed();
		} catch (Exception e) {
			LogUtils.logInfo("调用service[{}]的[{}]方法,出错[{}]",className,methodName,e.getMessage());
		}
		LogUtils.logInfo("结束service[{}]的[{}]方法,返回值[{}]",className,methodName,result);
		return result;
	}
}
