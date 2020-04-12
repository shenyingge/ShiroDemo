package cn.sai.shiro.test;

import cn.sai.shiro.domain.ActiveUser;
import cn.sai.shiro.domain.User;
import cn.sai.shiro.service.UserService;
import cn.sai.shiro.service.impl.UserServiceImpl;
import cn.sai.shiro.utils.MD5Utils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRealm extends AuthorizingRealm {

    private UserService userService = new UserServiceImpl();

    public UserRealm() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密算法
        credentialsMatcher.setHashAlgorithmName("md5");
        //指定散列次数
        credentialsMatcher.setHashIterations(1024);
        //设置匹配
        setCredentialsMatcher(credentialsMatcher);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        /**
         * 以前登录逻辑是把用户名和密码发到数据库去匹配
         * 在shiro里是先用户名把用户对象查询出来，再做密码匹配
        */
        String username = (String) token.getPrincipal();
        System.out.println(username);
        User user = userService.queryUserByUsername(username);

        if(null != user){

            //要从数据库查
            List<String> roles = new ArrayList<>();
            List<String> perms = new ArrayList<>();

            ActiveUser activeUser = new ActiveUser(user,roles,perms);

            //密码使用数据库中查询的密文
            /**
             * 参数1：可以传任意对象
             * 参数2：数据库中查出的密码
             * 参数3：realm名
            */
            //SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser,user.getPassword(),getName());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, user.getPassword(), ByteSource.Util.bytes(username),this.getName());
            return info;
        }else{
            return null;
        }

    }

    /**
     * 授权
     * 调用subject.hasRole的时候就被调用
    */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        System.out.println("doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //获取用户信息
        ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();

        //添加角色
        Collection<String> roles = activeUser.getRoles();
        if(roles != null && roles.size() > 0){
            info.addRoles(roles);
        }

        //添加权限
        Collection<String> permissions = activeUser.getPermissions();
        if(permissions != null && permissions.size() > 0){
            info.addStringPermissions(permissions);
        }

        /*
        超级管理员
        if(activeUser.getUser().getType() == 0){
            info.addStringPermissions("*:*");
        }
        * */
        return info;
    }
}
