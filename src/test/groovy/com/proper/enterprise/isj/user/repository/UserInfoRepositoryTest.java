package com.proper.enterprise.isj.user.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.proper.enterprise.isj.user.model.UserInfo;
import com.proper.enterprise.isj.user.model.enums.CardTypeEnum;
import com.proper.enterprise.isj.user.model.info.CardInfo;
import com.proper.enterprise.platform.test.AbstractTest;
import com.proper.enterprise.platform.test.utils.JSONUtil;

/**
 * Created by think on 2016/8/12 0012.
 *
 */
public class UserInfoRepositoryTest extends AbstractTest {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Test
	public void saveUser() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(String.valueOf(1));
		userInfo.setName("测试1");
		userInfo.setIdentityCard("11111");
		List<CardInfo> cardList = new ArrayList<>();
		CardInfo cardInfo = new CardInfo();
		cardInfo.setCardType(CardTypeEnum.CASE_NO);
		cardInfo.setCardNo("11111111111");
		cardList.add(cardInfo);
		userInfo.setCards(cardList);
		userInfoRepository.save(userInfo);
	}

	@Test
	public void getUserInfoByUserId() {
		UserInfo userInfo = userInfoRepository.getByUserId(String.valueOf(1));
		try {
			System.out.println(JSONUtil.toJSON(userInfo));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}