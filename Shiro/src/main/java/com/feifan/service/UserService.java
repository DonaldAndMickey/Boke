package com.feifan.service;

import java.util.List;
import java.util.Set;

import com.feifan.to.UserTo;

public interface UserService {

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

    // 批量出入用户
    void insertUserList(List<UserTo> userTos);
}
