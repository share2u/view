package site.share2u.view.util;

import site.share2u.view.pojo.Column;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.PageData;

import java.util.*;

/**
 * Created by Administrator on 2018/3/28.
 */
public class OptionUtil {
    /**
     * 获取维度的枚举
     *
     * @param dimension
     * @param data
     * @return
     */
    public static Map<String, Set<String>> getEnum(List<Dimension> dimension, List<PageData> data) {
        Map<String, Set<String>> map = new HashMap<>();
        for (int i = 0; i < dimension.size(); i++) {
            Set set = new HashSet<>();
            map.put(dimension.get(i).getName(), set);
        }
        // 遍历数据拿出维度数据枚举
        for (int i = 0; i < data.size(); i++) {
            PageData pageData = data.get(i);
            for (int j = 0; j < dimension.size(); j++) {
                map.get(dimension.get(j).getName()).add(pageData.get(dimension.get(j).getName()).toString());
            }
        }
        return map;
    }
    
    /**
     * 获取hash排序的维度组合
     *
     * @param dimensions
     * @param measures
     * @return
     */
    public static List<String> getHashDimesion(List<Dimension> dimensions, List<Measure> measures) {
        ArrayList<String> strs = new ArrayList<>();
        HashSet<String> strHash = new HashSet<>();
        for (Dimension d : dimensions) {
            strHash.add(d.getName());
        }
        for (Measure m : measures) {
            strHash.add("agg_" + m.getName());
        }
        Iterator<String> iterator = strHash.iterator();
        while (iterator.hasNext()) {
            strs.add(iterator.next());
        }
        return strs;
    }
    
    /**
     * 从数据库获得的数据转为二维数据
     *
     * @return
     */
    public static List<List<Double>> pageDate2list(List<Dimension> dimensions, List<PageData> optionData) {
        List<List<Double>> lists = new ArrayList<List<Double>>();
        for (int i = 0; i < optionData.size(); i++) {
            ArrayList<Double> doubles = new ArrayList<>();
            Iterator iterator = optionData.get(i).entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry next = (Map.Entry) iterator.next();
                if (next.getKey() == dimensions.get(0).getName()) {
                    continue;
                }
                doubles.add(Double.parseDouble((next.getValue().toString())));
            }
            lists.add(doubles);
        }
        return lists;
    }
}
