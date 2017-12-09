package site.share2u.view.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.share2u.view.dao.OptionMapper;
import site.share2u.view.pojo.PageData;
import site.share2u.view.service.OptionDataService;

import java.util.List;

@Service
public class OptionDataServiceImpl implements OptionDataService {
    @Autowired
    private OptionMapper optionMapper;

    @Override
    public List<PageData> getOptionData(String sql) {
        List<PageData> optionData = optionMapper.getOptionData(sql);
        return optionData;
    }
}
