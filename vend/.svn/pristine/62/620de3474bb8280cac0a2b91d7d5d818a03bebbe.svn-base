package com.haiyi.netty.code;


import com.haiyi.netty.packet.util.BasePacket;
import com.haiyi.netty.packet.util.Command;
import com.haiyi.netty.packet.util.JSONSerializer;
import com.haiyi.netty.packet.util.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 编码解码工具类
 * 
 * @author Administrator
 *
 */
public class CodeUtil {

	public static final int MAGIC_NUMBER = 0x1234567;

	/**
	 * 编码
	 * 
	 * @param packet
	 * @return
	 */
	public static ByteBuf encode(ByteBuf byteBuf, BasePacket packet) {
		// 1. 创建 ByteBuf 对象
		//ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

		// 2. 序列化 Java 对象
		byte[] bytes = Serializer.DEFAULT.serialize(packet);

		// 3. 实际编码过程
		//byteBuf.writeInt(MAGIC_NUMBER);

		//byteBuf.writeByte(packet.getVersion());
		//byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
		//byteBuf.writeByte(packet.getCommand());
		//byteBuf.writeInt(bytes.length);
		byteBuf.writeBytes(bytes);

		return byteBuf;
	}

	public static BasePacket decode(ByteBuf byteBuf) {
		// 跳过 magic number
		byteBuf.skipBytes(4);
		// 跳过版本号
		byteBuf.skipBytes(1);
		// 序列化算法标识
		byte serializeAlgorithm = byteBuf.readByte();
		// 指令
		byte command = byteBuf.readByte();
		// 数据包长度
		int length = byteBuf.readInt();
		byte[] bytes = new byte[length];
		byteBuf.readBytes(bytes);
		
		Class<? extends BasePacket> requestType = Command.getRequestType(command);
		
		Serializer serializer = new JSONSerializer();
		if (requestType != null && serializer != null) {
			return serializer.deserialize(requestType, bytes);
		}
		return null;
	}
	 

}