package site.share2u.view.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import site.share2u.view.pojo.Dim;
import site.share2u.view.pojo.OptionVO;
import site.share2u.view.pojo.OptionView;
import site.share2u.view.pojo.PageData;

@Repository
public interface OptionMapper {
	List<PageData> getOptionData(String sql);
	List<OptionView> getOptionsByDashboardId(Integer dashboardId);
	void saveOption(OptionVO optionVO);
	void saveSOMOption(OptionVO optionVO);
	void saveDims(List<Dim> dims);
	int deleOption(Integer optionId);
	OptionView getOption(Integer optionId);
	
	List<PageData> getDims(String tableName);
}
