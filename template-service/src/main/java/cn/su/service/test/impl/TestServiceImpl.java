package cn.su.service.test.impl;

import cn.su.dao.entity.test.TestModel;
import cn.su.service.test.TestService;
import org.springframework.stereotype.Service;

/**
 * @Author: sr
 * @Date: Create In 22:02 2021/1/14 0014
 * @Description: 测试
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public void insertTest() {
        TestModel testModel = new TestModel();
    }

    @Override
    public void updateTest() {

    }

    @Override
    public void searchTest() {
        //DatabaseOperator<TestModel> databaseOperator = new DatabaseOperator<>(TestModel.class);
        //databaseOperator.defaultSelect();
        //System.out.println(databaseOperator.getSearchSql());
        //TestModel testModel = databaseOperator.selectObject();
        //List<TestModel> testModels = databaseOperator.selectList();
        //System.out.println("*******************************************");
        //System.out.println(testModel.toString());
        //System.out.println("*******************************************");
        //for (TestModel testModel1 : testModels) {
        //    System.out.println(testModel1.toString());
        //}
    }
}
