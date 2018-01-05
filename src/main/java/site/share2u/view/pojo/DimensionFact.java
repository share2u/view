package site.share2u.view.pojo;

import site.share2u.view.enums.DataType;

import java.util.List;

/**
 *  维度事实，drools规则引擎使用
 * Created by Administrator on 2018/1/4.
 */
public class DimensionFact {

    Integer dimensionCount;//维度的数量
    List<DataType> dimensionsType;//维度s类型
    List<Integer> dimensionsSum;//维度s数量
    Integer measureCount;//度量计数

    public Integer getDimensionCount() {
        return dimensionCount;
    }

    public void setDimensionCount(Integer dimensionCount) {
        this.dimensionCount = dimensionCount;
    }



    public List<Integer> getDimensionsSum() {
        return dimensionsSum;
    }

    public void setDimensionsSum(List<Integer> dimensionsSum) {
        this.dimensionsSum = dimensionsSum;
    }

    public Integer getMeasureCount() {
        return measureCount;
    }

    public void setMeasureCount(Integer measureCount) {
        this.measureCount = measureCount;
    }

    public List<DataType> getDimensionsType() {
        return dimensionsType;
    }

    public void setDimensionsType(List<DataType> dimensionsType) {
        this.dimensionsType = dimensionsType;
    }
}
