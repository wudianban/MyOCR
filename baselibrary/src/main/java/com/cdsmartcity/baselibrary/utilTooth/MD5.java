package com.cdsmartcity.baselibrary.utilTooth;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5
 */
@SuppressLint("DefaultLocale")
public final class MD5 {

	/**
	 * 获取MD5消息摘要
	 *
	 * @param data  源数据
	 * @return MD5消息摘要
	 */
	public static final byte[] getMD5(byte[] data) {
		byte[] md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md5 = md.digest(data);
		} catch (Exception e) {
		}
		return md5;
	}

	/*
	 * MD5字符串
	 */
	@SuppressLint("DefaultLocale")
	public static final String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			if (!TextUtils.isEmpty(str)) {
				messageDigest.update(str.getBytes("UTF-8"));
			}
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toUpperCase();
	}

	/*
	 * MD5字符串
	 */
	public static final String getMD5Str(byte[] data) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(data);
		} catch (NoSuchAlgorithmException e) {
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toUpperCase();
	}


	public static String getMd5Php(String inputStr){
		String md5Str = inputStr;
		try {
			if(inputStr != null) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(inputStr.getBytes());
				BigInteger hash = new BigInteger(1, md.digest());
				md5Str = hash.toString(16);
				if((md5Str.length() % 2) != 0) {
					md5Str = "0" + md5Str;
				}
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return md5Str;
	}

	public static String md5Php(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}
	public static String hexdigest(String paramString) {
		try {
			String str = hexdigest(paramString.getBytes());
			return str;
		} catch (Exception localException) {
		}
		return null;
	}


	public static String hexdigest(byte[] paramArrayOfByte) {
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramArrayOfByte);
			byte[] arrayOfByte = localMessageDigest.digest();
			char[] arrayOfChar = new char[32];
			int i = 0;
			int j = 0;
			while (true) {
				if (i >= 16)
					return new String(arrayOfChar);
				int k = arrayOfByte[i];
				int m = j + 1;
				arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
				j = m + 1;
				arrayOfChar[m] = hexDigits[(k & 0xF)];
				i++;
			}
		} catch (Exception localException) {
		}
		return null;
	}
	private static final char[] hexDigits = { 48, 49, 50, 51, 52, 53, 54, 55,
			56, 57, 97, 98, 99, 100, 101, 102 };

}
