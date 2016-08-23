package com.proper.enterprise.isj.user.model.info;

import com.proper.enterprise.isj.user.model.enums.CardTypeEnum;
import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/12 0012.
 */

public class CardInfo extends BaseDocument {

	private CardTypeEnum cardType;

	private String cardNo;

	public CardTypeEnum getCardType() {
		return cardType;
	}

	public void setCardType(CardTypeEnum cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}
