package com.haiyi.netty.packet.util;

/**
 * 自定义序列化接口
 * @author Administrator
 *
 */
public interface Serializer {
	 
	byte JSON_SERIALIZER = 1;
	
    Serializer DEFAULT = new JSONSerializer();
			

	byte getSerializerAlgorithm();

	/**
	 * java 对象转换成二进制
	 */
	byte[] serialize(Object object);

	/**
	 * 二进制转换成 java 对象
	 */
	<T> T deserialize(Class<T> clazz, byte[] bytes);
}