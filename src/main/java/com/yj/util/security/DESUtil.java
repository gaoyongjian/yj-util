package com.yj.util.security;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class DESUtil {
    /**
     * iv
     */
    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};


    /**
     * @return DES算法密钥
     */
    public static byte[] generateKey() {
        try {

            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 生成一个DES算法的KeyGenerator对象
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            kg.init(sr);

            // 生成密钥
            SecretKey secretKey = kg.generateKey();

            // 获取密钥数据
            byte[] key = secretKey.getEncoded();

            return key;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("DES算法，生成密钥出错!");
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 加密函数
     *
     * @param data 加密数据
     * @param key  密钥
     * @return 返回加密后的数据
     */
    public static byte[] encryptECB(byte[] data, byte[] key) {

        try {

            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // using DES in ECB mode
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

            // 执行加密操作
            byte encryptedData[] = cipher.doFinal(data);

            return encryptedData;
        } catch (Exception e) {
            System.err.println("DES算法，加密数据出错!");
            e.printStackTrace();
        }

        return null;
    }





    /**
     * 解密函数
     *
     * @param data
     *            解密数据
     * @param key
     *            密钥
     * @return 返回解密后的数据
     */
    public static byte[] decryptECB(byte[] data, byte[] key) {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // byte rawKeyData[] = /* 用某种方法获取原始密匙数据 */;

            // 从原始密匙数据创建一个DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // using DES in ECB mode
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

            // 正式执行解密操作
            byte decryptedData[] = cipher.doFinal(data);

            return decryptedData;
        } catch (Exception e) {
            System.err.println("DES算法，解密出错。");
            e.printStackTrace();
        }

        return null;
    }



    /**
     * encryptDES
     *
     * @param encryptString
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String encryptCBC(String encryptString, String encryptKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64Util.encode(encryptedData);
    }

    /**
     * decryptDES
     *
     * @param decryptString
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static String decryptCBC(String decryptString, String decryptKey) throws Exception {
        byte[] byteMi = Base64Util.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);
        return new String(decryptedData);
    }

    public static void main(String[] args) throws Exception {
//        String key = "ingage30";
//
//        byte[] t1=encryptECB("ingage".getBytes(),key.getBytes());
//
//        System.out.println(Base64.encodeBase64String(t1));
//
//        System.out.println(new String(decryptECB(t1,key.getBytes())));
//
//
//        String s1 = encryptCBC("ingage", key);
//        System.out.println(s1);
//
//        System.out.println(decryptCBC(s1, key));

        System.out.println(Base64.encodeBase64String(generateKey()));

    }
}
