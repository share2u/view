package site.share2u.view.service.impl;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.axis.Axis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.LineType;
import com.github.abel533.echarts.code.SeriesType;
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
        return SeriesTypeView.LINE;
    }

    @Override
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData) {
        Map<String, Set<Object>> dimensionEnum = OptionUtil.getEnum(dimensions, optionData);
        CEcharts cEcharts = new CEcharts();
        Title title = new Title();
        title.setText("默认的图表标题");
        // 如果维度为两个，添加第二个维度为图例
        Legend legend = null;
        Set<Object> d1Enum =null;
        if (dimensions.size() == 2) {
            d1Enum = dimensionEnum.get(dimensions.get(1).getName());
            legend = new Legend();
            List<Data> legendData = new ArrayList<>();
            for (Object object : d1Enum) {
                legendData.add(new Data(object.toString()));
            }
            legend.setData(legendData);
        }
        // 设置x轴的数据
        List<Axis> xAxis = new ArrayList<>();
        ValueAxis xAxisx = new ValueAxis();
        xAxisx.name("x轴名称").splitLine().lineStyle().type(LineType.dashed);
        if (dimensions.get(0).getDataType().equals("varchar") || dimensions.get(0).getDataType().equals("char")) {
            xAxisx.setType(AxisType.category);// 类目轴必须设置data
        }
        List<Object> d2Enum = new ArrayList<>();// 第二维作为x轴
        d2Enum.addAll(dimensionEnum.get(dimensions.get(0).getName()));
        xAxisx.setData(d2Enum);
        xAxis.add(xAxisx);
        // 设置y轴的数据
        List<Axis> yAxis = new ArrayList<>();
        ValueAxis yAxisy = new ValueAxis();
        yAxisy.name("y轴名称").splitLine().lineStyle().type(LineType.dashed);
        yAxis.add(yAxisy);
        // 数据区域
        List<Series> series = new ArrayList<>();
        if (dimensions.size() > 1) {
            // 创建多系列的数据--d1的个数
            Iterator<Object> d1Iterator = d1Enum.iterator();
            while (d1Iterator.hasNext()) {
                String dimensionName = (String) d1Iterator.next();
                Series scatter = new Line();
                scatter.setName(dimensionName);
                List<List<Object>> serieData = new ArrayList<>();
                for (int i = 0; i < optionData.size(); i++) {
                    PageData pageData = optionData.get(i);// 单行数据
                    if (pageData.get(dimensions.get(1).getName()).equals(dimensionName)) {// 是这个系列
                        List<Object> data1 = new ArrayList<Object>();
                        for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
                            String key = iterator.next();
                            if (dimensionName.equals(pageData.get(key))) {
                                continue;
                            }
                            data1.add(pageData.get(key));
                        }
                        serieData.add(data1);
                    }
                }
                scatter.setData(serieData);
                series.add(scatter);

            }
        } else {
            Series scatter = new Line();
            List<List<Object>> serieData = new ArrayList<>();
            for (int i = 0; i < optionData.size(); i++) {
                PageData pageData = optionData.get(i);// 单行数据
                List<Object> data1 = new ArrayList<Object>();
                for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
                    String key = iterator.next();
                    data1.add(pageData.get(key));
                }
                serieData.add(data1);
            }
            scatter.setData(serieData);
            series.add(scatter);
        }
        return cEcharts.setScatterOption(title, legend, xAxis, yAxis, series);
    }
}
