package site.share2u.view.service;

import site.share2u.view.pojo.PageData;

import java.util.List;

/**
 * @Description option数据相关的接口
 * @Author chenweimin
 */
public interface OptionDataService {
    public List<PageData> getOptionData(String sql);
}
