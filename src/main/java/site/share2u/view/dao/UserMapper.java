package site.share2u.view.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import site.share2u.view.pojo.PageData;
@Repository
public interface UserMapper {
	List<PageData> getUsers();
}
