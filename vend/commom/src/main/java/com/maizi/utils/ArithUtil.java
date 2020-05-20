package com.maizi.utils;
import java.math.BigDecimal;
/**
 * 精度工具类
 * @author Administrator
 *
 */
public class ArithUtil {
	
	//默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;
	
	/**
	 * 相加
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal add(double v1,double v2){
	    BigDecimal b1 = new BigDecimal(Double.toString(v1));	
	    BigDecimal b2 = new BigDecimal(Double.toString(v2));	
	    return b1.add(b2);
	}
	
	/**
	 * 相减
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal sub(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));	
		BigDecimal b2 = new BigDecimal(Double.toString(v2));	
		return b1.subtract(b2);
	}
	
	/**
	 * 相乘
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));	
		BigDecimal b2 = new BigDecimal(Double.toString(v2));	
		return b1.multiply(b2);
	}
	
	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static BigDecimal div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * @param v1  被除数
	 * @param v2  除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static BigDecimal div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static BigDecimal round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("Thescale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}
};