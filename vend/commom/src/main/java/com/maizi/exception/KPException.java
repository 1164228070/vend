package com.maizi.exception;

/**
 * 
  * @ClassName: KPException
  * @Company: 麦子科技
  * @Description: 自定义异常
  * @author 技术部-谢维乐
  * @date 2016年4月2日 下午5:19:30
  *
 */
public class KPException extends RuntimeException {

	// 异常信息
	
	public String selfMessage;              //异常消息
	public ExceptionKind exceptionKind;     //自定义异常类型
	public Exception originalException;     //原始异常
	public String originalMessage;          //原始异常消息

	
	/**
	 * 
	 * @param exceptionKind
	 * @param message    
	 */
	public KPException(ExceptionKind exceptionKind,String ...message) {
		super(exceptionKind.getMessage());
		if(message!=null){
			this.selfMessage = exceptionKind.getMessage()+"["+message[0]+"]";
		}
	}
	
	
	/**
	 * 
	 * @param exceptionKind
	 * @param exception
	 * @param message   
	 */
	public KPException(ExceptionKind exceptionKind,Exception exception,String ... message) {
		super(exceptionKind.getMessage());
		if(message!=null){
			this.selfMessage = exceptionKind.getMessage()+"["+message[0]+"]";
		}
		this.originalException = exception;
	}

	public String getSelfMessage() {
		return selfMessage;
	}

	public void setSelfMessage(String selfMessage) {
		this.selfMessage = selfMessage;
	}

	public String getOriginalMessage() {
		return originalMessage;
	}

	public void setOriginalMessage(String originalMessage) {
		this.originalMessage = originalMessage;
	}
}