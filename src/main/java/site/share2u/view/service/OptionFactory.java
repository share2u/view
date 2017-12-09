package site.share2u.view.service;

import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.json.GsonOption;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;

import java.util.List;

/**
 * @Description  option工厂s，产生不同的option工厂
 * @Author chenweimin
 */
public interface OptionFactory {
    public SeriesType getSupportSeriesType();
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData);
}
