package com.feifan.service.impl;

import com.feifan.dao.UserMapper;
import com.feifan.exception.GloableExecption;
import com.feifan.service.UserService;
import com.feifan.to.UserTo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Donald
 * @date 2019/01/19
 * @see
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GloableExecption.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserTo findUserById(int id) {
        return null;
    }

    @Override
    public UserTo findUserByUsername(String username) {
        return null;
    }

    @Override
    public void insertUser(UserTo user) {

    }

    @Override
    public void updateUser(UserTo user) {

    }

    @Override
    public Set<String> findPermsByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> findRolesByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insertUserList(List<UserTo> userTos) {
        LOGGER.info("批量插入用户进行中");
        List<UserTo> list = new ArrayList<UserTo>();
        UserTo user = null;
        SimpleHash sHash = null;
        StringBuffer buffer = null;
        for (int i = 0; i < 15; i++) {
            user = new UserTo();
            user.setAge(i);
            user.setBrithday(new Date());
            user.setCreteTime(new Date());
            user.setModifyTime(new Date());
            user.setLastLoginTime(new Date());
            String salt = String.valueOf(System.currentTimeMillis());
            sHash = new SimpleHash("MD5", "password", ByteSource.Util.bytes(salt), 1024);
            user.setSalt(salt);
            user.setPassword(sHash.toString());
            buffer = new StringBuffer();
            buffer.append("wang").append(i);
            user.setUsername(buffer.toString());
            user.setGender(i % 2 == 0 ? "man" : "women");
            list.add(user);
            /*try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        // userMapper.insertUserList(list);
        Set<String> roles = userMapper.findRolesByUsername("wang3");
        Set<String> perms = userMapper.findPermsByUsername("wang3");
        UserTo userTo = userMapper.findUserByUsername("wang3");
        LOGGER.info("OK is {},{} userTo is{}", roles, perms, userTo);
    }

}
