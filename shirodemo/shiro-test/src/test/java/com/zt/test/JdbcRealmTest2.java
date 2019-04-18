package com.zt.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @description: JdbcRealm测试,从数据库中查询用户和角色和权限信息.使用自定义的的sql语句去数据库中查询信息(不按照默认规则创建表)
 * @version: v1.0.0
 * @author: zhangtao
 * @date: 2019/4/18 15:51
 */
public class JdbcRealmTest2 {

    //druid连接池
    private DruidDataSource druidDataSource = new DruidDataSource();

    {
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/shirotest");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
    }

    private JdbcRealm jdbcRealm = new JdbcRealm();



    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

    @Test
    public void test(){

        //设置连接池,连接数据库
        jdbcRealm.setDataSource(druidDataSource);

        //开启权限校验的开关,默认是关闭的
        jdbcRealm.setPermissionsLookupEnabled(true);

        String sql = "select password from test_user where username = ?";
        //设置自定义的认证查询语句
        jdbcRealm.setAuthenticationQuery(sql);

        String roleSql = "select role from test_user_role where username = ?";

        //设置自定义的角色查询语句
        jdbcRealm.setUserRolesQuery(roleSql);

        String permissionSql = "select permission from test_role_permission where role = ?";
        //设置自定义的权限查询语句
        jdbcRealm.setPermissionsQuery(permissionSql);

        defaultSecurityManager.setRealm(jdbcRealm);


        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("xiaoming","123");

        subject.login(usernamePasswordToken);

        System.out.println("is authenticated " + subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermissions("user:update","user:queryAll");

    }
}
