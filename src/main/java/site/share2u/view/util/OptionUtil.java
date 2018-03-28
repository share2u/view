package site.share2u.view.util;

import site.share2u.view.pojo.Column;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.PageData;

import java.util.*;

/**
 * Created by Administrator on 2018/3/28.
 */
public class OptionUtil {
    public static Map<String, Set<Object>> getEnum(List<Dimension> dimension, List<PageData> data) {
        Map<String, Set<Object>> map = new HashMap<>();
        for (int i = 0; i < dimension.size(); i++) {
            Set set = new HashSet<>();
            map.put(dimension.get(i).getName(), set);
        }
        // 遍历数据拿出维度数据枚举
        for (int i = 0; i < data.size(); i++) {
            PageData pageData = data.get(i);
            for (int j = 0; j < dimension.size(); j++) {
                map.get(dimension.get(j).getName()).add(pageData.get(dimension.get(j).getName()));
            }
        }
        return map;
    }
}
