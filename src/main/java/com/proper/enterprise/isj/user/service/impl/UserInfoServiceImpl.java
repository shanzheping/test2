package com.proper.enterprise.isj.user.service.impl;

import com.proper.enterprise.platform.api.auth.model.User;
import com.proper.enterprise.platform.api.auth.service.UserService;
import com.proper.enterprise.platform.auth.common.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proper.enterprise.isj.user.model.UserInfo;
import com.proper.enterprise.isj.user.repository.UserInfoRepository;
import com.proper.enterprise.isj.user.service.UserInfoService;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by think on 2016/8/15 0015.
 *
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	UserService userService;

	@Override
	public UserInfo saveOrUpdateUserInfo(UserInfo userInfo) {
		userInfo = userInfoRepository.save(userInfo);
		return userInfo;
	}

	@Override
	public UserInfo getUserInfoByUserId(String userId) {
		UserInfo userInfo = userInfoRepository.getByUserId(userId);
		return userInfo;
	}

	@Override
	public UserInfo getUserInfoByTelephone(String telephone) {
		UserInfo userInfo = userInfoRepository.getByTelephone(telephone);
		return userInfo;
	}

	@Override
	public void saveUserAndUserInfo(UserEntity user, UserInfo userInfo) throws Exception {
		User us = userService.save(user);
		userInfo.setUserId(us.getId());
		userInfoRepository.save(userInfo);
	}
}
