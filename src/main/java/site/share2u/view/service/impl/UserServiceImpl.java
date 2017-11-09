package site.share2u.view.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.share2u.view.dao.UserMapper;
import site.share2u.view.pojo.PageData;
import site.share2u.view.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userDao;
	@Override
	public List<PageData> getUsers() {
		return userDao.getUsers();
	}

}
