package com.yj.util.security;

import java.security.MessageDigest;

public class SecurityUtil {
	public static MessageDigest md5;

	public static String md5code(String str) {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++){
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	
	public static void main(String[] args) {

		System.out.println(SecurityUtil.md5code("w0CDwKBn+cia0KVdzi9nxjTGkWAt5UiGLNiB2VkjbYrW5ZWWhAmJCbyTebMX rlxVhzhWvHZKG6ClwHz5eqN3nT37NNAAPfEinW17CNgB/I65ZhUiofBEd9tp xzvzIa88TSykyHllTSrhir482pYL9OB4XE+qJ9hqltbJv7Kj6o7Axgn0cgf5 3496AQupKpqA6krsHAwD8Hd85vHOKfb3jspZF2Vqz5lUt7kKJLTvJVe1RNaz TbxzecmLn7q39Uh2NTwqgOYm2NLQoJStMPxekY61kVZIf6frnihx2n0KHZVG RyPA35f58L7IdRUbtvAxB/NuxgFNWDQbvty/Th0V/C3ucmP/LTGDcTwA0ncR 7Jk5y9v0ONck9KvCdJCUrhElEskjzESPIZdSAelo/eKW/qHjTImKinWQk+3J Lre9qAFtgveJPO3GV5sLUmJQuUy6kMaYuvSpwqEBvv9W9jMbrg=="));
	}
}
