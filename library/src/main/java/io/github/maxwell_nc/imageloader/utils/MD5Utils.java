package io.github.maxwell_nc.imageloader.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5数字摘要工具
 */
public class MD5Utils {

	/**
	 * 获得32位的MD5摘要值
	 * @param content 要计算的文本内容 
	 * @return MD5值
	 */
	public static String getMD5String(String content) {
		byte[] digestBytes = null;
		try {
			digestBytes = MessageDigest.getInstance("md5").digest(
					content.getBytes());
		} catch (NoSuchAlgorithmException e) {
			//can not reach
		}
		String md5Code = new BigInteger(1, digestBytes).toString(16);
		//补全不足位数
		for (int i = 0; i < 32 - md5Code.length(); i++) {
			md5Code = "0" + md5Code;
		}
		return md5Code;
	}
}
