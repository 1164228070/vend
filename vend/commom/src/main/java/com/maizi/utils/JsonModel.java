package com.maizi.utils;


/**
 * JSON模型类
 * @author Administrator
 *
 */
public class JsonModel{
	private boolean success;    //是否成功
	private String msg;         //描述信息
	private Object data;        //响应信息
	private int total=0;        //总的记录数量
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	 
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
    
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }


	@Override
	public String toString() {
		return "JsonModel{" +
				"success=" + success +
				", msg='" + msg + '\'' +
				", data=" + data +
				", total=" + total +
				'}';
	}
}