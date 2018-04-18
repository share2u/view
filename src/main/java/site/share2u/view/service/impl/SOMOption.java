package site.share2u.view.service.impl;

import com.github.abel533.echarts.json.GsonOption;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;
import site.share2u.view.pojo.SeriesTypeView;
import site.share2u.view.service.OptionFactory;
import site.share2u.view.util.OptionUtil;
import site.share2u.view.util.TxtUtil;
import site.share2u.view.util.som.Neural_Window;

import java.util.List;

/**
 * @auther: CWM
 * @date: 2018/4/18.
 */
public class SOMOption implements OptionFactory {
    @Override
    public SeriesTypeView getSupportSeriesType() {
        return new SeriesTypeView(10,"SOM");
    }
    
    @Override
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData) {
        StringBuilder sb = new StringBuilder();
        sb.append("hello\thihihii\tkoaksfdoa\nasfsafa\tasfasfa\tafasf\n");
        
        //生成SOM模型,
        Neural_Window.establish_network_type(measures.size(),OptionUtil.pageDate2list(dimensions,optionData));
        //根据模型生成散点图
        return null;
    }
    public static void main(String[] str){
        StringBuilder sb = new StringBuilder();
        sb.append("hello\thihihii\tkoaksfdoa\nasfsafa\tasfasfa\tafasf\n");
        TxtUtil.writeStr("hello",sb.toString());
    }
}
