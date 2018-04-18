package site.share2u.view.controller;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.json.GsonOption;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.share2u.view.pojo.*;
import site.share2u.view.service.Context;
import site.share2u.view.service.OptionFactory;
import site.share2u.view.service.OptionService;
import site.share2u.view.service.SerieTypeService;
import site.share2u.view.serviceInfo.SchemaService;

import java.util.ArrayList;
import java.util.List;

@RestController
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
@Autowired
private Context context;
@Autowired
private SerieTypeService serieTypeService;
@Autowired
private SchemaService schemaService;

    private static Logger log = Logger.getLogger(OptionController.class);
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
     * 新增图表界面
     * @param dashboardId
     * @return
     */
    @RequestMapping(value = "/dashborad/create/{id}")
    public ModelAndView createOption(@PathVariable("id")Integer dashboardId){
        //图表的选择
        ModelAndView mav = new ModelAndView();
        List<Table> tables = schemaService.getTables();
        mav.addObject("dashboardId",dashboardId);
        mav.addObject("tables",tables);
        mav.setViewName("createOption");
        return mav;
    }
    /**
     * 知道了图表类型生成选定的option
    */
    @RequestMapping(value="/option/create",method = RequestMethod.POST)
    public ResponseBO createOption(@RequestBody OptionVO optionVO){
        String seriesType=optionVO.getSeriesType();
        String tableName=optionVO.getTableName();
        List<Dimension> dimensions=optionVO.getDimensions();
        List<Measure> measures=optionVO.getMeasures();
        log.info("创建option的数据:"+optionVO);
        ResponseBO rb = new ResponseBO();

        context.setOptionFactory(getOptionFactory(serieTypeService.getNameByName(seriesType)));
        GsonOption option = context.generOption(tableName, dimensions, measures);
        log.info("option数据为:"+option.toString());
        rb.setData(option);
        if(seriesType.equals("C200")){
            rb.setData(option.getTitle().getText());
        }
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
        log.info("根据图表类型（"+seriesType+"）获取option工厂："+optionFactory.getClass());
        return optionFactory;
    }
    /**
     * @Description: 获得某个option
     */
    @RequestMapping(value="/option/{id}",method = RequestMethod.GET)
    public ResponseBO getOption(@PathVariable("id") Integer optionId){
        ResponseBO rb = new ResponseBO();
        OptionView option = optionService.getOption(optionId);
        if(option !=null){
            rb.setData(option);
        }else{
            rb.setReasonMessage("服务器内部错误");
        }
        return rb;
    }
    /**
     * 保存option与维度组合
     */
    @RequestMapping(value="/option/save",method = RequestMethod.POST)
    public ResponseBO saveOption(@RequestBody OptionVO optionVO){
        ResponseBO rb = new ResponseBO();
        //1、保存option
        optionVO.setOption1(optionVO.getOption1().replaceAll("\\s*", ""));
        OptionView optionView = optionService.saveOption(optionVO);
        //2、保存维度组合
        if(optionView !=null){
            rb.setCompleteCode(200);
            rb.setData(optionView);
        }else{
            rb.setCompleteCode(500);
            rb.setReasonMessage("服务器错误");
        }
        return rb;
    }

}
