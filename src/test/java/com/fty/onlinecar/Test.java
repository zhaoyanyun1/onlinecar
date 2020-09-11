package com.fty.onlinecar;

import cn.hutool.core.codec.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

public class Test {

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
    private static Key key;
    private static Cipher cipher;


    public static void main(String[] args) {
        String encrypData = "1XpGQpQDmD2npkD0XWpvwTJJrdgekHQwzj7NMcLwJjYsY55bA4I4kQaoLfxI/9bI0DnwoBXYiyFRdErPe/n7khbUR08KfvGIX+ik+T2jG5/fkQ6h1aZEqPLi/9gtjiEhIDmQZ2N3CW9IRWdj1nWVc0Z9nRJtKm0F5TvjY3S8Y2VyCcS2oXxZ64QeF2I3voq2vBf9/+0StA8PBtHBqou/GA==";
        String ivData="Ao6VZkygiVtY45nW0CAazg==";
        String sessionKey ="r\\/TZxcIZfQGoPEIUMV1FyQ==";
//        byte[] encrypData = Base64.decode(encrypdatastr);
//        byte[] ivData = Base64.decode(ivdate);
//        byte[] sessionKey = Base64.decode("hoE6uqgPam9cIeL3Ojktwg==");
        String str="";
        try {
            str = decryptData(encrypData,sessionKey,ivData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(str);

    }
    public static String decryptData(String encryptDataB64, String sessionKeyB64, String ivB64) {
        return new String(
                decryptOfDiyIV(
                        Base64.decode(encryptDataB64),
                        Base64.decode(sessionKeyB64),
                        Base64.decode(ivB64)
                )
        );
    }
    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param ivs           自定义对称解密算法初始向量 iv
     * @return 解密后的字节数组
     */
    private static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    private static void init(byte[] keyBytes) {
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(ALGORITHM_STR, "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
