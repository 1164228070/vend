package com.haiyi.domain.vo;

import com.haiyi.domain.Dev;
import com.haiyi.domain.Product;

public class ProductVo extends Product {
	private Dev dev;
	public Dev getDev() {
		return dev;
	}
	public void setDev(Dev dev) {
		this.dev = dev;
	}
}
