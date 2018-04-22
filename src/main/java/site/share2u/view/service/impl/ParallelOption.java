package site.share2u.view.service.impl;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.axis.ParallelAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Parallel;
import com.github.abel533.echarts.series.Series;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;
import site.share2u.view.pojo.SeriesTypeView;
import site.share2u.view.service.OptionFactory;
import site.share2u.view.util.CEcharts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @auther: CWM
 * @date: 2018/4/19.
 */
@Service
public class ParallelOption implements OptionFactory {
    @Override
    public SeriesTypeView getSupportSeriesType() {
        return new SeriesTypeView(6,"parallel");
    }
    
    @Override
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData) {
        String[] split = tableName.split("@@");
        CEcharts cEcharts = new CEcharts();
        Title title = new Title();
        title.setText(split[1]);
        List<List<Object>> data =  new ArrayList<>();
        List<ParallelAxis> parallelAxis = new ArrayList<>();
        for(int i=0;i<dimensions.size();i++){
            ParallelAxis parallelAxis1 = new ParallelAxis();
            parallelAxis1.setDim(i);
            parallelAxis1.setType(AxisType.category);
            parallelAxis1.setName(dimensions.get(i).getName());
            parallelAxis.add(parallelAxis1);
        }
        for(int j=0;j<measures.size();j++){
            ParallelAxis parallelAxis1 = new ParallelAxis();
            parallelAxis1.setDim(j+dimensions.size());
            parallelAxis1.setName("agg_"+measures.get(j).getName());
            parallelAxis.add(parallelAxis1);
        }
        for (int i=0;i<optionData.size();i++){
            PageData pageData = optionData.get(i);
            List<Object> dims = new ArrayList<>();
            for(int j=0;j<dimensions.size();j++){
               dims.add(pageData.get(dimensions.get(j).getName()));
            }
            for(int j=0;j<measures.size();j++){
                dims.add(pageData.get("agg_"+measures.get(j).getName()));
            }
            data.add(dims);
        }
        return  cEcharts.setParallelOption(title,data,parallelAxis);
    }
}
