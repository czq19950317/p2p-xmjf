package com.shsxt.xm.server.service;

import com.shsxt.xm.api.po.BasUser;
import com.shsxt.xm.api.service.IBasUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by lp on 2017/12/7.
 */

public class TestBasUserService extends  TestBase{
    @Resource
    private IBasUserService basUserService;

    @Test
    public  void test01(){
        BasUser basUser=basUserService.queryBasUserById(1);
        System.out.println(basUser);
    }

    @Test
    public  void test02(){
        basUserService.saveBasUser("17621530014","123456");
    }


}
