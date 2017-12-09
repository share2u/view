package site.share2u.view.service.impl;

import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.json.GsonOption;
import org.springframework.stereotype.Service;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;
import site.share2u.view.service.OptionFactory;

import java.util.List;

/**
 * @Description
 * @Author chenweimin
 */
@Service
public class LineOption implements OptionFactory {
    @Override
    public SeriesType getSupportSeriesType() {
        return SeriesType.line;
    }

    @Override
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData) {
        return null;
    }
}
