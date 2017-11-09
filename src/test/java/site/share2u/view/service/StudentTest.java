package site.share2u.view.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import site.share2u.view.BaseJunit4Test;


public class StudentTest extends BaseJunit4Test{
	
	@Test
	public void testHello(){
		/*
		 * ApplicationContext容器对对象的装配时机，会将其中所有的对象一次性全部装配好，执行效率高，占用内存
		 */
		//获取容器
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		//从容器中获取对象
		StudentService studentService = (StudentService) context.getBean("studentService");
		System.out.println(studentService.hello(1));
	}
	
	@Autowired
	StudentService studentService;
	@Test
	public void testHello2(){
		System.out.println(studentService.hello(100));
	}
}
