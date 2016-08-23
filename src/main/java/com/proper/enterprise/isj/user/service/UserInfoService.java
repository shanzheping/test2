package com.proper.enterprise.isj.user.service;

import com.proper.enterprise.isj.user.model.UserInfo;
import com.proper.enterprise.platform.auth.common.entity.UserEntity;

/**
 * Created by think on 2016/8/15 0015. 用户扩展信息接口
 */
public interface UserInfoService {

	/**
	 * 保存信息
	 */
	UserInfo saveOrUpdateUserInfo(UserInfo userInfo);

	/**
	 * 根据用户Id查询信息
	 */
	UserInfo getUserInfoByUserId(String userId);

	UserInfo getUserInfoByTelephone(String telephone);

	void saveUserAndUserInfo(UserEntity user, UserInfo userInfo) throws Exception;
}
