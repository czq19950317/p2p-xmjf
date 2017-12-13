package com.shsxt.xm.server.service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.junit.Test;

/**
 * Created by lp on 2017/12/7.
 */
public class TestSms {

    @Test
    public  void test(){
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "24664902", "04e5d0670a772219984bf206cb85c55b");
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("");
        req.setSmsType("normal");
        req.setSmsFreeSignName("小马金服");
        req.setSmsParamString("{\"code\":\"8888\",\"product\":\"xmjf\"}");
        req.setRecNum("18321530938");
        req.setSmsTemplateCode("SMS_115100107");
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
    }
}
