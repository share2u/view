package site.share2u.view.controller;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.json.GsonOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.share2u.view.pojo.*;
import site.share2u.view.service.Context;
import site.share2u.view.service.OptionFactory;
import site.share2u.view.service.OptionService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chart")
public class OptionController {
/*
 * 1.根据维度等获得推荐类型
 * 	参数需要有---表名，维度的list,度量的list
 * 2.根据图表类型，维度等信息画图
 * 3.保存option?
 */

@Autowired
private OptionService optionService;
@Autowired
private List<OptionFactory> optionFactories;

    /**
     * 获取仪表盘的多个option内容
     */
    @RequestMapping(value="/options/{id}")
    @ResponseBody
    public ResponseBO getOptionsByDashboardId(@PathVariable("id")Integer dashboardId){
        ResponseBO responseBO = new ResponseBO();
        List<OptionView> options = optionService.getOptionByDashboardId(dashboardId);
        responseBO.setData(options);
        return responseBO;
    }


    //推荐的图表类型
    @RequestMapping(value = "/recommemd")
    public ResponseBO getTuiJianType(String tableName, List<Dimension> dimensions, List<Measure> measures) {
        ResponseBO rb = new ResponseBO();
        //TODO 从数据库中读取一些信息--可以从做缓存
        List<SeriesType> types = optionService.getTypes(tableName,dimensions, measures);
        rb.setData(JSON.toJSON(types));
        return rb;
    }

    /**
     * 知道了图表类型生成选定的option
    */
    @RequestMapping(value="/option/create",method = RequestMethod.POST)
    public ResponseBO createOption(@RequestBody OptionVO optionVO){
        Integer seriesType=optionVO.getSeriesType();
        String tableName=optionVO.getTableName();
        List<Dimension> dimensions=optionVO.getDimensions();
        List<Measure> measures=optionVO.getMeasures();

        ResponseBO rb = new ResponseBO();

        Context context = new Context();//优化位置，类似springmvc单例多线程
        context.setOptionFactory(getOptionFactory(SeriesTypeView.getName(seriesType)));
        GsonOption option = context.generOption(tableName, dimensions, measures);

        rb.setData(option);
        return rb;
    }

    /**
     *  根据OPTION类型引入相应的option实现类
     */
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
    /**
     * @Description: 获得某个option
     */
    @RequestMapping(value="/option/{id}",method = RequestMethod.GET)
    public ResponseBO getOption(@PathVariable("id") String optionId){
        ResponseBO rb = new ResponseBO();

        return rb;
    }
    /**
     * 保存option
     */
    @RequestMapping(value="/option/save",method = RequestMethod.POST)
    public ResponseBO saveOption(OptionView option){
        ResponseBO rb = new ResponseBO();

        return rb;
    }

}
