package site.share2u.view.service.impl;

import com.alibaba.fastjson.JSON;
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
 * @auther: CWM
 * @date: 2018/4/16.
 */
@Service
public class TableAOption implements OptionFactory {
    @Override
    public SeriesTypeView getSupportSeriesType() {
        return new SeriesTypeView(9,"tableA");
    }
    
    @Override
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData) {
       
        GsonOption option = new GsonOption();
        Title title = new Title();
        List<List<Object>> array = new ArrayList<>();
        for (PageData pageData:optionData){
            ArrayList<Object> objects = new ArrayList<>();
            for(int i=0;i<dimensions.size();i++){
                objects.add(pageData.get(dimensions.get(i).getName()));
            }
            for(int i=0;i<measures.size();i++){
                objects.add(pageData.get("agg_"+measures.get(i).getName()));
            }
            
            array.add(objects);
        }
        title.setText(JSON.toJSON(array).toString());
        option.setTitle(title);
        return option;
    }
}
