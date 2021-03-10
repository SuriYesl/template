package cn.su.controller.test;

import cn.su.dao.automatic.SimpleSqlAutomationUtil;
import cn.su.dao.entity.test.TestModel;
import cn.su.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;
import java.util.List;

/**
 * @Author: sr
 * @Date: Create In 21:57 2021/1/14 0014
 * @Description: 测试专用接口
 */
@RestController
@RequestMapping("template/controller/test")
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/get_method", method = {RequestMethod.GET})
    public void getMethod() {
        System.out.println("这里是GET请求");
        //testService.searchTest();
        SimpleSqlAutomationUtil simpleSqlAutomationUtil = new SimpleSqlAutomationUtil();
        List<TestModel> tests = simpleSqlAutomationUtil.select(TestModel.class).eq("name", "李四").forList();
        for (TestModel testModel : tests) {
            System.out.println(testModel.toString());
        }
    }
}
