package cn.sai.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * shiro的认证
 * 使用shiro.ini文件
 * @author: sai
 * @time: 2020/4/9 17:33
*/

public class TestAuthentication{
    //日志输出
    private static final transient Logger log = LoggerFactory.getLogger(TestAuthentication.class);

    public static void main(String[] args) {
        log.info("my first shiro");
        //1.创建安全管理器的工厂对象
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2.使用工厂创建安全管理器
        DefaultSecurityManager securityManager = (DefaultSecurityManager)factory.getInstance();
        //3.创建userRealm
        UserRealm userRealm = new UserRealm();
        //4.给securityManager注入userRealm
        securityManager.setRealm(userRealm);
        //5.把当前的安全管理器绑定到当前的线程
        SecurityUtils.setSecurityManager(securityManager);
        //6.使用SecurityUtils.getSubject得到主体对象
        Subject subject = SecurityUtils.getSubject();
        //7.封装用户名，密码
        AuthenticationToken token = new UsernamePasswordToken("zhangsan","123");
        //8.认证
//        try{
//            subject.login(token);
//        }catch (IncorrectCredentialsException e){
//            System.out.println("密码不正确");
//        }catch (UnknownAccountException e){
//            System.out.println("用户名不存在");
//        }
        try {
            subject.login(token);
            System.out.println("认证通过");
        }catch (AuthenticationException e){
            System.out.println("用户名或密码错误");
        }

        boolean role1 = subject.hasRole("role1");
        System.out.println("has role1: "+role1);
        boolean permitted = subject.isPermitted("user:query");
        System.out.println("is permitted: "+permitted);
    }
}
