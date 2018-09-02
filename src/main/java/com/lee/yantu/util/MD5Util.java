package com.lee.yantu.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {
	/**
	 * MD5 加密
	 * 提供 要加密的字符串
	 * 返回 加密过的字符串
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String Encode(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String result = null;
		//获取加密的计算方式
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		//进行加密操作
		BASE64Encoder base64 = new BASE64Encoder();
		result = base64.encode(md5.digest(str.getBytes("utf-8")));
		return result;
	}

	/**
	 * 判断加密过的数据
	 * 提供 数据库中的字符（已加密），前台提供的子串（还未加密）
	 * 返回 两个字符是否一致
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static boolean check(String oldstr ,String newstr) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String temp = Encode(newstr);
		return oldstr.equalsIgnoreCase(temp);
	}
}
