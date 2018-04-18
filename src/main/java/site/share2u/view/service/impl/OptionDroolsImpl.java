package site.share2u.view.service.impl;

import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.json.GsonOption;
import site.share2u.view.pojo.*;
import site.share2u.view.service.OptionService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/4.
 */
public class OptionDroolsImpl implements OptionService {
    @Override
    public OptionView getOption(Integer optionId) {
        return null;
    }
    
    @Override
    public List<SeriesType> getTypes(String tableName,List<Dimension> types, List<Measure> measures) {
        return null;
    }



    @Override
    public List<OptionView> getOptionByDashboardId(Integer dashboardId) {
        return null;
    }
    
    @Override
    public OptionView saveOption(OptionVO optionVO) {
        return null;
    }
}
