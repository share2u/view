package site.share2u.view.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.share2u.view.dao.SerieTypeMapper;
import site.share2u.view.pojo.SeriesTypeView;
import site.share2u.view.service.SerieTypeService;

import java.util.List;

/**
 * User: CWM
 * Date: 2018/4/3.
 */
@Service
public class SerieTypeServiceImpl implements SerieTypeService {

    @Autowired
    private SerieTypeMapper serieTypeMapper;
    @Override
    public SeriesTypeView getNameById(Integer id) {
        return serieTypeMapper.getNameById(id);
    }

    @Override
    public List<SeriesTypeView> getTypes() {
        return null;
    }
}
