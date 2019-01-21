package com.feifan.controller;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feifan.dao.UserMapper;
import com.feifan.exception.GloableExecption;
import com.feifan.exception.ServiceException;
import com.feifan.service.UserService;
import com.feifan.shiro.UserRelam;
import com.feifan.to.UserTo;

/**
 * 控制层
 * 
 * @author Donald
 * @date 2019/01/20
 * @see
 */
@Controller
@RequestMapping
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GloableExecption.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/login")
    public String login(UserTo userTo) {
        LOGGER.info("开始执行登陆操作 username：{}，password:{}", userTo.getUsername(), userTo.getPassword());
        Subject subject = SecurityUtils.getSubject();// 创建用户实体
        System.err.println(subject.isRemembered() + "" + subject.isAuthenticated());
        // if (!subject.isAuthenticated()) {
        UsernamePasswordToken token = new UsernamePasswordToken(userTo.getUsername(), userTo.getPassword());
        try {
            // 执行登陆验证
            subject.login(token);
            LOGGER.info("恭喜登陆成功");
        } catch (UnknownAccountException e) {
            throw new ServiceException("用户名不存在");
        } catch (IncorrectCredentialsException e) {
            throw new ServiceException("用户名或者密码错误");
        }
        // }
        return "index";
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        LOGGER.info("用户登出成功");
        return "index";
    }

    @RequestMapping("/select")
    @ResponseBody
    @RequiresPermissions(value = {"system:user:delete"})
    public Object selectUser() {
        // userService.insertUserList(new ArrayList<>());
        Subject subject = SecurityUtils.getSubject();
        LOGGER.info("执行查询:{}:{}", subject.getPrincipal(), subject.getSession());
        return userMapper.findUserByUsername("wang3");
    }

    @RequestMapping("/insert")
    @ResponseBody
    @RequiresPermissions(value = {"system:user:insert"})
    public Object insertUser(UserTo userTo) {
        Subject subject = SecurityUtils.getSubject();
        System.err.println("进来了");
        return userMapper.findUserByUsername("wang3");
    }

    /**
     * 尝试通过异常让事务回滚
     * 
     * @return
     */
    @RequestMapping("trans")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) // 开启事务,异常事务回滚
    public Object transTest() {
        UserTo user = new UserTo();
        user.setAge(18);
        user.setBrithday(new Date());
        user.setCreteTime(new Date());
        user.setModifyTime(new Date());
        user.setLastLoginTime(new Date());
        String salt = String.valueOf(System.currentTimeMillis());
        SimpleHash sHash = new SimpleHash("MD5", "password", ByteSource.Util.bytes(salt), 1024);
        user.setSalt(salt);
        user.setPassword(sHash.toString());
        user.setUsername("wang");
        user.setGender("women");
        // 获取自增的主键
        userMapper.insertUser(user);
        LOGGER.info("新增后主键是：{}", user.getId());
        // int i = 0 / 0;// 制造异常 看事务回滚
        return null;
    }

    // 配置错误界面
    @RequestMapping("error-400")
    public String toPage400() {
        return "err";
    }

    @Autowired
    private UserRelam userRelam;

    // 登陆界面
    @RequestMapping("page/login")
    public String log() {
        userRelam.clearCache();// 刷新权限不许要重新登录
        return "login";
    }

    // 主界面
    @RequestMapping("page/index")
    public String index() {
        return "index";
    }

}
