package com.shsxt.xm.server.service;

import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import com.shsxt.xm.api.constant.P2PConstant;
import com.shsxt.xm.api.po.*;
import com.shsxt.xm.api.service.IBasUserService;
import com.shsxt.xm.api.utils.AssertUtil;
import com.shsxt.xm.api.utils.RandomCodesUtils;
import com.shsxt.xm.server.db.dao.*;
import com.shsxt.xm.server.utils.DateUtils;
import com.shsxt.xm.server.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.annotation.processing.SupportedSourceVersion;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lp on 2017/12/7.
 */
@Service
public class BasUserServiceImpl implements IBasUserService {
    @Resource
    private BasUserDao basUserDao;
    @Resource
    private BasUserInfoDao basUserInfoDao;


    @Resource
    private BasUserSecurityDao basUserSecurityDao;

    @Resource
    private BusAccountDao busAccountDao;

    @Resource
    private BusUserIntegralDao busUserIntegralDao;

    @Resource
    private BusIncomeStatDao busIncomeStatDao;

    /**
     * bus_user_stat
     * bas_experienced_gold
     * sys_app_settings
     * sys_log
     */

    @Resource
    private BusUserStatDao busUserStatDao;

    @Resource
    private  BasExperiencedGoldDao basExperiencedGoldDao;

    @Resource
    private SysLogDao sysLogDao;


    @Override
    public BasUser queryBasUserById(Integer id) {
        return basUserDao.queryBasUserById(id);
    }

    @Override
    public BasUser queryBasUserByPhone(String phone) {
        return basUserDao.queryBasUserByPhone(phone);
    }

    @Override
    public void saveBasUser(String phone, String password) {
        Integer userId= initBasUser(phone,password);
        initBasUserInfo(userId);
        initBasUserSecurity(userId);
        initBusAccount(userId);
        initBusUserIntegral(userId);
        initBusIncomeStat(userId);
        initBusUserStat(userId);
        initBasExperiencedGold(userId);
        initSysLog(userId);
    }

    @Override
    public BasUser userLogin(String phone, String password) {
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号非空!");
        AssertUtil.isTrue(StringUtils.isBlank(password),"密码非空!");
        BasUser basUser = basUserDao.queryBasUserByPhone(phone);
        AssertUtil.isTrue(null ==basUser,"该用户不存在！");
        String salt=basUser.getSalt();
        password=MD5.toMD5(password+salt);
        AssertUtil.isTrue(!password.equals(basUser.getPassword()),"密码不正确!");
        return basUser;
    }

    @Override
    public BasUser quickLogin(String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号非空!");
        BasUser basUser=basUserDao.queryBasUserByPhone(phone);
        AssertUtil.isTrue(null==basUser,"该用户不存在!");
        return basUser;
    }

    private void initSysLog(Integer userId) {
        SysLog sysLog=new SysLog();
        sysLog.setAddtime(new Date());
        sysLog.setCode("REGISTER");
        sysLog.setOperating("用户注册");
        sysLog.setResult(1);
        sysLog.setUserId(userId);
        sysLog.setType(4);
        AssertUtil.isTrue(sysLogDao.insert(sysLog)<1,P2PConstant.OPS_FAILED_MSG);
    }

    private void initBasExperiencedGold(Integer userId) {
        BasExperiencedGold basExperiencedGold=new BasExperiencedGold();
        basExperiencedGold.setAddtime(new Date());
        basExperiencedGold.setAmount(BigDecimal.valueOf(2888L));
        basExperiencedGold.setGoldName("注册体验金");
        basExperiencedGold.setRate(BigDecimal.valueOf(10));
        basExperiencedGold.setStatus(2);
        basExperiencedGold.setUsefulLife(3);
        basExperiencedGold.setUserId(userId);
        basExperiencedGold.setWay("register");
        basExperiencedGold.setExpiredTime(DateUtils.addTime(1,new Date(),30));
        AssertUtil.isTrue(basExperiencedGoldDao.insert(basExperiencedGold)<1, P2PConstant.OPS_FAILED_MSG);
    }

    private void initBusUserStat(Integer userId) {
        BusUserStat busUserStat=new BusUserStat();
        busUserStat.setUserId(userId);
        AssertUtil.isTrue(busUserStatDao.insert(busUserStat)<1,P2PConstant.OPS_FAILED_MSG);
    }

    private void initBusIncomeStat(Integer userId) {
        BusIncomeStat busIncomeStat=new BusIncomeStat();
        busIncomeStat.setUserId(userId);
        AssertUtil.isTrue(busIncomeStatDao.insert(busIncomeStat)<1,P2PConstant.OPS_FAILED_MSG);
    }

    private void initBusUserIntegral(Integer userId) {
        BusUserIntegral busUserIntegral=new BusUserIntegral();
        busUserIntegral.setUserId(userId);
        busUserIntegral.setTotal(0);
        busUserIntegral.setUsable(0);
        AssertUtil.isTrue(busUserIntegralDao.insert(busUserIntegral)<1,P2PConstant.OPS_FAILED_MSG);
    }

    private void initBusAccount(Integer userId) {
        BusAccount busAccount=new BusAccount();
        busAccount.setUserId(userId);
        busAccount.setCash(BigDecimal.ZERO);
        AssertUtil.isTrue(busAccountDao.insert(busAccount)<1,P2PConstant.OPS_FAILED_MSG);
    }

    private void initBasUserSecurity(Integer userId) {
        BasUserSecurity basUserSecurity=new BasUserSecurity();
        basUserSecurity.setUserId(userId);
        basUserSecurity.setPhoneStatus(1);
        AssertUtil.isTrue(basUserSecurityDao.insert(basUserSecurity)<1,P2PConstant.OPS_FAILED_MSG);
    }

    private void initBasUserInfo(Integer userId) {
        BasUserInfo basUserInfo=new BasUserInfo();
        basUserInfo.setUserId(userId);
        String investCode=RandomCodesUtils.createRandom(false,6);
        basUserInfo.setInviteCode(investCode);
        AssertUtil.isTrue(basUserInfoDao.insert(basUserInfo)<1,P2PConstant.OPS_FAILED_MSG);
    }


    private Integer initBasUser(String phone, String password) {
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(password),"密码不能为空!");
        AssertUtil.isTrue(null!= queryBasUserByPhone(phone),"该手机号已注册!");
        BasUser basUser=new BasUser();
        basUser.setAddtime(new Date());
        basUser.setMobile(phone);
        String salt= RandomCodesUtils.createRandom(false,4);
        basUser.setSalt(salt);// 设置盐值
        password= MD5.toMD5(password+salt);// 加盐加密
        basUser.setPassword(password);
        basUser.setReferer("PC");
        basUser.setStatus(1);
        basUser.setType(1);
        //basUserDao.insert(basUser);// 获取主键
        AssertUtil.isTrue(basUserDao.insert(basUser)<1, P2PConstant.OPS_FAILED_MSG);
        Integer userId=basUser.getId();
        String year=new SimpleDateFormat("yyyy").format(new Date());
        basUser.setUsername("SXT_P2P"+year+userId);
        AssertUtil.isTrue(basUserDao.update(basUser)<1,P2PConstant.OPS_FAILED_MSG);
        return userId;
    }
}
