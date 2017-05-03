package com.yj.util.date;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ͨ�÷���ʵ����
 * @author SCORPIO
 * @since 2013-06-21
 */
public class Until {

     public static String[][] bankkey = {
    		{"icbc","7"},{"ccb","8"},{"abc","9"},{"hkbc","103"},
			{"boc","10"},{"cmb","22"},{"ceb","23"},{"cib","24"},
			{"cmbc","25"},{"bocomm","26"},{"citic","29"},{"sdb","32"},
			{"gdb","30"},{"hxb","31"},{"spd","33"},{"pingan","65"},
			{"bos","69"},{"psbc","70"},{"bccb","71"},{"nbcb","72"},
			{"bsb","73"},{"jsbk","74"},{ "njcb","75"},{"beai","76"},
			{"dlcb","80"},{"dgcb","79"},{"hrbb","78"},{"cqcb","77"},
			{"gzcb","81"},{"hccb","82"},{"hscb","83"},{"sjbc","84"},
			{"tccb","91"},{"srcb","100"},{"wzcb","101"},{"cscb","102"}			
	 };
     
	/**
	 * �������ID��ȡ���е�Ӣ�ļ��
	 */
	public static String getBankENSignName(String bankID){
		String enName = "";
    	for (int i = 0; i < bankkey.length; i++) {
 	       String[] temp = bankkey[i];
 	       if(temp[1].equalsIgnoreCase(bankID))
 	    	  enName =temp[0];
 		     break;
 	    }
		return enName ;
	}
	
	public static String md5(String text, String charSet) {
		MessageDigest msgDigest = null;

		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}

		try {
			msgDigest.update(text.getBytes(charSet));//注意该接口是按照指定编码形式加密
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System doesn't support your EncodingException.");

		}

		byte[] bytes = msgDigest.digest();
		String md5Str = new String(encodeHex(bytes));
//		System.out.println(md5Str);
		return md5Str;
	}

	public static char[] encodeHex(byte[] data) {
		int l = data.length;
		char[] out = new char[l << 1];
		char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6','7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}
		return out;
	}
	public static String getDateYearMonthDay(Date date) {
		return getDateString(date, "yyyy-MM");
	}
	
	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
	
}
