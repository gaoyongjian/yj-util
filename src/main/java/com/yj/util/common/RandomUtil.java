package com.yj.util.common;

import java.util.Random;

public class RandomUtil {

	public static int random(){
		Random r=new Random();
		return r.nextInt(100000)+100000;
		
	}
}
