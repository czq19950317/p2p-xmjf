package com.shsxt.xm.server.service;

import com.shsxt.xm.api.service.ISmsService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by lp on 2017/12/8.
 */

public class TestSmsService extends  TestBase{
    @Resource
    private ISmsService smsService;

    @Test
    public  void test(){
        smsService.sendPhoneSms("18516597651","6868",1);
    }
}
