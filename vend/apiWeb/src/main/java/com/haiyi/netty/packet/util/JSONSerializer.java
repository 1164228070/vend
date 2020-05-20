package com.haiyi.netty.packet.util;

import com.alibaba.fastjson.JSON;
import com.maizi.utils.LogUtils;

public class JSONSerializer implements Serializer {
	@Override
	public byte getSerializerAlgorithm() {
		return SerializerAlgorithm.JSON;
	}

	@Override
	public byte[] serialize(Object object) {
		LogUtils.logError("响应====="+JSON.toJSONString(object));
		return JSON.toJSONString(object).getBytes();
	}

	@Override
	public <T> T deserialize(Class<T> clazz, byte[] bytes) {
		LogUtils.logError("请求====="+new String(bytes));
		return JSON.parseObject(bytes, clazz);
	}
}