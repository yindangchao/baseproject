package heyhou.com.baseproject.base.ex;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 给activity的每个控件变量定义注解进行初始化
 * @author lb
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {
	//控件id
	int id() default -1;
	//是否绑定onClick事件
	boolean onClick() default false;
	//visiable or gone
	boolean visiable() default true;
}
