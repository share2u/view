package site.share2u.view.dao;

import org.springframework.stereotype.Repository;
import site.share2u.view.pojo.SeriesTypeView;

import java.util.List;

/**
 * User: CWM
 * Date: 2018/4/3.
 */
@Repository
public interface SerieTypeMapper {
    SeriesTypeView getNameById(Integer id);
    List<SeriesTypeView> getTypes();
}
