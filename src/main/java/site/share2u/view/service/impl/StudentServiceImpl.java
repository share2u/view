package site.share2u.view.service.impl;

import org.springframework.stereotype.Service;

import site.share2u.view.service.StudentService;

@Service("studentService")
public class StudentServiceImpl  implements StudentService{

	@Override
	public int hello(int i) {
		return i;
	}

}
