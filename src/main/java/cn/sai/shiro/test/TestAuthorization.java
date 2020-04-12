package cn.sai.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class TestAuthorization {

    private static final transient Logger log = LoggerFactory.getLogger(TestAuthentication.class);

    public static void main(String[] args) {
        log.info("my first shiro");
        //1.创建安全管理器的工厂对象
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2.使用工厂创建安全管理器
        SecurityManager securityManager = factory.getInstance();
        //3.把当前的安全管理器绑定到当前的线程
        SecurityUtils.setSecurityManager(securityManager);
        //4.使用SecurityUtils.getSubject得到主体对象
        Subject subject = SecurityUtils.getSubject();
        //5.封装用户名，密码
        AuthenticationToken token = new UsernamePasswordToken("zhangsan","123");
        try {
            subject.login(token);
            System.out.println("认证通过");
        }catch (AuthenticationException e){
            System.out.println("用户名或密码错误");
        }
        //判断是否认证通过
        boolean authenticated = subject.isAuthenticated();
        System.out.println("是否认证通过："+authenticated);

        //角色判断
        boolean hasRole2 = subject.hasRole("role2");
        System.out.println("是否有role2的角色：" +hasRole2);

        List<String> list = Arrays.asList("role1","role2","role3");
        boolean[] hasRoles = subject.hasRoles(list);
        for (boolean b:hasRoles) {
            System.out.println(b);
        }

        //权限判断
        subject.isPermitted("user:query");
        boolean[] permitted = subject.isPermitted("user:query", "user:add", "user:export");
        boolean permittedAll = subject.isPermittedAll("user:query", "user:add", "user:export");

    }
}
