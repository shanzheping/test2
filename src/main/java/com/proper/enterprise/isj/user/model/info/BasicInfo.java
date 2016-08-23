package com.proper.enterprise.isj.user.model.info;

import java.util.ArrayList;
import java.util.List;

import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/12 0012. 公共信息
 */

public class BasicInfo extends BaseDocument {

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 电话
	 */
	private String telephone;

	/**
	 * 身份证号
	 */
	private String identityCard;

	/**
	 * 就诊卡
	 */

	private List<CardInfo> cards = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public List<CardInfo> getCards() {
		return cards;
	}

	public void setCards(List<CardInfo> cards) {
		this.cards = cards;
	}
}
