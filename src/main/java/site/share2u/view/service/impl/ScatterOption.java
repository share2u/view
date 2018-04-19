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
import com.github.abel533.echarts.series.Scatter;
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
 * @auther: CWM
 * @date: 2018/4/19.
 */
@Service
public class ScatterOption implements OptionFactory {
    @Override
    public SeriesTypeView getSupportSeriesType() {
        return new SeriesTypeView(3, "scatter");
    }
    
    /**
     * 散点图，一般是两个度量维度，如果加入一个维度，那就是作为图例，再加入一个度量可以用来散点图的大小
     * 根据优先级排序的话，使用颜色来区分
     *
     * @param tableName
     * @param dimensions
     * @param measures
     * @param optionData
     * @return
     */
    @Override
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData) {
        Map<String, Set<String>> dimensionEnum = OptionUtil.getEnum(dimensions, optionData);
        CEcharts cEcharts = new CEcharts();
        Title title = new Title();
        title.setText("默认的图表标题");
        Legend legend = new Legend();
        Set<String> d1Enum = dimensionEnum.get(dimensions.get(0).getName());
        if (dimensions.size() == 1) {
            // 如果维度为1个，添加维度为图例
            List<Data> legendData = new ArrayList<>();
            for (Object object : d1Enum) {
                legendData.add(new Data(object.toString()));
            }
            legend.setData(legendData);
        }
        List<Axis> xAxis = new ArrayList<>();
        ValueAxis xAxisx = new ValueAxis();
        xAxisx.name("x轴名称").splitLine().lineStyle().type(LineType.dashed);
        xAxis.add(xAxisx);
        // 设置y轴的数据
        List<Axis> yAxis = new ArrayList<>();
        ValueAxis yAxisy = new ValueAxis();
        yAxisy.name("y轴名称").splitLine().lineStyle().type(LineType.dashed);
        yAxis.add(yAxisy);
        
        // 数据区域
        List<Series> series = new ArrayList<>();
        
        if (dimensions.size() == 1) {
            Iterator<String> iter = d1Enum.iterator();
            while (iter.hasNext()) {
                Series scatter = new Scatter();
                String dataName = iter.next();
                scatter.setName(dataName);
                List<List<Object>> serieData = new ArrayList<>();
                for (int i = 0; i < optionData.size(); i++) {
                    // 单行数据
                    PageData pageData = optionData.get(i);
                    if (optionData.get(i).get(dimensions.get(0).getName()).equals(dataName)) {
                        ArrayList<Object> data1 = new ArrayList<Object>();
                        data1.add(pageData.get("agg_" + measures.get(0).getName()));
                        data1.add(pageData.get("agg_" + measures.get(1).getName()));
                        serieData.add(data1);
                    }
                }
                series.add(scatter);
            }
        } else {
            Series scatter = new Scatter();
            List<List<Object>> serieData = new ArrayList<>();
            for (int i = 0; i < optionData.size(); i++) {
                // 单行数据
                PageData pageData = optionData.get(i);
                ArrayList<Object> data1 = new ArrayList<Object>();
                data1.add(pageData.get("agg_" + measures.get(0).getName()));
                data1.add(pageData.get("agg_" + measures.get(1).getName()));
                serieData.add(data1);
            }
            scatter.setData(serieData);
            series.add(scatter);
        }
        return cEcharts.setScatterOption(title, legend, xAxis, yAxis, series);
    }
}
