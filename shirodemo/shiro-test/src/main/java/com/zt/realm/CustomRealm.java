package com.zt.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: 自定义Realm, 实际开发中在这里进行认证和授权, 从数据库中或者缓存中取数据
 * @version: v1.0.0
 * @author: zhangtao
 * @date: 2019/4/18 17:01
 */
public class CustomRealm extends AuthorizingRealm {


    {
        super.setName("customRealm");
    }

    //授权方法
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //从认证信息中获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();

        //根据用户名获取角色和权限
        Set<String> roleSet = getRolesByUserName(username);
        Set<String> permissionSet = getPermissionByUserName(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roleSet);
        authorizationInfo.addStringPermissions(permissionSet);
        return authorizationInfo;
    }

    //模拟从数据库中获取权限数据
    private Set<String> getPermissionByUserName(String username) {
        HashSet<String> permissionSet = new HashSet<String>();
        permissionSet.add("user:delete");
        permissionSet.add("user:add");
        return permissionSet;
    }

    //模拟从数据库中获取角色信息
    private Set<String> getRolesByUserName(String username) {
        HashSet<String> roleSet = new HashSet<String>();
        roleSet.add("admin");
        roleSet.add("user");
        return roleSet;
    }


    //认证方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //  从认证信息中获取用户名
        String username = (String) authenticationToken.getPrincipal();

        //根据用户名从数据库中获取凭证
        String password = getPasswordByUserName(username);
        if (password == null) {
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("zhangtao", password, "customRealm");

        //设置加盐
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("zt"));

        return simpleAuthenticationInfo;

    }

    //模拟从数据库中查询凭证
    private String getPasswordByUserName(String username) {
        HashMap<String, String> userMap = new HashMap<String, String>();
        //加密之后,数据库中的密码是MD5并且加盐之后的
        userMap.put("zhangtao", "0ed924706cc37695fd81f189f79ae10d");
        return userMap.get(username);
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123","zt");
        System.out.println(md5Hash);
    }
}
