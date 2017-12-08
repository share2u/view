package site.share2u.view.util;

import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;

import java.util.List;

/**
 * @Description
 * @Author chenweimin
 */
public class MakeSql {
    public static String getSql(String tableName,List<Dimension> dimensions, List<Measure> measures){
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        if (dimensions != null && dimensions.size() > 0) {
            for (Dimension dimension : dimensions) {
                sb.append(dimension.getName() + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        if (measures != null && measures.size() > 0) {
            if (dimensions.size() > 0)
                sb.append(",");
            for (Measure measure:measures) {
                sb.append(measure.getMethod() + "("+measure.getName()+") as" +measure.getName()+"agg,");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(" from ");
        sb.append(tableName);
        if (dimensions != null && dimensions.size() > 0) {
            sb.append(" group by ");
            for (Dimension dimension : dimensions) {
                sb.append(dimension.getName() + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
