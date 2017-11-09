package site.share2u.view.service.impl;

import org.springframework.stereotype.Service;

import site.share2u.view.service.StudentService;

@Service("studentService")
public class StudentServiceImpl  implements StudentService{

	
	public StudentServiceImpl() {
		super();
		System.out.println("接口实现类的构造方法");
	}

	@Override
	public int hello(int i) {
		return i;
	}

}
