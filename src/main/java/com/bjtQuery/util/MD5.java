package com.bjtQuery.util;

import java.security.MessageDigest;

public class MD5 {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	// 十六进制下数字到字符的映射数组

	/** 把inputString加密 */
	public static String encryption(String inputString) {
		return encodeByMD5(inputString);
	}


	/**
	 * 验证输入的签名是否正确
	 *
	 * @param Sign
	 *            真正的签名（加密后的真签名）
	 * @param inputString
	 *            输入的字符串
	 * @return 验证结果，boolean类型
	 */
	public static boolean authenticateSign(String Sign, String inputString) {
		if (Sign.equals(encodeByMD5(inputString))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证输入的密码是否正确
	 * 
	 * @param password
	 *            真正的密码（加密后的真密码）
	 * @param inputString
	 *            输入的字符串
	 * @return 验证结果，boolean类型
	 */
	public static boolean authenticatePassword(String password,
			String inputString) {
		if (password.equals(encodeByMD5(inputString))) {
			return true;
		} else {
			return false;
		}
	}

	/** 对字符串进行MD5编码 */
	public static String encodeByMD5(String originString) {
		if (originString != null) {
			try {
				// 创建具有指定算法名称的信息摘要
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
				byte[] results = md.digest(originString.getBytes());
				// 将得到的字节数组变成字符串返回
				String resultString = byteArrayToHexString(results);
//				return resultString.toUpperCase();
				return resultString.toLowerCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 轮换字节数组为十六进制字符串
	 * 
	 * @param b
	 *            字节数组
	 * @return 十六进制字符串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将一个字节转化成十六进制形式的字符串
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static void main(String[] args) {
		String password = MD5.encryption("33937632972609945627d7cf4b438d413a9daff57f3c4bae58abc1234561459320545");
//		System.out.println("对123用MD5摘要后的字符串：" + password);
//		String inputString = "apptoken=ec55122f86274c89b42e1e3aa4e7f305&noncestr=0123456789&appkey=yilin@kmwlyy";
//
//		System.out.println("123与密码匹配"+ MD5.authenticatePassword(password, inputString));
		System.out.println(password);
	}
}
