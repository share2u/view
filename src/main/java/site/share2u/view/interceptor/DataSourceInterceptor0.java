package site.share2u.view.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import site.share2u.view.util.DatabaseContextHolder;

@Aspect
@Component
@Order(0) // 执行在@transactional之前
public class DataSourceInterceptor0 {
	
	@Pointcut("execution(* site.share2u.view.serviceInfo.*.*(..))")
	private void dataSource0(){};
	
	@Before("dataSource0()")
	public void setDataSource0(JoinPoint jp) {
		DatabaseContextHolder.setDatabaseContext("dataSource0");
	}
}
