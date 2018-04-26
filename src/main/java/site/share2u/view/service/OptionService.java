package site.share2u.view.service;

import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.json.GsonOption;
import site.share2u.view.pojo.*;

import java.util.List;
import java.util.Map;

public interface OptionService {
    /**
     * 根据维度和度量推荐图表
     *
     * @return 返回可以采用的图表类型
     */
    List<String> getTypes(String tableName, List<Dimension> types, List<Measure> measures);
    
    
    /**
     * 获取对应仪表盘的option信息
     *
     * @param dashboardId 仪表盘id
     * @return
     */
    List<OptionView> getOptionByDashboardId(Integer dashboardId);
    
    /**
     * 保存option
     *
     * @param optionView
     * @return
     */
    OptionView saveOption(OptionVO optionView);
    
    /**
     * 保存维度组合
     *
     * @param dims
     */
    void saveDims(List<Dim> dims);
    
    /**
     * 删除option
     *
     * @param optionId
     * @return
     */
    Boolean deleOption(Integer optionId);
    
    /**
     * 获取option
     */
    OptionView getOption(Integer optionId);
    
    /**
     * 获取所有的optionVOs
     *
     * @return
     */
    List<OptionVO> getAllOptions();
    
    /**
     * 更新option的内容
     * @param optionVO
     */
    void updateOptionContent(OptionVO optionVO);
    
}
