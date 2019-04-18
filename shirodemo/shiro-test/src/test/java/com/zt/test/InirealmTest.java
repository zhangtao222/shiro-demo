package com.zt.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @description: 使用IniRealm进行认证授权 IniRealm可以设置权限,通过配置文件的方式
 * @version: v1.0.0
 * @author: zhangtao
 * @date: 2019/4/18 15:08
 */
public class InirealmTest {

    private IniRealm iniRealm = new IniRealm("classpath:user.ini");

    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

    @Test
    public void test(){

        defaultSecurityManager.setRealm(iniRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangtao","123");

        subject.login(usernamePasswordToken);

        System.out.println("is authenticated " + subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermissions("user:delete","user:update");

    }
}
