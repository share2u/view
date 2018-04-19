package site.share2u.view.controller;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.axis.Axis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.LineType;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.series.Series;
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
import site.share2u.view.util.CEcharts;
import site.share2u.view.util.TxtUtil;
import site.share2u.view.util.kmeans.Kmeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/chart")
public class OptionController {
    static String[] cateName = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P"};
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
    @RequestMapping(value = "/options/{id}")
    @ResponseBody
    public ResponseBO getOptionsByDashboardId(@PathVariable("id") Integer dashboardId) {
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
        List<SeriesType> types = optionService.getTypes(tableName, dimensions, measures);
        rb.setData(JSON.toJSON(types));
        return rb;
    }
    
    /**
     * 新增图表界面
     *
     * @param dashboardId
     * @return
     */
    @RequestMapping(value = "/dashborad/create/{id}")
    public ModelAndView createOption(@PathVariable("id") Integer dashboardId) {
        //图表的选择
        ModelAndView mav = new ModelAndView();
        List<Table> tables = schemaService.getTables();
        mav.addObject("dashboardId", dashboardId);
        mav.addObject("tables", tables);
        mav.setViewName("createOption");
        return mav;
    }
    
    /**
     * 知道了图表类型生成选定的option
     */
    @RequestMapping(value = "/option/create", method = RequestMethod.POST)
    public ResponseBO createOption(@RequestBody OptionVO optionVO) {
        String seriesType = optionVO.getSeriesType();
        String tableName = optionVO.getTableName();
        List<Dimension> dimensions = optionVO.getDimensions();
        List<Measure> measures = optionVO.getMeasures();
        log.info("创建option的数据:" + optionVO);
        ResponseBO rb = new ResponseBO();
        
        context.setOptionFactory(getOptionFactory(serieTypeService.getNameByName(seriesType)));
        GsonOption option = context.generOption(tableName, dimensions, measures);
        log.info("option数据为:" + option.toString());
        rb.setData(option);
        if (seriesType.equals("C200")) {
            rb.setData(option.getTitle().getText());
        }
        return rb;
    }
    
    /**
     * 根据OPTION类型引入相应的option实现类
     */
    private OptionFactory getOptionFactory(SeriesTypeView seriesType) {
        OptionFactory optionFactory = null;
        for (OptionFactory tmpFactory : optionFactories) {
            if (tmpFactory.getSupportSeriesType().equals(seriesType)) {
                optionFactory = tmpFactory;
                break;
            }
        }
        log.info("根据图表类型（" + seriesType + "）获取option工厂：" + optionFactory.getClass());
        return optionFactory;
    }
    
    /**
     * @Description: 获得某个option
     */
    @RequestMapping(value = "/option/{id}", method = RequestMethod.GET)
    public ResponseBO getOption(@PathVariable("id") Integer optionId) {
        ResponseBO rb = new ResponseBO();
        OptionView option = optionService.getOption(optionId);
        if (option != null) {
            rb.setData(option);
        } else {
            rb.setReasonMessage("服务器内部错误");
        }
        return rb;
    }
    
    /**
     * 保存option与维度组合
     */
    @RequestMapping(value = "/option/save", method = RequestMethod.POST)
    public ResponseBO saveOption(@RequestBody OptionVO optionVO) {
        ResponseBO rb = new ResponseBO();
        //1、保存option
        optionVO.setOption1(optionVO.getOption1().replaceAll("\\s*", ""));
        OptionView optionView = optionService.saveOption(optionVO);
        //2、保存维度组合
        if (optionView != null) {
            rb.setCompleteCode(200);
            rb.setData(optionView);
        } else {
            rb.setCompleteCode(500);
            rb.setReasonMessage("服务器错误");
        }
        return rb;
    }
    
    @RequestMapping(value = "/option/kmeanScatter", method = RequestMethod.POST)
    public ResponseBO KmeanScatter(@RequestParam("pathName") String pathName, @RequestParam("k") Integer k) {
        ResponseBO responseBO = new ResponseBO();
        try {
            Kmeans km = new Kmeans(k);
            ArrayList<float[]> dataSet = new ArrayList<float[]>();
            BufferedReader br = new BufferedReader(new FileReader(new File("D:\\som\\" + pathName + ".txt")));
            br.readLine();
            br.readLine();
            String s = null;
            String[] split;
            while ((s = br.readLine()) != null) {
                split = s.split("\t");
                float[] f = new float[2];
                f[0] = Float.parseFloat(split[split.length - 2]);
                f[1] = Float.parseFloat(split[split.length - 1]);
                dataSet.add(f);
            }
            br.close();
            // 设置原始数据集
            km.setDataSet(dataSet);
            // 执行算法
            km.execute();
            // 得到聚类结果
            ArrayList<ArrayList<float[]>> cluster = km.getCluster();
            ArrayList<float[]> center = km.getCenter();
            StringBuilder sbb = new StringBuilder();
            BufferedReader brr = new BufferedReader(new FileReader(new File("D:\\som\\" + pathName + ".txt")));
            String tableName = brr.readLine();
            String axis = brr.readLine();
            String ss = null;
            sbb.append(tableName + "\n");
            sbb.append(axis + "\n");
            int c = 0;
            while ((ss = brr.readLine()) != null) {
                sbb.append(ss + "\t" + distance(center, dataSet.get(c)) + "\n");
                c++;
            }
            brr.close();
            TxtUtil.writeStr(pathName + "_" + k, sbb.toString(), false);
            
            //画一个散点图，代标签和一个雷达图
            CEcharts cEcharts = new CEcharts();
            Title title = new Title();
            title.setText("默认的图表标题");
            
            List<Axis> xAxis = new ArrayList<>();
            ValueAxis xAxisx = new ValueAxis();
            xAxisx.name("x轴名称").splitLine().lineStyle().type(LineType.dashed);
            xAxis.add(xAxisx);
            // 设置y轴的数据
            List<Axis> yAxis = new ArrayList<>();
            ValueAxis yAxisy = new ValueAxis();
            yAxisy.name("y轴名称").splitLine().lineStyle().type(LineType.dashed);
            yAxis.add(yAxisy);
            // 数据区域
            List<Series> series = new ArrayList<>();
            List<List<Object>> serieDataTmp = new ArrayList<>();
            BufferedReader brrr = new BufferedReader(new FileReader(new File("D:\\som\\" + pathName + "_" + k + ".txt")));
            brrr.readLine();
            brrr.readLine();
            String strTmp = null;
            while ((strTmp = brrr.readLine()) != null) {
                // 单行数据
                String[] split1 = strTmp.split("\t");
                ArrayList<Object> data1 = new ArrayList<Object>();
                data1.add(split1[split1.length - 3]);
                data1.add(split1[split1.length - 2]);
                data1.add(split1[split1.length - 1]);
                serieDataTmp.add(data1);
            }
            brrr.close();
            ArrayList<String> ca = new ArrayList();
            for (int i = 0; i < center.size(); i++) {
                Series scatter = new Scatter();
                scatter.setName(cateName[i]);
                ca.add(cateName[i]);
                List<List<Object>> serieData = new ArrayList<>();
                for (int m = 0; m < serieDataTmp.size(); m++) {
                    if (serieDataTmp.get(m).get(2).equals(cateName[i])) {
                        ArrayList<Object> data1 = new ArrayList<Object>();
                        data1.add(serieDataTmp.get(m).get(0));
                        data1.add(serieDataTmp.get(m).get(1));
                        serieData.add(data1);
                    }
                }
                scatter.setData(serieData);
                series.add(scatter);
            }
            Legend legend = new Legend();
            legend.setData(ca);
            GsonOption gsonOption = cEcharts.setScatterOption(title, legend, xAxis, yAxis, series);
            responseBO.setData(gsonOption);
            //TODO : 生成平行坐标图，保存的话就保存平行坐标图
            return responseBO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private String distance(ArrayList<float[]> center, float[] data) {
        float mindis = 10;
        String s = "";
        for (int i = 0; i < center.size(); i++) {
            if (distance(center.get(i), data) < mindis) {
                mindis = distance(center.get(i), data);
                s = cateName[i];
            }
        }
        return s;
    }
    
    private float distance(float[] element, float[] center) {
        float distance = 0.0f;
        float x = element[0] - center[0];
        float y = element[1] - center[1];
        float z = x * x + y * y;
        distance = (float) Math.sqrt(z);
        return distance;
    }
}
