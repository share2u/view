package site.share2u.view.pojo;

import com.github.abel533.echarts.code.SeriesType;

/**
 * Created by Administrator on 2018/3/27.
 */
public enum SeriesTypeView {
    LINE(1, "line"),
    LINES(2, "lines"),
    BAR(3, "bar"),
    SCATTER(4, "scatter"),
    EFFECTSCATTER(5, "effectScatter"),
    CANDLESTICK(6, "candlestick"),
    PIE(7, "pie"),
    GROUP(8, "graph"),
    MAP(9, "map"),
    FUNNEL(10, "funnel"),
    GAUGE(11, "gauge"),
    TREEMAP(12, "treemap"),
    HEATMAP(13, "heatmap"),
    BOXPLOT(14, "boxplot"),
    PARALLEL(15, "parallel"),
    SANKEY(16, "sankey");

    private int index;
    private String name;

    SeriesTypeView(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static SeriesTypeView getName(int index){
        for (SeriesTypeView stv:SeriesTypeView.values()) {
            if(stv.getIndex()==index){
                return stv;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
