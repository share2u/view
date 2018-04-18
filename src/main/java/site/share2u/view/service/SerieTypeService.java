package site.share2u.view.service;

import site.share2u.view.pojo.SeriesTypeView;

import java.util.List;

/**
 * User: CWM
 * Date: 2018/4/3.
 */
public interface  SerieTypeService {
    /**
     * 根据id获取图表类型名称
     * @param id
     * @return
     */
    SeriesTypeView getNameById(Integer id);
    /**
     * 根据name获取图表类型名称
     * @return
     */
    SeriesTypeView getNameByName(String chartName);

    /**
     * 获取所有的图表类型
     * @return
     */
    List<SeriesTypeView> getTypes();

}
