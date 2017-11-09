package site.share2u.view.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import site.share2u.view.BaseJunit4Test;
import site.share2u.view.pojo.PageData;

public class UserTest extends BaseJunit4Test{

	@Autowired
	private UserService userService;
	@Test
	public void getUsersTest(){
		List<PageData> users = userService.getUsers();
		System.out.println(users);
	}
}

