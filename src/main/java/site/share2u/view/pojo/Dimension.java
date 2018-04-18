package site.share2u.view.pojo;

/**
 * @Description 维度
 * @Author chenweimin
 */
public class Dimension {
    private String name;
    private Integer dataType;

    public Dimension() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Dimension(String name, Integer dataType) {
        this.name = name;
        this.dataType = dataType;
    }
    
    @Override
    public String toString() {
        return "Dimension{" +
                "name='" + name + '\'' +
                ", dataType=" + dataType +
                '}';
    }
}
