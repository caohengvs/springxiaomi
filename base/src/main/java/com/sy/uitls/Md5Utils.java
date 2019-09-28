package com.sy.uitls;

import java.math.BigInteger;
import java.security.MessageDigest;


public class Md5Utils {

    public static void main(String[] args) {
        String s = md5("123456");
        System.out.println(s);
    }
    public static String md5(String str){
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes("utf-8"));
            byte[] digest = messageDigest.digest();
            BigInteger bigInteger=new BigInteger(1, digest);
            String secret=bigInteger.toString(16);
            return  secret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
