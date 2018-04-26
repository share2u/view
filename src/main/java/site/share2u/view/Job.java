package site.share2u.view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.json.GsonOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import site.share2u.view.pojo.*;
import site.share2u.view.service.Context;
import site.share2u.view.service.OptionFactory;
import site.share2u.view.service.OptionService;
import site.share2u.view.service.SerieTypeService;

import java.util.List;

/**
 * @auther: CWM
 * @date: 2018/4/23.
 */
public class Job {
    @Autowired
    private OptionService optionService;
    @Autowired
    private List<OptionFactory> optionFactories;
    @Autowired
    private Context context;
    @Autowired
    private SerieTypeService serieTypeService;
    
    /**
     * 1、optionids---2、optionid+dims
     */
    public void execute() {
        
        List<OptionVO> options = optionService.getAllOptions();
        for (OptionVO optionVO : options) {
            String seriesType = optionVO.getSeriesType();
            JSONObject option1 = JSON.parseObject(optionVO.getOption1());
            
            String tableName = optionVO.getTableName() + "@@" + "标题" + "@@" + "x轴" + "@@" + "y轴";
            List<Dimension> dimensions = optionVO.getDimensions();
            List<Measure> measures = optionVO.getMeasures();
            
            context.setOptionFactory(getOptionFactory(serieTypeService.getNameByName(seriesType)));
            GsonOption option = context.generOption(tableName, dimensions, measures);
            optionVO.setOption1(option.toString().replaceAll("\\s*", ""));
            optionService.updateOptionContent(optionVO);
        }
    }
    
    private OptionFactory getOptionFactory(SeriesTypeView seriesType) {
        OptionFactory optionFactory = null;
        for (OptionFactory tmpFactory : optionFactories) {
            if (tmpFactory.getSupportSeriesType().equals(seriesType)) {
                optionFactory = tmpFactory;
                break;
            }
        }
        return optionFactory;
    }
}
