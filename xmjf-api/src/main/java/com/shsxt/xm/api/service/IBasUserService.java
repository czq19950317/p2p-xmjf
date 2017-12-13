package com.shsxt.xm.api.service;

import com.shsxt.xm.api.po.BasUser;

/**
 *
 *  用户模块接口方法定义
 */
public interface IBasUserService {
    public BasUser queryBasUserById(Integer id);
    public BasUser queryBasUserByPhone(String phone);

    /**
     * 保存用户记录
     * @param phone
     * @param password
     */
    public void saveBasUser(String phone, String password);

    /**
     * 手机号+密码登录
     * @param phone
     * @param password
     * @return
     */
    public  BasUser userLogin(String phone,String password);

    /**
     * 快捷登录
     * @param phone
     * @return
     */
    public BasUser quickLogin(String phone);

}
