package com.proper.enterprise.isj.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.proper.enterprise.isj.user.model.UserInfo;
import com.proper.enterprise.isj.user.model.info.FamilyMemberInfo;
import com.proper.enterprise.isj.user.service.UserInfoService;
import com.proper.enterprise.platform.test.AbstractTest;

/**
 * Created by think on 2016/8/15 0015.
 *
 */
public class UserInfoControllerTest extends AbstractTest {

	@Autowired
	UserInfoService userInfoService;

	@Test
	public void testGetUserInfo() throws Exception {
		String userId = "c2c95fd0-45f5-47f5-ae47-b7014ac6514c";

		UserInfo info = new UserInfo();
		info.setUserId(userId);
		info = userInfoService.saveOrUpdateUserInfo(info);
		info = new UserInfo();
		info = (UserInfo) getAndReturn("/base/userinfo/getUserInfo?userId=" + userId, info,
				HttpStatus.OK);

		info.setTelephone("13800000001");
		List<FamilyMemberInfo> memberInfoList = new ArrayList<>();
		FamilyMemberInfo memberInfo = new FamilyMemberInfo();
		memberInfo.setId(UUID.randomUUID().toString());
		memberInfo.setIdentityCard("2110101010100");
		memberInfoList.add(memberInfo);
		info.setFamilyMemberInfo(memberInfoList);
		System.out.println(info.getName());
		System.out.println(1);
		userInfoService.saveOrUpdateUserInfo(info);

	}

}