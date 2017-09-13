package com.rong.admin.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.rong.persist.model.Admin;
import com.rong.system.service.AdminService;
import com.rong.system.service.AdminServiceImpl;

/**
 * <p>User: rongwq
 * <p>Date: 16-8-24
 * <p>Version: 1.0
 */
public class UserRealm extends AuthorizingRealm {
    private AdminService userService = new AdminServiceImpl();

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> set = new HashSet<>();
        Admin user = userService.getByUserName(username);
        if(user!=null){
        	set.add(user.getRole());
        }
        authorizationInfo.setRoles(set);
        authorizationInfo.setStringPermissions(userService.findPermissions(username));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();

        Admin user = userService.getByUserName(username);

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserName(), //用户名
                user.getPassword(), //密码
                getName()  //realm name
        );
        
        if(user.getRole().equals("agent")){
			throw new UnknownAccountException();//如果是代理商，也抛出错误
		}
        return authenticationInfo;
    }
}
