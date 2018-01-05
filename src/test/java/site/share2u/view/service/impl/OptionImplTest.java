package site.share2u.view.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.code.SeriesType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import site.share2u.view.BaseJunit4Test;
import site.share2u.view.enums.DataType;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.service.OptionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/1/5.
 */
public class OptionImplTest extends BaseJunit4Test{
    @Autowired
    OptionService optionService;

    @Test
    public void getTypes() throws Exception {
        String tableName="tb_order";
        List<Dimension> types = new ArrayList<>();
        List<Measure> measures =  new ArrayList<>();
        types.add(new Dimension("SERVICECOST_ID", DataType.STRINGE));
        types.add(new Dimension("STORE_ID", DataType.STRINGE));
        long startTime = System.currentTimeMillis();
        List<SeriesType> types1 = optionService.getTypes(tableName, types, measures);
        System.out.println(JSON.toJSONString(types1));
        long endTime = System.currentTimeMillis();
        float seconds = (endTime - startTime) / 1000F;
        System.out.println("执行时间："+seconds+"秒");
    }

    @Test
    public void getOption() throws Exception {
    }

}