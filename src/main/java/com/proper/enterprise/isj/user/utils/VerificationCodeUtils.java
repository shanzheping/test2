package com.proper.enterprise.isj.user.utils;

/**
 * Created by think on 2016/8/22 0022. 随机生成验证码
 */
public class VerificationCodeUtils {
	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 *            验证码长度
	 * @return 验证码字符串
	 */
	public static String createRandom(boolean numberFlag, int length) {
		StringBuilder retStr = null;
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = new StringBuilder();
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr = retStr.append(strTable.charAt(intR));
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);
		return retStr.toString();
	}
}
