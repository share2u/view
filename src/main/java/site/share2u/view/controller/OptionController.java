package site.share2u.view.controller;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.json.GsonOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.ResponseBO;
import site.share2u.view.service.Context;
import site.share2u.view.service.OptionFactory;
import site.share2u.view.service.OptionService;

import java.util.List;

@Controller
@RequestMapping("/option")
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
    /*
     * 前后端传递大参数
     * 1，可以根据参数直接生成新的option2.已经形成的option要数据库参数加js路径
     *
     */
    @RequestMapping(value = "/recommemd")
    public ResponseBO getTuiJianType(String tableName, List<Dimension> dimensions, List<Measure> measures) {
        ResponseBO rb = new ResponseBO();
        //TODO 从数据库中读取一些信息--可以从做缓存
        List<SeriesType> types = optionService.getTypes(tableName,dimensions, measures);
        rb.setData(JSON.toJSON(types));
        return rb;
    }

    /**
    * @Description: 生成某个option
    * @Author:   chenweimin
    */
    @RequestMapping(value="/option",method = RequestMethod.POST)
    public ResponseBO createOption(SeriesType seriesType,String tableName, List<Dimension> dimensions, List<Measure> measures){
        ResponseBO rb = new ResponseBO();
        Context context = new Context();//优化位置，类似springmvc单例多线程
        context.setSeriesType(getOptionFactory(seriesType));
        GsonOption option = context.generOption(tableName, dimensions, measures);
        rb.setData(option);
        return rb;
    }

    /**
     *  根据OPTION类型引入相应的option实现类
     */
    private OptionFactory getOptionFactory(SeriesType seriesType) {
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
     * @Description: 获得某个option,要根据这个option可以还原到创建当时那个option的信息
     */
    @RequestMapping(value="/option/{id}",method = RequestMethod.GET)
    public ResponseBO createOption(@PathVariable("id") String optionId){
        ResponseBO rb = new ResponseBO();
        return rb;
    }

}
