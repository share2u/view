package site.share2u.view.util;

import org.apache.log4j.Logger;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;

import java.util.List;

/**
 * @Description
 * @Author chenweimin
 */
public class MakeSql {
    private static Logger log = Logger.getLogger(MakeSql.class);
    /**
     *  拼接获取option数据的sql，不区分数据的类型
     * @param tableName
     * @param dimensions
     * @param measures
     * @return
     */
    public static String getSql(String tableName,List<Dimension> dimensions, List<Measure> measures){
        log.info("获取"+tableName+"表中的关于--维度("+dimensions.toString()+")与度量（"+measures.toString()+")--的数据");
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        if (dimensions != null && dimensions.size() > 0) {
            for (Dimension dimension : dimensions) {
                if(dimension.getName().equals("user_attenton_time") || dimension.getName().equals("update_time") || dimension.getName().equals("create_time") ){
                    sb.append("DATE_FORMAT("+dimension.getName()+", '%Y-%m-%d') as "+dimension.getName()+ ",");
                }else{
                    sb.append(dimension.getName() + ",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        if (measures != null && measures.size() > 0) {
            if (dimensions.size() > 0) {
                sb.append(",");
            }
            for (Measure measure:measures) {
                String methodName = measure.getMethod();
                sb.append(methodName + "("+measure.getName()+") as " +"agg_"+measure.getName()+",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(" from ");
        sb.append(tableName);
        if (dimensions != null && dimensions.size() > 0) {
            sb.append(" group by ");
            for (Dimension dimension : dimensions) {
                if(dimension.getName().equals("user_attenton_time") || dimension.getName().equals("update_time") || dimension.getName().equals("create_time") ){
                    sb.append("DATE_FORMAT("+dimension.getName()+", '%Y-%m-%d')"+ ",");
                }else{
                    sb.append(dimension.getName() + ",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        log.info("拼接的sql为："+sb.toString());
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
