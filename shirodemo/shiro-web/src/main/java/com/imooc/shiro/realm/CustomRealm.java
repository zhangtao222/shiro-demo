package com.imooc.shiro.realm;

import com.imooc.dao.UserDao;
import com.imooc.vo.User;
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

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: JiaZF
 * Date: 2018/8/6
 * Time: 8:44
 * Description:
 */
public class CustomRealm extends AuthorizingRealm {

    @Resource
     private UserDao userDao;

    Map<String ,String> userMap =new HashMap<String, String>();
    {
        userMap.put("Mark","283538989cef48f3d7d8a1c1bdf2008f");
        super.setName("customRealm");
    }
    //做授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        //从数据库或者从缓存中获取数据
        Set<String> roles = getRolesByUserName(username);
        //获得权限
        Set<String> permissions =getPermissionsByUserName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo =new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    /**
     * 通过角色 获得权限
     * @param username
     * @return
     */
    private Set<String> getPermissionsByUserName(String username) {
        List<String> roles = userDao.getPermissionsByUserName(username);
        Set<String> sets = new HashSet<String>(roles);
       /* sets.add("user:delete");
        sets.add("user:add");*/
        return sets;
    }

    /**
     * 模拟获得   角色
     * @param username
     * @return
     */
    private Set<String> getRolesByUserName(String username) {
        System.out.println("从数据库获取数据"+"========");
        List<String> roles = userDao.getRolesByUsername(username);
        Set<String> sets = new HashSet<String>(roles);
        /*Set<String> sets =new HashSet<String>();
        sets.add("admin");
        sets.add("user");*/
        return sets;
    }

    // 做认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //1 从主体中获得用户名
        String username  = (String)token.getPrincipal();

        //2 使用用户名，从数据库获得凭证
        String password = getPasswordByUsername(username);
        if(password==null){
            return  null;
        }
        SimpleAuthenticationInfo authenticationInfo =new SimpleAuthenticationInfo(
                username,password,"customRealm"
        );
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return authenticationInfo;
    }

    /**
     * 模拟数据库查询凭证
     * @param username
     * @return
     */
    private String getPasswordByUsername(String username) {
        User user = userDao.getUserByUsername(username);
        if(user ==null){
            return null;
        }
        return user.getPassword();
                //userMap.get(username);
    }


    public static void main(String[] args) {
        Md5Hash hash = new Md5Hash("123456","xiaoming");
        System.out.println(hash.toString());
    }
}
