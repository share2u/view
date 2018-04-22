package site.share2u.view.service.impl;

import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Funnel;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Series;
import org.springframework.stereotype.Service;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;
import site.share2u.view.pojo.SeriesTypeView;
import site.share2u.view.service.OptionFactory;
import site.share2u.view.util.CEcharts;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: CWM
 * @date: 2018/4/19.
 */
@Service
public class FunnelOption  implements OptionFactory{
    @Override
    public SeriesTypeView getSupportSeriesType() {
        return new SeriesTypeView(11,"funnel");
    }
    
    @Override
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData) {
        String[] split = tableName.split("@@");
        CEcharts cEcharts = new CEcharts();
        Title title = new Title();
        title.setText(split[1]);
        List<Series> series = new ArrayList<>();
        Series serie = new Funnel();
        serie.setName(dimensions.get(0).getName());
        List<PageData> serieData = new ArrayList<>();
        for (int i = 0; i < optionData.size(); i++) {
            // 单行数据
            PageData pageData = optionData.get(i);
            PageData data1 = new PageData();
            data1.put("name",pageData.get(dimensions.get(0).getName()).toString());
            data1.put("value",pageData.get("agg_" + measures.get(0).getName()));
            serieData.add(data1);
        }
        serie.setData(serieData);
        series.add(serie);
        return  cEcharts.setPieOption(title, null,series);
    }
}
