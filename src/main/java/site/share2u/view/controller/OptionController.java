package site.share2u.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import site.share2u.view.pojo.Dimension;
import site.share2u.view.pojo.Measure;
import site.share2u.view.pojo.ResponseBO;

import java.util.List;

@Controller
@RequestMapping("/option")
public class OptionController {
/*
 * 1.根据维度等获得推荐类型
 * 	参数需要有---表名，维度的list,度量的list
 * 2.根据图表类型，维度等信息画图
 * 3.保存option?
 */


    /*
     * 前后端传递大参数
     * 1，可以根据参数直接生成新的option2.已经形成的option要数据库参数加js路径
     *
     */
    @RequestMapping(value = "/recommemd")
    public ResponseBO getTuiJianType(String tableName, List<Dimension> dimensions, List<Measure> measures) {
        ResponseBO rb = new ResponseBO();
        return rb;
    }


}
