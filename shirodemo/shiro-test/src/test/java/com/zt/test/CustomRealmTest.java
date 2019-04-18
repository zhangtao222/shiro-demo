package com.zt.test;

import com.zt.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @description: 自定义的Realm测试
 * @version: v1.0.0
 * @author: zhangtao
 * @date: 2019/4/18 17:12
 */
public class CustomRealmTest {


    private CustomRealm customRealm = new CustomRealm();


    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

    @Test
    public void test() {

        //对密码加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //设置加密算法
        matcher.setHashAlgorithmName("md5");
        //加密次数
        matcher.setHashIterations(1);

        customRealm.setCredentialsMatcher(matcher);

        defaultSecurityManager.setRealm(customRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangtao", "123");

        subject.login(usernamePasswordToken);

        System.out.println("is authenticated " + subject.isAuthenticated());

        subject.checkRoles("admin", "user");

        subject.checkPermissions("user:add", "user:delete");

    }
}
