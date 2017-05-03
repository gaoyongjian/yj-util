package com.yj.util.security;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



public class DESUtil {
		/**
		 * ��Կiv
		 */
	    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
	    /**
	     * ���� encryptDES
	     * @param encryptString
	     * @param encryptKey
	     * @return
	     * @throws Exception
	     */
		public static String encryptDES(String encryptString, String encryptKey)throws Exception {
			 IvParameterSpec zeroIv = new IvParameterSpec(iv);
			 SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			 Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			 cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			 byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
			 return Base64Util.encode(encryptedData);
		 }
		/**
		 * ����decryptDES
		 * @param decryptString
		 * @param decryptKey
		 * @return
		 * @throws Exception
		 */
		public static String decryptDES(String decryptString, String decryptKey)throws Exception {
		   byte[] byteMi = Base64Util.decode(decryptString);
		   IvParameterSpec zeroIv = new IvParameterSpec(iv);
		   SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		   Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		   cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		   byte decryptedData[] = cipher.doFinal(byteMi);
		   return new String(decryptedData);
		}
	    public static void main(String[] args) throws Exception{
	    	String key = "WAK@_#MB";

	    	String s1 = encryptDES("2905298", key);

	    	System.out.println(s1);

	        String s4=decryptDES("wxne3pikIZc=", key);  

	        //String s5=decryptBasedDes1(s4);  
	       //String s6=decryptBasedDes(s5);  
	        System.out.println(s4);  
	        String encode = URLEncoder.encode("xiB2/CxwBaY=", "utf-8");
			System.out.println(encode);
	    }
}
