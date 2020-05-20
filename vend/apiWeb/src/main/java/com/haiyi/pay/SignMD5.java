package com.haiyi.pay;



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

/**
 * MD5签名工具类
 * @author Administrator
 *
 */
public class SignMD5 {
	
	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format(Configure.HEX_FORMAT, b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static String encode(CharSequence charSequence) {
		try {
			MessageDigest crypt = MessageDigest.getInstance(Configure.MD5);
			crypt.reset();
			crypt.update(charSequence.toString().getBytes(Configure.CHARTSET_UTF8));
			return byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Configure.EMPTY;
	}

	/**
	 * 生成 MD5
	 *
	 * @param data 待处理数据
	 * @return MD5结果
	 */
	public static String MD5(String data) throws Exception {
		java.security.MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] array = md.digest(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}


	public boolean matches(CharSequence charSequence, String encodedPassword) {
		return encode(charSequence).equals(encodedPassword);
	}

	public String createNonceStr() {
		return UUID.randomUUID().toString().replaceAll(Configure.MIDDLE_LINE, Configure.EMPTY);
	}

	public String createTimeStamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

}