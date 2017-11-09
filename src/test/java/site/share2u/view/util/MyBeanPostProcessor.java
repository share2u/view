package site.share2u.view.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Bean后处理器，需要在spring容器中声明
 * @author CWM 2017年11月7日
 * 
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println(bean.toString()+"="+beanName+"=要被初始化了");
		return bean;
	}

	/* (non-Javadoc)
	 * bean就是即将要初始化的Bean实例
	 * beanName就是spring容器中的id属性值，若bean没有id就是name属性值
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println(bean.toString()+"="+beanName+"=初始化了");
		return bean;
	}

}
