package com.zt.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @description: shiro 认证测试
 * @version: v1.0.0
 * @author: zhangtao
 * @date: 2019/4/10 18:00
 */
public class AuthenticationTest {

    //创建一个简单的Realm,认证信息和授权信息从这里面或者缓存中读取
    private SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void testBefore() {
        simpleAccountRealm.addAccount("zhangtao", "1234");
    }

    @Test
    public void testAuthentication() {

        //构建securityManager环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(simpleAccountRealm);

        
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangtao", "1234");
        subject.login(usernamePasswordToken);
        System.out.println("is isAuthenticated: " + subject.isAuthenticated());
        subject.logout();
        System.out.println("is isAuthenticated: " + subject.isAuthenticated());
    }
}
