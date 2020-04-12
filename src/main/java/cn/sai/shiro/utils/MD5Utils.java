package cn.sai.shiro.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Utils {
    /**
     * 参数：密码，盐，次数
    */
    public static String md5(String source, String salt, Integer hashIterations){
        return new Md5Hash(source,salt,hashIterations).toString();
    }
}
