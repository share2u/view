package site.share2u.view.util;

import org.junit.Test;
import site.share2u.view.enums.DataType;
import site.share2u.view.pojo.Dimension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/1/5.
 */
public class MakeSqlTest {
    @Test
    public void getSql() throws Exception {
    }

    @Test
    public void getSql1() throws Exception {
        List<Dimension> dimensions = new ArrayList<>();
        Dimension dimension1 = new Dimension();
        dimension1.setDataType(DataType.STRINGE);
        dimension1.setName("SERVICECOST_ID");
        Dimension dimension2 = new Dimension();
        dimension2.setDataType(DataType.STRINGE);
        dimension2.setName("STORE_ID");
        dimensions.add(dimension1);
        dimensions.add(dimension2);
        String str = MakeSql.getSql("tb_order", dimensions);
        System.out.println(str);
    }

}