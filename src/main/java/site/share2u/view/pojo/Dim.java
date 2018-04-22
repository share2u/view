package site.share2u.view.pojo;

/**
 * @auther: CWM
 * @date: 2018/4/22.
 */
public class Dim {
    Integer id;
    Integer optionId;
    String dimName;
    String dimOper;
    String pathName;
    
    public String getPathName() {
        return pathName;
    }
    
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getOptionId() {
        return optionId;
    }
    
    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }
    
    public String getDimName() {
        return dimName;
    }
    
    public void setDimName(String dimName) {
        this.dimName = dimName;
    }
    
    public String getDimOper() {
        return dimOper;
    }
    
    public void setDimOper(String dimOper) {
        this.dimOper = dimOper;
    }
}
