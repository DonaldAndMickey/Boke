package com.feifan.shiro;

import com.feifan.dao.UserMapper;
import com.feifan.to.UserTo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 
 * @author Donald
 * @date 2019/01/20
 * @see
 */
public class UserRelam extends AuthorizingRealm {

    private static final Logger Logger = LoggerFactory.getLogger(UserRelam.class);

    @Autowired
    private UserMapper userMapper;

    /*执行授权（为当前登录的Subject授予角色和权限）
     * 如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置  
     * 超过这个时间间隔再刷新页面，该方法会被执行  
     * 当访问到页面的时候，使用了相应的注解或者shiro标签才会执行此方法否则不会执行
     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可
     * (non-Javadoc)
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Logger.info("用户登陆成功开始执行授权操作");
        // 获取当前用户
        String username = (String)principals.getPrimaryPrincipal();
        // TODO 授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取权限
        Set<String> perms = userMapper.findPermsByUsername(username);
        // 获取角色
        Set<String> roles = userMapper.findRolesByUsername(username);
        info.setStringPermissions(perms);
        info.setRoles(roles);
        Logger.info("{} 的权限和角色是：{}{}", username, perms, roles);
        return info;
    }

    /*执行认证 查询数据库用户信息，
     * (non-Javadoc)
     * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Logger.info("用户开始执行shiro 认证操作");
        // 获取用户的信息
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        // 用户名
        String username = userToken.getUsername();

        // 查询数据库 返回信息
        UserTo userTo = userMapper.findUserByUsername(username);
        if (userTo == null) {
            throw new AuthenticationException("用户名信息错误");
        }
        // SimpleHash sHash = new SimpleHash("MD5", passWord, ByteSource.Util.bytes("password"), 1024); 不访问数据库自己操作

        Logger.info("usertO 基本信息是：{}", userTo);

        // 传入:用户名,加密后的密码,盐值,该realm的名字，加密算法和加密次数在已经在配置文件中指定
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, userTo.getPassword(),
            ByteSource.Util.bytes(userTo.getSalt()), getName());
        return info;
    }

    // 权限修改生效后，立即刷新清空缓存，则可以实现用户不退出生效新的权限
    // 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法
    public void clearCache() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

}
