package site.share2u.view.util;

import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;

import java.util.List;

/**
 * @Description
 * @Author chenweimin
 */
public class MakeSql {
    /**
     *  拼接获取option数据的sql
     * @param tableName
     * @param dimensions
     * @param measures
     * @return
     */
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

    /**
     *  拼接获取table 列 去重计数的sql
     * @param tableName
     * @param dimensions
     * @return
     */
    public static String getSql(String tableName,List<Dimension> dimensions){
        StringBuilder sb = new StringBuilder();
        for (Dimension dimension: dimensions) {
            sb.append("select count(distinct ");
            sb.append(dimension.getName());
            sb.append(" ) as tmp from "+ tableName);
            sb.append( " union ");
        }
        if (dimensions != null && dimensions.size() > 0) {
            sb.delete(sb.length() - 6,sb.length()-1);
        }
        return sb.toString();

    }


}
