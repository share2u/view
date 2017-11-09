package site.share2u.view.dao;

import java.util.List;

import site.share2u.view.pojo.PageData;


public interface OptionMapper {
	 List<PageData> getOptionData(String sql);
}
