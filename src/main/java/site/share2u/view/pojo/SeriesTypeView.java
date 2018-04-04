package site.share2u.view.pojo;

/**
 * User: CWM
 * Date: 2018/4/3.
 */
public class SeriesTypeView {
    private Integer id;
    private String name;
    private String remark;
    
    public SeriesTypeView() {
    }
    
    public SeriesTypeView(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeriesTypeView that = (SeriesTypeView) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
