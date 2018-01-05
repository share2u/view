package site.share2u.view.service.impl;

import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.json.GsonOption;
import site.share2u.view.pojo.Column;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.service.OptionService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/4.
 */
public class OptionDroolsImpl implements OptionService {
    @Override
    public List<SeriesType> getTypes(String tableName,List<Dimension> types, List<Measure> measures) {
        return null;
    }

    @Override
    public GsonOption getOption(String tableName, List<Column> dimension, Map<String, Column> measures, SeriesType seriesType) {
        return null;
    }
}
