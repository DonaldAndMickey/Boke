package com.feifan.dao;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feifan.exception.GloableExecption;
import com.feifan.to.UserTo;

public interface UserMapper {

    public static final Logger LOGGER = LoggerFactory.getLogger(GloableExecption.class);

    // 通过id查询用户数据
    UserTo findUserById(int id);

    // 通过用户名查找用户信息
    UserTo findUserByUsername(String username);

    // 新增用户
    void insertUser(UserTo user);

    // 更新用户
    void updateUser(UserTo user);

    // 查找权限
    Set<String> findPermsByUsername(String username);

    // 查找角色
    Set<String> findRolesByUsername(String username);

    // 批量写入用户
    void insertUserList(List<UserTo> userTos);

}
