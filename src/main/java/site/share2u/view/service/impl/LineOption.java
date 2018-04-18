package site.share2u.view.service.impl;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.axis.Axis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.LineType;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Series;
import org.springframework.stereotype.Service;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;
import site.share2u.view.pojo.SeriesTypeView;
import site.share2u.view.service.OptionFactory;
import site.share2u.view.util.CEcharts;
import site.share2u.view.util.OptionUtil;

import java.util.*;

/**
 * @Description 折线图option
 * @Author chenweimin
 */
@Service
public class LineOption implements OptionFactory {
    @Override
    public SeriesTypeView getSupportSeriesType() {
        return new SeriesTypeView(1, "line");
    }
    
    @Override
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData) {
        Map<String, Set<String>> dimensionEnum = OptionUtil.getEnum(dimensions, optionData);
        CEcharts cEcharts = new CEcharts();
        Title title = new Title();
        title.setText("默认的图表标题");
        // 设置x轴的数据
        List<Axis> xAxis = new ArrayList<>();
        ValueAxis xAxisx = new ValueAxis();
        xAxisx.name("x轴名称").splitLine().lineStyle().type(LineType.dashed);
        if (dimensions.get(0).getDataType() == 1 || dimensions.get(0).getDataType() == 7) {
            // 类目轴必须设置data
            xAxisx.setType(AxisType.category);
        }
        
        
        List<String> d0Enum = new ArrayList<>();
       
        
        // 设置y轴的数据
        List<Axis> yAxis = new ArrayList<>();
        ValueAxis yAxisy = new ValueAxis();
        yAxisy.name("y轴名称").splitLine().lineStyle().type(LineType.dashed);
        yAxis.add(yAxisy);
        // 数据区域
        List<Series> series = new ArrayList<>();
        Series scatter = new Line();
        List<List<Object>> serieData = new ArrayList<>();
        for (int i = 0; i < optionData.size(); i++) {
            // 单行数据
            PageData pageData = optionData.get(i);
            ArrayList<Object> data1 = new ArrayList<Object>();
            data1.add(pageData.get(dimensions.get(0).getName()).toString());
            d0Enum.add(pageData.get(dimensions.get(0).getName()).toString());
            data1.add(pageData.get("agg_" + measures.get(0).getName()));
            serieData.add(data1);
        }
        scatter.setData(serieData);
        series.add(scatter);
        xAxisx.setData(d0Enum);
        xAxis.add(xAxisx);
        return cEcharts.setScatterOption(title, null, xAxis, yAxis, series);
    }
}
