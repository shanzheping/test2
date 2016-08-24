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
import com.proper.enterprise.isj.user.utils.IdcardUtils;
import com.proper.enterprise.isj.user.utils.MobileNoUtils;
import com.proper.enterprise.isj.user.utils.VerificationCodeUtils;
import com.proper.enterprise.platform.api.auth.model.User;
import com.proper.enterprise.platform.api.auth.service.UserService;
import com.proper.enterprise.platform.auth.common.entity.UserEntity;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.MD5Util;
import com.proper.enterprise.platform.core.utils.StringUtil;

/**
 * Created by think on 2016/8/15 0015. 用户注册
 */
@RestController
@RequestMapping(path = "/login")
public class UserRegController extends BaseController {

	@Autowired
	private IUserInfoService userInfoService;

	@Autowired
	private UserService userService;

	/**
	 * 通过手机号验证用户是否存在
	 */
	@RequestMapping(path = "/regist", method = RequestMethod.GET)
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
				resultMsg = "该手机号已存在";
				httpStatus = HttpStatus.BAD_REQUEST;
			} else {
				this.sendVerificationCode(phoneNo);
				resultMsg = "";
				httpStatus = HttpStatus.OK;
			}
		}
		return new ResponseEntity(resultMsg, httpStatus);
	}

	/**
	 * 发送验证码
	 */
	@RequestMapping(path = "/sendVerificationCode", method = RequestMethod.POST)
	public void sendVerificationCode(@RequestParam(required = true) String phoneNo) {
		VerificationCodeUtils.sendVerificationCode(phoneNo);

	}

	/**
	 * 验证身份证号输入是否正确
	 */
	@RequestMapping(path = "/checkUserIdCard", method = RequestMethod.GET)
	public ResponseEntity<String> checkUserIdCard(@RequestParam(required = true) String idCard) {

		String resultMsg = "";
		HttpStatus httpStatus = HttpStatus.OK;
		if (StringUtil.isEmpty(idCard)) {
			resultMsg = "身份证号不能为空";
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {
			if (!IdcardUtils.validateCard(idCard)) {
				resultMsg = "身份证号输入有误";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		}
		return new ResponseEntity(resultMsg, httpStatus);
	}

	/**
	 * 注册
	 */
	@RequestMapping(path = "/registSetting", method = RequestMethod.PUT)
	public ResponseEntity<String> registUser(@RequestParam(required = true) String phoneNo,
			@RequestParam(required = true) String password,
			@RequestParam(required = true) String verificationCode,
			@RequestParam(required = true) String name,
			@RequestParam(required = true) String idCard) {

		String resultMsg = "";
		HttpStatus httpStatus = HttpStatus.OK;
		if (!VerificationCodeUtils.getVerificationcodeMap().containsKey(phoneNo)) {
			resultMsg = "验证码无效";
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (VerificationCodeUtils.getVerificationcodeMap().get(phoneNo)
				.equals(verificationCode)) {
			resultMsg = "请输入正确的验证码";
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (!MobileNoUtils.isMobileNo(phoneNo)) {
			resultMsg = "手机号码无效";
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {
			if (!IdcardUtils.validateCard(idCard)) {
				resultMsg = "身份证号输入有误";
				httpStatus = HttpStatus.BAD_REQUEST;
			} else {
				UserEntity user = new UserEntity();
				user.setUsername(phoneNo);
				user.setPassword(MD5Util.md5Hex(password));
				UserInfo userInfo = new UserInfo();
				userInfo.setIdentityCard(idCard);
				userInfo.setName(name);
				userInfo.setVisitFlag(true);
				try {
					userInfo = userInfoService.saveUserAndUserInfo(user, userInfo);
					resultMsg = userInfo.getUserId();
				} catch (Exception e) {
					e.printStackTrace();
					resultMsg = "保存用户信息失败";
					httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}
		}
		return new ResponseEntity(resultMsg, httpStatus);
	}

}
