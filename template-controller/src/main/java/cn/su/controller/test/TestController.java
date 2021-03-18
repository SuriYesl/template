package cn.su.controller.test;

import cn.su.dao.entity.test.TestModel;
import cn.su.dao.link.DatabaseOperation;
import cn.su.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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

    @RequestMapping(value = "/own_test", method = {RequestMethod.GET})
    public String ownTest() {
        TestModel insert = new TestModel();
        insert.setName("insert");
        insert.setCreateTime(LocalDateTime.now());
        insert.setAge(15);
        insert.setSomeNote("insert");
        insert.setSomeMessage("insert@qq.com");
        TestModel insert2 = new TestModel();
        insert2.setName("insert2");
        insert2.setCreateTime(LocalDateTime.now());
        insert2.setAge(22);
        insert2.setSomeNote("insert2");
        insert2.setSomeMessage("insert2@qq.com");

        TestModel testModel = new TestModel();
        testModel.setName("update2");
        testModel.setCreateTime(LocalDateTime.now());
        testModel.setAge(33);
        testModel.setSomeNote("update test2");
        testModel.setSomeMessage("update2@qq.com");
        //插入
        //new DatabaseOperation().insert().into("tb_test_model").obj(insert);
        //new DatabaseOperation().insert().into("tb_test_model").obj(insert2);
        //查
        TestModel select = new DatabaseOperation().select().from("tb_test_model").where().eq("name", "insert").forObject(TestModel.class);
        System.out.println(select);
        //改
        //new DatabaseOperation().update("tb_test_model").set(testModel).where().eq("name", "update").doUpdate();
        //删
        //new DatabaseOperation().delete().from("tb_test_model").where().eq("name", "张三").doDelete();
        return "test";
    }
}
