package com.proper.enterprise.isj.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proper.enterprise.isj.user.model.UserInfo;
import com.proper.enterprise.isj.user.service.IUserInfoService;
import com.proper.enterprise.isj.user.utils.MobileNoUtils;
import com.proper.enterprise.isj.user.utils.VerificationCodeUtils;
import com.proper.enterprise.platform.api.auth.model.User;
import com.proper.enterprise.platform.api.auth.service.UserService;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.MD5Util;

/**
 * Created by think on 2016/8/23 0023. 用户相关信息
 *
 */

@RestController
@RequestMapping(path = "/login")
public class UserInfoController extends BaseController {

	@Autowired
	IUserInfoService userInfoService;

	@Autowired
	UserService userService;

	/**
	 * 用户登录
	 * 
	 * @param phoneNo
	 *            用户名
	 * @param password
	 *            密码
	 * @return 登录用户
	 */
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public ResponseEntity<User> login(String phoneNo, String password) {
		User user = userService.getByUsername(phoneNo);
		if (user == null || !user.getPassword().equals(MD5Util.md5Hex(password))) {
			user = null;
		}
		return responseOfGet(user);
	}

	/**
	 * 获取用户首页信息
	 *
	 * @param userId
	 *            用户Id
	 * @return
	 */
	@RequestMapping(path = "/getUserInfo", method = RequestMethod.GET)
	public ResponseEntity<UserInfo> getUserInfo(String userId) {
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
		return responseOfGet(userInfo);
	}

	/**
	 * 修改用户信息
	 */
	@RequestMapping(path = "/modifyUserInfo", method = RequestMethod.POST)
	public ResponseEntity<UserInfo> modifyUserInfo(@RequestParam(required = true) String userId,
			@RequestParam(required = true) String name,
			@RequestParam(required = true) String phoneNo) {
		UserInfo info = userInfoService.getUserInfoByUserId(userId);
		if (info != null) {
			info.setName(name);
			info.setTelephone(phoneNo);
			info = userInfoService.saveOrUpdateUserInfo(info);
		}
		return responseOfPut(info);
	}

	/**
	 * 通过手机号验证用户是否存在
	 */
	@RequestMapping(path = "/forgetPassword", method = RequestMethod.GET)
	public ResponseEntity<String> checkRegistTelephone(
			@RequestParam(required = true) String phoneNo) {
		String resultMsg = "";
		HttpStatus httpStatus = HttpStatus.OK;
		if (!MobileNoUtils.isMobileNo(phoneNo)) {
			resultMsg = "请输入有效电话号码";
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {
			User user = userService.getByUsername(phoneNo);
			if (user != null) {
				VerificationCodeUtils.sendVerificationCode(phoneNo);
				resultMsg = "";
				httpStatus = HttpStatus.OK;
			} else {
				resultMsg = "该手机号不存在";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		}
		return new ResponseEntity(resultMsg, httpStatus);
	}

	/**
	 * 修改密码
	 * 
	 * @param phoneNo
	 *            电话号
	 * @param password
	 *            新密码
	 * @param verificationCode
	 *            验证码
	 */
	@RequestMapping(path = "/forgetPasswordSetting", method = RequestMethod.POST)
	public void modifyPassword(@RequestParam(required = true) String phoneNo,
			@RequestParam(required = true) String password,
			@RequestParam(required = true) String verificationCode) {

		if (VerificationCodeUtils.getVerificationcodeMap().containsKey(phoneNo)
				&& VerificationCodeUtils.getVerificationcodeMap().get(phoneNo)
						.equals(verificationCode)) {
			User user = userService.getByUsername(phoneNo);
			user.setPassword(MD5Util.md5Hex(password));
			userService.save(user);
		}
	}
}
