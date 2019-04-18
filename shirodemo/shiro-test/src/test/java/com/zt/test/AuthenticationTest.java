package com.zt.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @description: shiro 认证测试 SImpleAccountRealm不能设置权限
 * @version: v1.0.0
 * @author: zhangtao
 * @date: 2019/4/10 18:00
 */
public class AuthenticationTest {

    //创建一个简单的Realm,认证信息和授权信息从这里面或者缓存中读取
    private SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void testBefore() {
        //设置账号密码,和角色用于后续认证
        simpleAccountRealm.addAccount("zhangtao", "1234","admin","user");
    }

    @Test
    public void testAuthentication() {

        //1.构建securityManager环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //给环境设置realm,因为要从realm中获取认证授权信息
        securityManager.setRealm(simpleAccountRealm);

        //2.主体提交认证
        //给主体设置环境
        SecurityUtils.setSecurityManager(securityManager);
        //获取主体subject
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangtao", "1234");
        //登录
        subject.login(usernamePasswordToken);
        System.out.println("is isAuthenticated: " + subject.isAuthenticated());
        //校验是否拥有角色
        subject.checkRoles("admin","user");
        //登出
        //subject.logout();
        //System.out.println("is isAuthenticated: " + subject.isAuthenticated());
    }
}
