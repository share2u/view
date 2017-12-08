package site.share2u.view.pojo;

import site.share2u.view.enums.DataType;
import site.share2u.view.enums.Method;

/**
 * @Description  度量
 * @Author chenweimin
 */
public class Measure {
    private String name;
    private DataType dataType;
    private Method method;

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Method getMethod() {
        return method;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
