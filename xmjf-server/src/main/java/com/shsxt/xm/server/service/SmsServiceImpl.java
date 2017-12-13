package com.shsxt.xm.server.service;

import com.alibaba.fastjson.JSON;
import com.shsxt.xm.api.po.BasUser;
import com.shsxt.xm.api.service.IBasUserService;
import com.shsxt.xm.api.service.ISmsService;
import com.shsxt.xm.server.SmsType;
import com.shsxt.xm.server.constant.TaoBaoConstant;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.shsxt.xm.api.utils.AssertUtil;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lp on 2017/12/8.
 */
@Service
public class SmsServiceImpl implements ISmsService {
    @Resource
    private IBasUserService basUserService;
    @Override
    public void sendPhoneSms(String phone, String code, Integer type) {
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号非法!");
        /**
         * 11111111111
         * 12222222222
         *   正则判断手机号非法
         */
        AssertUtil.isTrue(StringUtils.isBlank(code),"手机号验证码不能为空!");
        AssertUtil.isTrue(null==type,"短信验证码类型不匹配!");
        AssertUtil.isTrue(!type.equals(SmsType.NOTIFY.getType())
                &&!type.equals(SmsType.REGISTER.getType()),"短信验证码类型不匹配!");

        if(type.equals(SmsType.REGISTER.getType())){
            /**
             * 注册时用户手机号不能重复
             */
            BasUser basUser= basUserService.queryBasUserByPhone(phone);
            AssertUtil.isTrue(null!=basUser,"该手机号已注册!");
            //doSend(phone,code, TaoBaoConstant.SMS_TEMATE_CODE_REGISTER);
        }
        if(type.equals(SmsType.NOTIFY.getType())){
            //doSend(phone,code,TaoBaoConstant.SMS_TEMATE_CODE_lOGIN);
        }
    }


    public  void doSend(String phone, String code,String templateCode){
       /* TaobaoClient client = new DefaultTaobaoClient(TaoBaoConstant.SERVER_URL,
                TaoBaoConstant.APP_KEY,TaoBaoConstant.APP_SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("");
        req.setSmsType(TaoBaoConstant.SMS_TYPE);
        req.setSmsFreeSignName(TaoBaoConstant.SMS_FREE_SIGN_NAME);
        Map<String,String> map=new HashMap<String,String>();
        map.put("code",code);
        req.setSmsParamString(JSON.toJSONString(map));
        req.setRecNum(phone);
        req.setSmsTemplateCode(templateCode);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
           rsp = client.execute(req);
          AssertUtil.isTrue(!rsp.isSuccess(),"短信发送失败,请稍后再试!");
        } catch (ApiException e) {
            e.printStackTrace();
        }*/
    }



}