package site.share2u.view.pojo;

/**
 * @Description  度量
 * @Author chenweimin
 */
public class Measure {
    private String name;
    private Integer dataType;
    private String method;
    
    public Measure() {
    }
    
    public Measure(String name, Integer dataType, String method) {
        this.name = name;
        this.dataType = dataType;
        this.method = method;
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
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    @Override
    public String toString() {
        return "Measure{" +
                "name='" + name + '\'' +
                ", dataType=" + dataType +
                ", method='" + method + '\'' +
                '}';
    }
}
