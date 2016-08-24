package com.proper.enterprise.isj.user.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by think on 2016/8/23 0023.
 */
public class MobileNoUtils {
	/**
	 * 验证手机号的方法
	 *
	 * @param mobiles
	 *            手机号码
	 * @return 是否符合规则
	 */
	public static boolean isMobileNo(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

}
