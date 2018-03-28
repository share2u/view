package site.share2u.view.pojo;

import com.github.abel533.echarts.Option;

/**
 * Created by Administrator on 2018/3/27.
 */
public class OptionView{
    private Integer Id;
    private Integer dashboardId;
    private String tableName;
    private String option1;
    private Integer seriesTypeView;//图表类型
    private Integer vaild;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(Integer dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public Integer getSeriesTypeView() {
        return seriesTypeView;
    }

    public void setSeriesTypeView(Integer seriesTypeView) {
        this.seriesTypeView = seriesTypeView;
    }

    public Integer getVaild() {
        return vaild;
    }

    public void setVaild(Integer vaild) {
        this.vaild = vaild;
    }
}
