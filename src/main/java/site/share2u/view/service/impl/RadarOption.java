package site.share2u.view.service.impl;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.json.GsonOption;
import org.springframework.stereotype.Service;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;
import site.share2u.view.pojo.SeriesTypeView;
import site.share2u.view.service.OptionFactory;
import site.share2u.view.util.OptionUtil;

import java.util.*;

/**
 * 两个文本，一个数字
 * 由于echarts中没有版本，暂时不做这个图了
 * @auther: CWM
 * @date: 2018/4/19.
 */
@Service
public class RadarOption implements OptionFactory {
    @Override
    public SeriesTypeView getSupportSeriesType() {
        return new SeriesTypeView(12,"radar");
    }
    
    @Override
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData) {
        Map<String, Set<String>> dimensionEnum = OptionUtil.getEnum(dimensions, optionData);
        GsonOption option = new GsonOption();
        Title title = new Title();
        title.setText("默认的标题名称");
        option.setTitle(title);
        Legend legend = new Legend();
        List<String> legendData = new ArrayList();
        Set<String> dims = dimensionEnum.get(dimensions.get(0).getName());
        Iterator<String> iterator = dims.iterator();
        while ((iterator.hasNext())){
            legendData.add(iterator.next());
        }
        legend.setData(legendData);
        
    
    
        return option;
    }
}
