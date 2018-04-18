package site.share2u.view.pojo;

import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */
public class OptionVO {
    Integer id;
    String seriesType;
    String tableName;
    List<Dimension> dimensions;
    List<Measure> measures;
    Integer dashboardId;
    String option1;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getDashboardId() {
        return dashboardId;
    }
    
    public void setDashboardId(Integer dashboardId) {
        this.dashboardId = dashboardId;
    }
    
    public String getOption1() {
        return option1;
    }
    
    public void setOption1(String option1) {
        this.option1 = option1;
    }
    
    public String getSeriesType() {
        return seriesType;
    }
    
    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }
    
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Dimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }
    
    @Override
    public String toString() {
        return "OptionVO{" +
                "id=" + id +
                ", seriesType='" + seriesType + '\'' +
                ", tableName='" + tableName + '\'' +
                ", dimensions=" + dimensions +
                ", measures=" + measures +
                ", dashboardId=" + dashboardId +
                ", option1='" + option1 + '\'' +
                '}';
    }
}
