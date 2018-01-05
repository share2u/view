package site.share2u.view.service;

import site.share2u.view.pojo.PageData;

import java.util.List;

/**
 *  查询相应数据库中关于表数据的相关信息
 * Created by Administrator on 2018/1/5.
 */
public interface TableDateService {
    //获取列  去重计数
    public List<PageData> getCoiumnCount(String sql);
}
