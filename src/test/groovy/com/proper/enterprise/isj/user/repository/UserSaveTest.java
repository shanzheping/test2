package com.proper.enterprise.isj.user.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.proper.enterprise.isj.user.model.UserInfo;
import com.proper.enterprise.isj.user.service.UserInfoService;
import com.proper.enterprise.platform.api.auth.model.User;
import com.proper.enterprise.platform.api.auth.service.UserService;
import com.proper.enterprise.platform.auth.common.entity.UserEntity;
import com.proper.enterprise.platform.test.AbstractTest;

/**
 * Created by think on 2016/8/15 0015.
 *
 */
public class UserSaveTest extends AbstractTest {

	@Autowired
	UserService userServiceTestImpl;

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	UserInfoService userInfoService;

	@Test
	public void saveUser() {
		// mockUser();
		for (int i = 0; i < 100; i++) {
			UserEntity user = new UserEntity();
			if (i < 10) {
				user.setUsername("ces0" + i);
			} else {
				user.setUsername("ces" + i);
			}
			user.setPassword("123456");
			user.setEmail(user.getUsername() + "@qq.com");
			user.setSuperuser(false);

			User us = userServiceTestImpl.save(user);
			UserInfo info = new UserInfo();
			info.setUserId(us.getId());
			if (i < 10) {
				info.setName("测试0" + i);
			} else {
				info.setName("测试" + i);
			}
			info.setTelephone("13800000000");
			info.setIdentityCard("210");
			userInfoService.saveOrUpdateUserInfo(info);
		}
		// User user = userService.getByUsername("ces01");
		// System.out.println(user.getId());
	}

}
