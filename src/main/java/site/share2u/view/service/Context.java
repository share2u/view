package site.share2u.view.service;

import com.github.abel533.echarts.json.GsonOption;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;
import site.share2u.view.util.MakeSql;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 建造者模式
 * @Author chenweimin
 */
@Component
public class Context {
    private OptionFactory optionFactory;
    @Autowired
    private  OptionDataService optionDataService;

    public OptionFactory getOptionFactory() {
        return optionFactory;
    }

    /**
     * 设置optionfactory
     * @param optionFactory
     */
    public void setOptionFactory(OptionFactory optionFactory) {
        this.optionFactory = optionFactory;
    }



    /**
     * 已知图表类型生成相应option
     * 1、组装sql
     * 2、通过sql提取数据
     * 3、创建option通过维度组合、数据
     * @param tableName
     * @param dimensions
     * @param measures
     * @return
     */
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures){
        String sql = MakeSql.getSql(tableName, dimensions, measures);
        List<PageData> optionData = optionDataService.getOptionData(sql);
        return  optionFactory.generOption(tableName,dimensions,measures,optionData);
    }


}
