package site.share2u.view.service;

import com.github.abel533.echarts.json.GsonOption;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;

import java.util.List;

/**
 * @Description
 * @Author chenweimin
 */
public class Context {
    private OptionService optionService;

    public void setSeriesType(OptionService optionService) {
        this.optionService = optionService;
    }

    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures){
      return  optionService.getOption(tableName,dimensions,measures);
    }
}
