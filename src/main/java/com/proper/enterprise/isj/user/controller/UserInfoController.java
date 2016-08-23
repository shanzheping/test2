package com.proper.enterprise.isj.user.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proper.enterprise.isj.user.model.UserInfo;
import com.proper.enterprise.isj.user.service.UserInfoService;
import com.proper.enterprise.isj.user.utils.IdcardUtils;
import com.proper.enterprise.platform.api.auth.service.UserService;
import com.proper.enterprise.platform.auth.common.entity.UserEntity;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.MD5Util;
import com.proper.enterprise.platform.core.utils.StringUtil;

/**
 * Created by think on 2016/8/15 0015.
 *
 */
@RestController
@RequestMapping(path = "/base/userinfo")
public class UserInfoController extends BaseController {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserService userServiceTestImpl;

	/**
	 * 通过手机号验证用户是否存在
	 */
	@RequestMapping(path = "/checkTelephone", method = RequestMethod.GET)
	public ResponseEntity<String> checkRegistTelephone(
			@RequestParam(required = true) String telephone) {

		String resultMsg = "";
		HttpStatus httpStatus = HttpStatus.OK;
		if (!this.isMobileNo(telephone)) {
			resultMsg = "请输入有效电话号码";
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {
			UserInfo userInfo = userInfoService.getUserInfoByTelephone(telephone);
			if (userInfo != null) {
				resultMsg = "该手机号已存在";
				httpStatus = HttpStatus.BAD_REQUEST;
			} else {
				resultMsg = "";
				httpStatus = HttpStatus.OK;
			}
		}
		return new ResponseEntity(resultMsg, httpStatus);
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
	@RequestMapping(path = "/registUser", method = RequestMethod.PUT)
	public ResponseEntity<String> registUser(String phoneNum,
			@RequestParam(required = true) String password, String verificationCode,
			@RequestParam(required = true) String name,
			@RequestParam(required = true) String idCard) {
		String resultMsg = "";
		HttpStatus httpStatus = HttpStatus.OK;
		if (!this.isMobileNo(phoneNum)) {
			resultMsg = "手机号码无效";
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {
			if (!IdcardUtils.validateCard(idCard)) {
				resultMsg = "身份证号输入有误";
				httpStatus = HttpStatus.BAD_REQUEST;
			} else {
				UserEntity user = new UserEntity();
				user.setUsername(phoneNum);
				user.setPassword(MD5Util.md5Hex(password));
				UserInfo userInfo = new UserInfo();
				userInfo.setIdentityCard(idCard);
				userInfo.setName(name);
			}
		}
		return new ResponseEntity<String>(resultMsg, httpStatus);
	}

	/**
	 * 注册
	 */
	@RequestMapping(path = "/registUser", method = RequestMethod.POST)
	public ResponseEntity<String> addUserInfo(String userName, String sexCode, String idCard,
			String phoneNum) {
		if (!IdcardUtils.validateCard(idCard)) {
			System.out.println(1);
		}
		return null;
	}

	@RequestMapping(path = "/getUserInfo", method = RequestMethod.GET)
	public ResponseEntity<UserInfo> getUserInfo(String userId) {
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
		return responseOfGet(userInfo);
	}

	public boolean isMobileNo(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

}
