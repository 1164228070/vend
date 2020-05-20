package com.maizi.enums;

/**
 * 体现方式枚举
 * @author Administrator
 *
 */
public enum CashTypeEnum {
	
	WX(0,"微信"),
	ZFB(1,"支付宝"),
	YL(2,"银联");
	
	CashTypeEnum(int code,String name){
		this.code = code;
		this.name = name;
	}
	
    public static String getNameByCode(int code) {
        for (CashTypeEnum temp : CashTypeEnum.values()) {
            if (code == temp.getCode()) {
                return temp.getName();
            }
        }
        return null;
    }

	private int code;
	private String name;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
