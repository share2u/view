package site.share2u.view.service.impl;

import com.github.abel533.echarts.code.SeriesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.share2u.view.dao.OptionMapper;
import site.share2u.view.pojo.*;
import site.share2u.view.service.OptionService;
import site.share2u.view.util.MakeSql;

import java.util.*;

@Service("optionService")
public class OptionImpl implements OptionService {


	@Autowired
	private OptionMapper optionMapper;
    
    @Override
    public OptionView getOption(Integer optionId) {
        return optionMapper.getOption(optionId);
    }
    
    @Override
	public List<SeriesType> getTypes(String tableName,List<Dimension> dimensions, List<Measure> measures) {
		DimensionFact dimensionFact = new DimensionFact();
		String sql = MakeSql.getSql(tableName, dimensions);
		long t1 = System.currentTimeMillis();
		List<PageData> optionData = optionMapper.getOptionData(sql);
		long t2 = System.currentTimeMillis();
		System.out.println("数据库执行时间"+(t2-t1)/1000F+"秒 ");
		dimensionFact.setDimensionCount(dimensions.size());
		dimensionFact.setMeasureCount(measures.size());
		//1、遍历取出维度的数量 select count(distinct dimension) from tableName;
		//2、维度类型list
		//3、维度计数
		//4、度量计数
		List<Integer> dimensionsType =  new ArrayList<>();
		List<Integer> dimensionsSum = new ArrayList<>();
		for (int i =0;i<optionData.size();i++) {
			dimensionsSum.add(Integer.parseInt(String.valueOf(optionData.get(i).get("tmp"))));
			dimensionsType.add(dimensions.get(i).getDataType());
		}
		dimensionFact.setDimensionsSum(dimensionsSum);
		dimensionFact.setDimensionsType(dimensionsType);
		return getSeriesTypeByFact(dimensionFact);
	}

	private List<SeriesType> getSeriesTypeByFact(DimensionFact dimensionFact){
		List<SeriesType> seriesTypes = new ArrayList<SeriesType>();
		Integer dimensionCount = dimensionFact.getDimensionCount();
		Integer measureCount = dimensionFact.getMeasureCount();
		List<Integer> dimensionsSum = dimensionFact.getDimensionsSum();
		List<Integer> dimensionsType = dimensionFact.getDimensionsType();
		//纵向柱状图
		if(dimensionCount == 1 && measureCount ==1 && dimensionsSum.get(0)<=12){
			seriesTypes.add(SeriesType.bar);
		}
		//横向柱状图
		if(dimensionCount == 1 && measureCount ==1 && dimensionsSum.get(0)>12){
			seriesTypes.add(SeriesType.bar);
		}
		//堆叠柱状图
		if(dimensionCount ==2 && measureCount ==1){
			seriesTypes.add(SeriesType.bar);
		}
		//散点图
		if(measureCount==2 && (dimensionCount==0 || dimensionCount ==1)){
			seriesTypes.add(SeriesType.scatter);
		}
		//气泡图
		if(measureCount==3 && dimensionCount ==1){
			seriesTypes.add(SeriesType.scatter);
		}
		//漏斗图
		if(dimensionCount ==1 && measureCount==1 && dimensionsSum.get(0)<=12){
			seriesTypes.add(SeriesType.funnel);
		}
		//折线图
		if(dimensionCount==0 && measureCount ==2){
			seriesTypes.add(SeriesType.line);
		}
		if (dimensionCount==1 && measureCount ==1){
			seriesTypes.add(SeriesType.line);
		}
		//面积图
		if(dimensionCount ==1 && measureCount ==2){
			seriesTypes.add(SeriesType.line);
		}
		//饼图
		if(dimensionCount==1 && measureCount==1 && dimensionsSum.get(0)<9){
			seriesTypes.add(SeriesType.pie);
		}
		//平行坐标图
		if(dimensionCount+measureCount>2){
			seriesTypes.add(SeriesType.parallel);
		}
		return seriesTypes;
	}
//	@Override
//	public GsonOption getOption(String tableName, List<Column> dimension, Map<String, Column> measures,
//			SeriesType seriesType) {
//		// 1.组装sql
//		String sql = setSql(tableName, dimension, measures);
//		System.out.println(sql);
//		// 2.取得数据
//		List<PageData> data = getOptionData(sql);
//		// 3.封装option
//		GsonOption option = setOption(dimension, measures, seriesType, data);
//		return option;
//	}

	@Override
	public List<OptionView> getOptionByDashboardId(Integer dashboardId) {
		List<OptionView> optionsByDashboardId = optionMapper.getOptionsByDashboardId(dashboardId);
		return optionsByDashboardId;
	}
	
	@Override
	public OptionView saveOption(OptionVO optionVO) {
		 optionMapper.saveOption(optionVO);
		return getOption(optionVO.getId());
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
	 * 柱状图
	 */
//	private GsonOption setBarOption(List<Column> dimension, Map<String, Column> measures, List<PageData> data) {
//		Map<String, Set<Object>> dimensionEnum = getEnum(dimension, data);
//		CEcharts cEcharts = new CEcharts();
//		Title title = new Title();
//		title.setText("默认的图表标题");
//		// 如果维度为两个，添加第二个维度为图例
//		Legend legend = null;
//		Set<Object> d1Enum =null;
//		if (dimension.size() == 2) {
//			d1Enum = dimensionEnum.get(dimension.get(1).getColumnName());
//			legend = new Legend();
//			List<Data> legendData = new ArrayList<>();
//			for (Object object : d1Enum) {
//				legendData.add(new Data(object.toString()));
//			}
//			legend.setData(legendData);
//		}
//		// 设置x轴的数据
//		List<Axis> xAxis = new ArrayList<>();
//		ValueAxis xAxisx = new ValueAxis();
//		xAxisx.name("x轴名称").splitLine().lineStyle().type(LineType.dashed);
//		if (dimension.get(0).getDataType().equals("varchar") || dimension.get(0).getDataType().equals("char")) {
//			xAxisx.setType(AxisType.category);// 类目轴必须设置data
//		}
//		List<Object> d2Enum = new ArrayList<>();// 第二维作为x轴
//		d2Enum.addAll(dimensionEnum.get(dimension.get(0).getColumnName()));
//		xAxisx.setData(d2Enum);
//		xAxis.add(xAxisx);
//		// 设置y轴的数据
//		List<Axis> yAxis = new ArrayList<>();
//		ValueAxis yAxisy = new ValueAxis();
//		yAxisy.name("y轴名称").splitLine().lineStyle().type(LineType.dashed);
//		yAxis.add(yAxisy);
//		// 数据区域
//		List<Series> series = new ArrayList<>();
//		if (dimension.size() > 1) {
//			// 创建多系列的数据--d1的个数
//			Iterator<Object> d1Iterator = d1Enum.iterator();
//			while (d1Iterator.hasNext()) {
//				String dimensionName = (String) d1Iterator.next();
//				Series scatter = new Bar();
//				scatter.setName(dimensionName);
//				List<List<Object>> serieData = new ArrayList<>();
//				for (int i = 0; i < data.size(); i++) {
//					PageData pageData = data.get(i);// 单行数据
//					if (pageData.get(dimension.get(1).getColumnName()).equals(dimensionName)) {// 是这个系列
//						List<Object> data1 = new ArrayList<Object>();
//						for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
//							String key = iterator.next();
//							if (dimensionName.equals(pageData.get(key))) {
//								continue;
//							}
//							data1.add(pageData.get(key));
//						}
//						serieData.add(data1);
//					}
//				}
//				scatter.setData(serieData);
//				series.add(scatter);
//
//			}
//		} else {
//			Series scatter = new Bar();
//			List<List<Object>> serieData = new ArrayList<>();
//			for (int i = 0; i < data.size(); i++) {
//				PageData pageData = data.get(i);// 单行数据
//				List<Object> data1 = new ArrayList<Object>();
//				for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
//					String key = iterator.next();
//					data1.add(pageData.get(key));
//				}
//				serieData.add(data1);
//			}
//			scatter.setData(serieData);
//			series.add(scatter);
//		}
//		return cEcharts.setScatterOption(title, legend, xAxis, yAxis, series);
//
//	}
	/**
	 * 散点图
	 */
//	private GsonOption setScatterOption(List<Column> dimension, Map<String, Column> measures, List<PageData> data) {
//		Map<String, Set<Object>> dimensionEnum = getEnum(dimension, data);
//		CEcharts cEcharts = new CEcharts();
//		Title title = new Title();
//		title.setText("默认的图表标题");
//		// 如果维度为两个，添加第二个维度为图例
//		Legend legend = null;
//		Set<Object> d1Enum =null;
//		if (dimension.size() == 2) {
//			d1Enum = dimensionEnum.get(dimension.get(1).getColumnName());
//			legend = new Legend();
//			List<Data> legendData = new ArrayList<>();
//			for (Object object : d1Enum) {
//				legendData.add(new Data(object.toString()));
//			}
//			legend.setData(legendData);
//		}
//		// 设置x轴的数据
//		List<Axis> xAxis = new ArrayList<>();
//		ValueAxis xAxisx = new ValueAxis();
//		xAxisx.name("x轴名称").splitLine().lineStyle().type(LineType.dashed);
//		if (dimension.get(0).getDataType().equals("varchar") || dimension.get(0).getDataType().equals("char")) {
//			xAxisx.setType(AxisType.category);// 类目轴必须设置data
//		}
//		List<Object> d2Enum = new ArrayList<>();// 第二维作为x轴
//		d2Enum.addAll(dimensionEnum.get(dimension.get(0).getColumnName()));
//		xAxisx.setData(d2Enum);
//		xAxis.add(xAxisx);
//		// 设置y轴的数据
//		List<Axis> yAxis = new ArrayList<>();
//		ValueAxis yAxisy = new ValueAxis();
//		yAxisy.name("y轴名称").splitLine().lineStyle().type(LineType.dashed);
//		yAxis.add(yAxisy);
//		// 数据区域
//		List<Series> series = new ArrayList<>();
//		if (dimension.size() > 1) {
//			// 创建多系列的数据--d1的个数
//			Iterator<Object> d1Iterator = d1Enum.iterator();
//			while (d1Iterator.hasNext()) {
//				String dimensionName = (String) d1Iterator.next();
//				Series scatter = new Scatter();
//				scatter.setName(dimensionName);
//				List<List<Object>> serieData = new ArrayList<>();
//				for (int i = 0; i < data.size(); i++) {
//					PageData pageData = data.get(i);// 单行数据
//					if (pageData.get(dimension.get(1).getColumnName()).equals(dimensionName)) {// 是这个系列
//						List<Object> data1 = new ArrayList<Object>();
//						for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
//							String key = iterator.next();
//							if (dimensionName.equals(pageData.get(key))) {
//								continue;
//							}
//							data1.add(pageData.get(key));
//						}
//						serieData.add(data1);
//					}
//				}
//				scatter.setData(serieData);
//				series.add(scatter);
//
//			}
//		} else {
//			Series scatter = new Scatter();
//			List<List<Object>> serieData = new ArrayList<>();
//			for (int i = 0; i < data.size(); i++) {
//				PageData pageData = data.get(i);// 单行数据
//				List<Object> data1 = new ArrayList<Object>();
//				for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
//					String key = iterator.next();
//					data1.add(pageData.get(key));
//				}
//				serieData.add(data1);
//			}
//			scatter.setData(serieData);
//			series.add(scatter);
//		}
//		return cEcharts.setScatterOption(title, legend, xAxis, yAxis, series);
//
//	}

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
			for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
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
