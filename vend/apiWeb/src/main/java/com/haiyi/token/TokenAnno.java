package com.haiyi.token;

public @interface TokenAnno {
	//是否验证Token true=验证 ,false = 不验证
	public boolean verifyToken() default true;
}