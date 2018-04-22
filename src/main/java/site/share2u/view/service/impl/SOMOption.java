package site.share2u.view.service.impl;

import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.axis.Axis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.LineType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.series.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.share2u.view.dao.OptionMapper;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;
import site.share2u.view.pojo.SeriesTypeView;
import site.share2u.view.service.OptionFactory;
import site.share2u.view.util.CEcharts;
import site.share2u.view.util.OptionUtil;
import site.share2u.view.util.TxtUtil;
import site.share2u.view.util.som.Neural_Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * @auther: CWM
 * @date: 2018/4/18.
 */

@Service
public class SOMOption implements OptionFactory {
    
    @Autowired
    OptionMapper optionMapper;
    @Override
    public SeriesTypeView getSupportSeriesType() {
        return new SeriesTypeView(10, "SOM");
    }
    
    @Override
    public GsonOption generOption(String tableName, List<Dimension> dimensions, List<Measure> measures, List<PageData> optionData) {
        String[] split1 = tableName.split("@@");
        String pathName = isHappend(split1[0],dimensions, measures);
        if(pathName == null){
            pathName=String.valueOf(System.currentTimeMillis());
            StringBuilder sb = new StringBuilder();
            sb.append(tableName + "\n");
            for (Dimension dim : dimensions) {
                sb.append(dim.getName() + "\t");
            }
            for (Measure mes : measures) {
                sb.append(mes.getName() + "\t");
            }
            sb.append("x\ty\tcategory\n");
            for (int i = 0; i < optionData.size(); i++) {
                Iterator iterator = optionData.get(i).entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry next = (Map.Entry) iterator.next();
                    if (next.getKey().equals(dimensions.get(0).getName())) {
                        sb.append(next.getValue() + "\n");
                    }
                }
            }
            TxtUtil.writeStr(pathName, sb.toString(), false);
            //生成SOM模型,
            Neural_Window.establish_network_type(pathName, measures.size(), OptionUtil.pageDate2list(dimensions, optionData));
        }
       
        
        // 取出文件表的数据封装为optiondata,生成option
        try {
            CEcharts cEcharts = new CEcharts();
            Title title = new Title();
            //挖坑
            title.setText(split1[1]+"@@"+pathName);
            
            List<Axis> xAxis = new ArrayList<>();
            ValueAxis xAxisx = new ValueAxis();
            xAxisx.name(split1[2]).splitLine().lineStyle().type(LineType.dashed);
            xAxis.add(xAxisx);
            // 设置y轴的数据
            List<Axis> yAxis = new ArrayList<>();
            ValueAxis yAxisy = new ValueAxis();
            yAxisy.name(split1[3]).splitLine().lineStyle().type(LineType.dashed);
            yAxis.add(yAxisy);
            
            // 数据区域
            List<Series> series = new ArrayList<>();
            Series scatter = new Scatter();
            List<List<Object>> serieData = new ArrayList<>();
            
            File file = new File("D:\\SOM\\" + pathName + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            br.readLine();
            String strTmp = null;
            int count = dimensions.size() + measures.size();
            while ((strTmp = br.readLine()) != null) {
                // 单行数据
                String[] split = strTmp.split("\t");
                
                ArrayList<Object> data1 = new ArrayList<Object>();
                data1.add(split[count]);
                data1.add(split[count + 1]);
                serieData.add(data1);
            }
            scatter.setData(serieData);
            series.add(scatter);
            return cEcharts.setScatterOption(title, null, xAxis, yAxis, series);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 存在返回地址，不存在为null
     * @param tableName
     * @param dimensions
     * @param measures
     * @return
     */
    private String isHappend(String tableName,List<Dimension> dimensions, List<Measure> measures){
        String pathNameTmp = null;
        List<String> dims = new ArrayList<>();
        for (Dimension d:dimensions) {
            dims.add(d.getName());
        }
        for (Measure m:measures) {
            dims.add(m.getName());
        }
        //1、根据tableName,SOM类型查出list
        //2、将数据放到一个hashmap中，key代表optionid.value代表维度组合的list《string》---进行比较
        List<PageData> pds = optionMapper.getDims(tableName);
        Map<String, List<String>> optionIds = new HashMap<>();
        for (PageData pd: pds) {
            String key = pd.get("pathname").toString();
            if(optionIds.keySet().contains(key)){
                optionIds.get(key).add(pd.get("dim_name").toString());
            }else{
                List<String> strs = new ArrayList<>();
                strs.add(pd.get("dim_name").toString());
                optionIds.put(pd.get("pathname").toString(),strs);
                
            }
        }
        Iterator<Map.Entry<String, List<String>>> entryItr = optionIds.entrySet().iterator();
        
         while(entryItr.hasNext()){
             Map.Entry<String, List<String>> next = entryItr.next();
             List<String> strings = next.getValue();
             if(strings.size() == dims.size()){
                if(strings.containsAll(dims)){
                    pathNameTmp = next.getKey();
                    break;
                }else{
                    continue;
                }
             }else{
                continue;
             }
         }
    
    
        return pathNameTmp;
    }
}
