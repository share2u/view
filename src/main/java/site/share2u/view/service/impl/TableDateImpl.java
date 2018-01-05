package site.share2u.view.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.share2u.view.dao.OptionMapper;
import site.share2u.view.pojo.PageData;
import site.share2u.view.service.TableDateService;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5.
 */
@Service
public class TableDateImpl implements TableDateService {
    protected  Logger log = Logger.getLogger(TableDateImpl.class);
    @Autowired
    OptionMapper optionMapper;

    @Override
    public List<PageData> getCoiumnCount(String sql) {
        log.info("取列计数的sql  为"+sql);
        List<PageData> optionData = optionMapper.getOptionData(sql);
        return optionData;
    }
}
