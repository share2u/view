package site.share2u.view.pojo;

import site.share2u.view.enums.DataType;

/**
 * @Description 维度
 * @Author chenweimin
 */
public class Dimension {
    private String name;
    private DataType dataType;

    public Dimension() {
    }

    public Dimension(String name, DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}
