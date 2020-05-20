package com.maizi.anno;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
  * 
  * @ClassName: ClassInfoAnno
  * @Company: 麦子科技
  * @Description: 类级别的注解
  * @author 技术部-谢维乐
  * @date 2016-3-11 上午11:07:28
  *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassInfoAnno {
	String msg() default "";                         //用户json 信息提示
	String resourceId()  default "";      //主键
	String dbId()  default "";            //数据库表主键列名
}