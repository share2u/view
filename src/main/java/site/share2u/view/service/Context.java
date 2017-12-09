package site.share2u.view.service;

import com.github.abel533.echarts.json.GsonOption;
import org.springframework.beans.factory.annotation.Autowired;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;
import site.share2u.view.util.MakeSql;

import java.util.List;

/**
 * @Description
 * @Author chenweimin
 */
public class Context {
    private OptionFactory optionFactory;
    @Autowired
    private OptionDataService optionDataService;

    public void setSeriesType(OptionFactory optionFactory) {
        this.optionFactory = optionFactory;
    }

    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures){
        String sql = MakeSql.getSql(tableName, dimensions, measures);
        List<PageData> optionData = optionDataService.getOptionData(sql);
        return  optionFactory.generOption(tableName,dimensions,measures,optionData);
    }


}
