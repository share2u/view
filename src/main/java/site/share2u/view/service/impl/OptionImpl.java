package site.share2u.view.service.impl;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.axis.Axis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.LineType;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.series.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.share2u.view.dao.OptionMapper;
import site.share2u.view.pojo.Column;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;
import site.share2u.view.service.OptionService;
import site.share2u.view.util.CEcharts;
import site.share2u.view.util.MakeSql;

import java.util.*;

@Service("optionService")
public class OptionImpl implements OptionService {

	
	@Autowired
	private OptionMapper optionMapper;

	@Override
	public List<SeriesType> getTypes(List<Dimension> dimensions, List<Measure> measures) {
		ArrayList<SeriesType> seriesTypes = new ArrayList<SeriesType>();
		//TODO 判断推荐的类型--重点
		/*
		int size = types.size();
		if(measures.size()>0){
			switch (size) {
			case 0:
				
				break;
			case 1:
				if(types.contains("date")||types.contains("datetime")){
					seriesTypes.add(SeriesType.line);
					seriesTypes.add(SeriesType.bar);
				}else {
					seriesTypes.add(SeriesType.bar);
					seriesTypes.add(SeriesType.line);
					seriesTypes.add(SeriesType.scatter); 
				}
				break;
			case 2:
				if(types.contains("date")||types.contains("datetime")){
					seriesTypes.add(SeriesType.line);
					seriesTypes.add(SeriesType.bar);
				}else{
					seriesTypes.add(SeriesType.bar);
					seriesTypes.add(SeriesType.line);
				}
				break;
			case 3:
				
				break;
			default:
				
				break;
			}
		}*/
		return seriesTypes;
	}

	@Override
	public GsonOption getOption(String tableName, List<Dimension> dimensions, List<Measure> measures){
		// 1.组装sql
		String sql = MakeSql.getSql(tableName, dimensions, measures);
		System.out.println(sql);
		// 2.取得数据
		List<PageData> data = getOptionData(sql);
		return null;
	}
	@Override
	public GsonOption getOption(String tableName, List<Column> dimension, Map<String, Column> measures,
			SeriesType seriesType) {
		// 1.组装sql
		String sql = setSql(tableName, dimension, measures);
		System.out.println(sql);
		// 2.取得数据
		List<PageData> data = getOptionData(sql);
		// 3.封装option
		GsonOption option = setOption(dimension, measures, seriesType, data);
		return option;
	}

	private Map<String, Set<Object>> getEnum(List<Column> dimension, List<PageData> data) {
		Map<String, Set<Object>> map = new HashMap<>();
		for (int i = 0; i < dimension.size(); i++) {
			Set set = new HashSet<>();
			map.put(dimension.get(i).getColumnName(), set);
		}
		// 遍历数据拿出维度数据枚举
		for (int i = 0; i < data.size(); i++) {
			PageData pageData = data.get(i);
			for (int j = 0; j < dimension.size(); j++) {
				map.get(dimension.get(j).getColumnName()).add(pageData.get(dimension.get(j).getColumnName()));
			}
		}

		return map;
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
	 * 画图
	 * 
	 * @param dimension
	 * @param measures
	 * @param seriesType
	 * @param data
	 * @return
	 */
	private GsonOption setOption(List<Column> dimension, Map<String, Column> measures, SeriesType seriesType,
			List<PageData> data) {
		GsonOption option = null;
		switch (seriesType) {
		case line:
			option = setLineOption(dimension, measures, data);
			break;
		case bar:
			option = setBarOption(dimension, measures, data);
			break;
		case scatter:
			option = setScatterOption(dimension, measures, data);
			break;

		default:
			break;
		}
		return option;
	}

	/**
	 * 折线图
	 */
	private GsonOption setLineOption(List<Column> dimension, Map<String, Column> measures, List<PageData> data) {
		Map<String, Set<Object>> dimensionEnum = getEnum(dimension, data);
		CEcharts cEcharts = new CEcharts();
		Title title = new Title();
		title.setText("默认的图表标题");
		// 如果维度为两个，添加第二个维度为图例
		Legend legend = null;
		Set<Object> d1Enum =null;
		if (dimension.size() == 2) {
			d1Enum = dimensionEnum.get(dimension.get(1).getColumnName());
			legend = new Legend();
			List<Data> legendData = new ArrayList<>();
			for (Object object : d1Enum) {
				legendData.add(new Data(object.toString()));
			}
			legend.setData(legendData);
		}
		// 设置x轴的数据
		List<Axis> xAxis = new ArrayList<>();
		ValueAxis xAxisx = new ValueAxis();
		xAxisx.name("x轴名称").splitLine().lineStyle().type(LineType.dashed);
		if (dimension.get(0).getDataType().equals("varchar") || dimension.get(0).getDataType().equals("char")) {
			xAxisx.setType(AxisType.category);// 类目轴必须设置data
		}
		List<Object> d2Enum = new ArrayList<>();// 第二维作为x轴
		d2Enum.addAll(dimensionEnum.get(dimension.get(0).getColumnName()));
		xAxisx.setData(d2Enum);
		xAxis.add(xAxisx);
		// 设置y轴的数据
		List<Axis> yAxis = new ArrayList<>();
		ValueAxis yAxisy = new ValueAxis();
		yAxisy.name("y轴名称").splitLine().lineStyle().type(LineType.dashed);
		yAxis.add(yAxisy);
		// 数据区域
		List<Series> series = new ArrayList<>();
		if (dimension.size() > 1) {
			// 创建多系列的数据--d1的个数
			Iterator<Object> d1Iterator = d1Enum.iterator();
			while (d1Iterator.hasNext()) {
				String dimensionName = (String) d1Iterator.next();
				Series scatter = new Line();
				scatter.setName(dimensionName);
				List<List<Object>> serieData = new ArrayList<>();
				for (int i = 0; i < data.size(); i++) {
					PageData pageData = data.get(i);// 单行数据
					if (pageData.get(dimension.get(1).getColumnName()).equals(dimensionName)) {// 是这个系列
						List<Object> data1 = new ArrayList<Object>();
						for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
							String key = iterator.next();
							if (dimensionName.equals(pageData.get(key))) {
								continue;
							}
							data1.add(pageData.get(key));
						}
						serieData.add(data1);
					}
				}
				scatter.setData(serieData);
				series.add(scatter);

			}
		} else {
			Series scatter = new Line();
			List<List<Object>> serieData = new ArrayList<>();
			for (int i = 0; i < data.size(); i++) {
				PageData pageData = data.get(i);// 单行数据
				List<Object> data1 = new ArrayList<Object>();
				for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
					String key = iterator.next();
					data1.add(pageData.get(key));
				}
				serieData.add(data1);
			}
			scatter.setData(serieData);
			series.add(scatter);
		}
		return cEcharts.setScatterOption(title, legend, xAxis, yAxis, series);

	}
	/**
	 * 柱状图
	 */
	private GsonOption setBarOption(List<Column> dimension, Map<String, Column> measures, List<PageData> data) {
		Map<String, Set<Object>> dimensionEnum = getEnum(dimension, data);
		CEcharts cEcharts = new CEcharts();
		Title title = new Title();
		title.setText("默认的图表标题");
		// 如果维度为两个，添加第二个维度为图例
		Legend legend = null;
		Set<Object> d1Enum =null;
		if (dimension.size() == 2) {
			d1Enum = dimensionEnum.get(dimension.get(1).getColumnName());
			legend = new Legend();
			List<Data> legendData = new ArrayList<>();
			for (Object object : d1Enum) {
				legendData.add(new Data(object.toString()));
			}
			legend.setData(legendData);
		}
		// 设置x轴的数据
		List<Axis> xAxis = new ArrayList<>();
		ValueAxis xAxisx = new ValueAxis();
		xAxisx.name("x轴名称").splitLine().lineStyle().type(LineType.dashed);
		if (dimension.get(0).getDataType().equals("varchar") || dimension.get(0).getDataType().equals("char")) {
			xAxisx.setType(AxisType.category);// 类目轴必须设置data
		}
		List<Object> d2Enum = new ArrayList<>();// 第二维作为x轴
		d2Enum.addAll(dimensionEnum.get(dimension.get(0).getColumnName()));
		xAxisx.setData(d2Enum);
		xAxis.add(xAxisx);
		// 设置y轴的数据
		List<Axis> yAxis = new ArrayList<>();
		ValueAxis yAxisy = new ValueAxis();
		yAxisy.name("y轴名称").splitLine().lineStyle().type(LineType.dashed);
		yAxis.add(yAxisy);
		// 数据区域
		List<Series> series = new ArrayList<>();
		if (dimension.size() > 1) {
			// 创建多系列的数据--d1的个数
			Iterator<Object> d1Iterator = d1Enum.iterator();
			while (d1Iterator.hasNext()) {
				String dimensionName = (String) d1Iterator.next();
				Series scatter = new Bar();
				scatter.setName(dimensionName);
				List<List<Object>> serieData = new ArrayList<>();
				for (int i = 0; i < data.size(); i++) {
					PageData pageData = data.get(i);// 单行数据
					if (pageData.get(dimension.get(1).getColumnName()).equals(dimensionName)) {// 是这个系列
						List<Object> data1 = new ArrayList<Object>();
						for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
							String key = iterator.next();
							if (dimensionName.equals(pageData.get(key))) {
								continue;
							}
							data1.add(pageData.get(key));
						}
						serieData.add(data1);
					}
				}
				scatter.setData(serieData);
				series.add(scatter);
				
			}
		} else {
			Series scatter = new Bar();
			List<List<Object>> serieData = new ArrayList<>();
			for (int i = 0; i < data.size(); i++) {
				PageData pageData = data.get(i);// 单行数据
				List<Object> data1 = new ArrayList<Object>();
				for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
					String key = iterator.next();
					data1.add(pageData.get(key));
				}
				serieData.add(data1);
			}
			scatter.setData(serieData);
			series.add(scatter);
		}
		return cEcharts.setScatterOption(title, legend, xAxis, yAxis, series);
		
	}
	/**
	 * 散点图
	 */
	private GsonOption setScatterOption(List<Column> dimension, Map<String, Column> measures, List<PageData> data) {
		Map<String, Set<Object>> dimensionEnum = getEnum(dimension, data);
		CEcharts cEcharts = new CEcharts();
		Title title = new Title();
		title.setText("默认的图表标题");
		// 如果维度为两个，添加第二个维度为图例
		Legend legend = null;
		Set<Object> d1Enum =null;
		if (dimension.size() == 2) {
			d1Enum = dimensionEnum.get(dimension.get(1).getColumnName());
			legend = new Legend();
			List<Data> legendData = new ArrayList<>();
			for (Object object : d1Enum) {
				legendData.add(new Data(object.toString()));
			}
			legend.setData(legendData);
		}
		// 设置x轴的数据
		List<Axis> xAxis = new ArrayList<>();
		ValueAxis xAxisx = new ValueAxis();
		xAxisx.name("x轴名称").splitLine().lineStyle().type(LineType.dashed);
		if (dimension.get(0).getDataType().equals("varchar") || dimension.get(0).getDataType().equals("char")) {
			xAxisx.setType(AxisType.category);// 类目轴必须设置data
		}
		List<Object> d2Enum = new ArrayList<>();// 第二维作为x轴
		d2Enum.addAll(dimensionEnum.get(dimension.get(0).getColumnName()));
		xAxisx.setData(d2Enum);
		xAxis.add(xAxisx);
		// 设置y轴的数据
		List<Axis> yAxis = new ArrayList<>();
		ValueAxis yAxisy = new ValueAxis();
		yAxisy.name("y轴名称").splitLine().lineStyle().type(LineType.dashed);
		yAxis.add(yAxisy);
		// 数据区域
		List<Series> series = new ArrayList<>();
		if (dimension.size() > 1) {
			// 创建多系列的数据--d1的个数
			Iterator<Object> d1Iterator = d1Enum.iterator();
			while (d1Iterator.hasNext()) {
				String dimensionName = (String) d1Iterator.next();
				Series scatter = new Scatter();
				scatter.setName(dimensionName);
				List<List<Object>> serieData = new ArrayList<>();
				for (int i = 0; i < data.size(); i++) {
					PageData pageData = data.get(i);// 单行数据
					if (pageData.get(dimension.get(1).getColumnName()).equals(dimensionName)) {// 是这个系列
						List<Object> data1 = new ArrayList<Object>();
						for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
							String key = iterator.next();
							if (dimensionName.equals(pageData.get(key))) {
								continue;
							}
							data1.add(pageData.get(key));
						}
						serieData.add(data1);
					}
				}
				scatter.setData(serieData);
				series.add(scatter);
				
			}
		} else {
			Series scatter = new Scatter();
			List<List<Object>> serieData = new ArrayList<>();
			for (int i = 0; i < data.size(); i++) {
				PageData pageData = data.get(i);// 单行数据
				List<Object> data1 = new ArrayList<Object>();
				for (Iterator<String> iterator = pageData.keySet().iterator(); iterator.hasNext();) {
					String key = iterator.next();
					data1.add(pageData.get(key));
				}
				serieData.add(data1);
			}
			scatter.setData(serieData);
			series.add(scatter);
		}
		return cEcharts.setScatterOption(title, legend, xAxis, yAxis, series);
		
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
			if (dimension.size() > 0)
				sb.append(",");
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
