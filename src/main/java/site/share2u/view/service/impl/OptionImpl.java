package site.share2u.view.service.impl;

import com.github.abel533.echarts.code.SeriesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.share2u.view.dao.OptionMapper;
import site.share2u.view.pojo.*;
import site.share2u.view.service.OptionService;
import site.share2u.view.util.MakeSql;

import java.util.*;

/**
 * @author Administrator
 */
@Service("optionService")
public class OptionImpl implements OptionService {
    
    
    @Autowired
    private OptionMapper optionMapper;
    
    @Override
    public OptionView getOption(Integer optionId) {
        return optionMapper.getOption(optionId);
    }
    
    @Override
    public List<OptionVO> getAllOptions() {
        return optionMapper.getAllOptions();
    }
    
    @Override
    public void updateOptionContent(OptionVO optionVO) {
        optionMapper.updateOptionContent(optionVO);
    }
    
    @Override
    public List<String> getTypes(String tableName, List<Dimension> dimensions, List<Measure> measures) {
        DimensionFact dimensionFact = new DimensionFact();
        String sql = MakeSql.getSql(tableName, dimensions);
        long t1 = System.currentTimeMillis();
        List<PageData> optionData = optionMapper.getOptionData(sql);
        long t2 = System.currentTimeMillis();
        System.out.println("数据库执行时间" + (t2 - t1) / 1000F + "秒 ");
        dimensionFact.setDimensionCount(dimensions.size());
        dimensionFact.setMeasureCount(measures.size());
        //1、遍历取出维度的数量 select count(distinct dimension) from tableName;
        //2、维度类型list
        //3、维度计数
        //4、度量计数
        List<Integer> dimensionsType = new ArrayList<>();
        List<Integer> dimensionsSum = new ArrayList<>();
        for (int i = 0; i < optionData.size(); i++) {
            dimensionsSum.add(Integer.parseInt(String.valueOf(optionData.get(i).get("tmp"))));
            dimensionsType.add(dimensions.get(i).getDataType());
        }
        dimensionFact.setDimensionsSum(dimensionsSum);
        dimensionFact.setDimensionsType(dimensionsType);
        return getSeriesTypeByFact(dimensionFact);
    }
    
    private List<String> getSeriesTypeByFact(DimensionFact dimensionFact) {
        Set<String> seriesTypes = new HashSet<>();
        Integer dimensionCount = dimensionFact.getDimensionCount();
        Integer measureCount = dimensionFact.getMeasureCount();
        List<Integer> dimensionsSum = dimensionFact.getDimensionsSum();
        List<Integer> dimensionsType = dimensionFact.getDimensionsType();
        if (dimensionCount == 1 && measureCount == 1 && dimensionsSum.get(0) > 11 ) {
            //折线图
            seriesTypes.add(".C221");
        }
        
        if (dimensionCount == 1 && measureCount == 1 && dimensionsSum.get(0) < 9) {
            //饼图
            seriesTypes.add(".C230");
        }
        //漏斗图
        if (dimensionCount == 1 && measureCount == 1 && dimensionsSum.get(0) < 12) {
            seriesTypes.add(".C330");
            seriesTypes.add(".C210");
        }
        if (dimensionCount == 0 && measureCount == 2) {
            seriesTypes.add(".C280");
        }
        if (dimensionCount == 2 && measureCount == 1  && dimensionsSum.get(0) < 12) {
            //堆叠柱状图
            seriesTypes.add(".C211");
        }
        if (dimensionCount == 2 && measureCount == 1) {
            //堆叠折线图
            seriesTypes.add(".C220");
        
        }
        if (dimensionCount == 1 && measureCount == 2) {
            seriesTypes.add(".C280");
        }
    
        if (dimensionCount == 1 && measureCount > 1) {
            seriesTypes.add(".C250");
            seriesTypes.add(".SOM");
        }
        //表格
        seriesTypes.add(".C200");
        List<String> objects = new ArrayList<>();
        objects.addAll(seriesTypes);
        return objects;
    }
    
    @Override
    public List<OptionView> getOptionByDashboardId(Integer dashboardId) {
        List<OptionView> optionsByDashboardId = optionMapper.getOptionsByDashboardId(dashboardId);
        return optionsByDashboardId;
    }
    
    @Override
    public OptionView saveOption(OptionVO optionVO) {
        if ("SOM".equals(optionVO.getSeriesType())) {
            optionMapper.saveSOMOption(optionVO);
        } else {
            optionMapper.saveOption(optionVO);
        }
        return getOption(optionVO.getId());
    }
    
    @Override
    public void saveDims(List<Dim> dims) {
        optionMapper.saveDims(dims);
    }
    
    
    @Override
    public Boolean deleOption(Integer optionId) {
        optionMapper.deleOption(optionId);
        return true;
    }
    
    
    /**
     * 根据sql 读取数据 字段会乱序
     *
     * @param sql
     * @return
     */
    private List<PageData> getOptionData(String sql) {
        List<PageData> optionData = optionMapper.getOptionData(sql);
        return optionData;
    }
    
    /**
     * 组装sql根据维度与度量
     */
    private static String setSql(String tableName, List<Column> dimension, Map<String, Column> measures) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        if (dimension != null && dimension.size() > 0) {
            for (Column column : dimension) {
                sb.append(column.getColumnName() + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        if (measures != null && measures.size() > 0) {
            if (dimension.size() > 0) {
                sb.append(",");
            }
            Set<String> keySet = measures.keySet();
            for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext(); ) {
                String aggType = iterator.next();// 聚合参数类型
                sb.append(aggType + "(");
                sb.append(measures.get(aggType).getColumnName());
                sb.append(") as " + measures.get(aggType).getColumnName() + "agg,");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(" from ");
        sb.append(tableName);
        if (dimension != null && dimension.size() > 0) {
            sb.append(" group by ");
            for (Column column : dimension) {
                sb.append(column.getColumnName() + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
