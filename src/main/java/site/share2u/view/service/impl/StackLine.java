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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 适合两个维度，一个度量
 *
 * @auther: CWM
 * @date: 2018/4/4.
 */
@Service
public class StackLine implements OptionFactory {
    private static Logger log =LoggerFactory.getLogger(StackLine.class);
    @Override
    public SeriesTypeView getSupportSeriesType() {
        return new SeriesTypeView(7, "stackLine");
    }
    
    /**
     * 1、得到数据库数据的正确顺序
     * 2、标题
     * 3、图例
     * 4、x轴
     * 5、y轴
     * 6、series数据区
     *
     * @param tableName  表名
     * @param dimensions 维度s
     * @param measures   度量s
     * @param optionData hash排序的数据
     * @return
     */
    @Override
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData) {
//        List<String> hashDimesion = OptionUtil.getHashDimesion(dimensions, measures);
        Map<String, Set<Object>> dimensionEnum = OptionUtil.getEnum(dimensions, optionData);
        
        CEcharts cEcharts = new CEcharts();
        Title title = new Title();
        title.setText("默认的图表标题");
        
        // 如果维度为两个，添加第二个维度为图例
        Legend legend = new Legend();
        Set<Object> d1Enum = dimensionEnum.get(dimensions.get(1).getName());
        List<Data> legendData = new ArrayList<>();
        for (Object object : d1Enum) {
            legendData.add(new Data(object.toString()));
        }
        legend.setData(legendData);
        
        // 设置x轴的数据
        List<Axis> xAxis = new ArrayList<>();
        ValueAxis xAxisx = new ValueAxis();
        xAxisx.name("x轴名称").splitLine().lineStyle().type(LineType.dashed);
        if (dimensions.get(0).getDataType() == 1 || dimensions.get(0).getDataType() == 7) {
            // 类目轴必须设置data
            xAxisx.setType(AxisType.category);
        }
        
        // 设置y轴的数据
        List<Axis> yAxis = new ArrayList<>();
        ValueAxis yAxisy = new ValueAxis();
        yAxisy.name("y轴名称").splitLine().lineStyle().type(LineType.dashed);
        yAxis.add(yAxisy);
        
        // 第0维作为x轴
        List<Object> d0Enum = new ArrayList<>();
        d0Enum.addAll(dimensionEnum.get(dimensions.get(0).getName()));
        xAxisx.setData(d0Enum);
        xAxis.add(xAxisx);
        
        // 数据区域
        List<Series> series = new ArrayList<>();
        // 创建多系列的数据--d1的个数
        Iterator<Object> d1Iterator = d1Enum.iterator();
        while (d1Iterator.hasNext()) {
            Series scatter = new Line();
            //系列名称
            String dimensionName = d1Iterator.next().toString();
            scatter.setName(dimensionName);
            
           
            Iterator<Object> d0Iterator = d0Enum.iterator();
            List<Object> data1 = new ArrayList<>();
            for (int j=0;j<d0Enum.size();j++){
                data1.add(0);
            }
            int count=0;
            while(d0Iterator.hasNext()) {
                //数组长度
                String d0 = d0Iterator.next().toString();
               for (int i = 0; i < optionData.size(); i++) {
                    // 单行数据
                    PageData pageData = optionData.get(i);
                    // 是这个系列就对应放到x轴上
                   if(pageData.get(dimensions.get(1).getName()).toString().equals(dimensionName) && pageData.get(dimensions.get(0).getName()).toString().equals(d0)){
                       data1.set(count,pageData.get("agg_"+measures.get(0).getName()));
                   }
               }
                count++;
            }
          
            scatter.setData(data1);
            series.add(scatter);
            
        }
        
        
        return cEcharts.setScatterOption(title, legend, xAxis, yAxis, series);
    }
}