package com.maizi.anno;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
  * 
  * @ClassName: FilterInfoAnno
  * @Company: 麦子科技
  * @Description: 类级别的查询条件注解
  * @author 技术部-谢维乐
  * @date 2016-3-11 上午11:07:45
  *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterInfoAnno {
	String[] fields();                         //过滤字段
	String [] sorts();                         //排序字段
}
