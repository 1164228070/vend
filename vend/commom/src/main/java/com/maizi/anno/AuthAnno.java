package com.maizi.anno;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
  * 
  * @ClassName: AuthAnno
  * @Company: 麦子科技
  * @Description: 方法级别注解
  * @author 技术部-谢维乐
  * @date 2016-3-11 上午11:07:12
  *
 */
@Target(java.lang.annotation.ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthAnno {
	
	//判断方式是否有效(默认true:有效 false:无效)
	public boolean  verifyValid() default true;
     
     //是否验证登陆 true=验证 ,false = 不验证
	 public boolean verifyLogin() default true;
	 
	 //是否验证URL true=验证 ,false = 不验证
	 public boolean verifyToken() default true;
	 
	 public String token() default "";   //建议:控制器名_权限名
}