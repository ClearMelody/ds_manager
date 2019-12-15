package com.waiterlong.vipmis.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/10
 * \*
 * \* Description
 *
 * @author wyk
 */
public class Md5Utils {
    public static String HashAlgorithName = "MD5";
    public static int HashIterations = 2;
    public static String encryptToMD5(String username, String password){
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        return new SimpleHash(HashAlgorithName, password, credentialsSalt, HashIterations).toString();
    }
}
