package com.proper.enterprise.isj.proxy.model;

import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/16 0016. 医生排班时间列表（只返回有号日期）
 */
public class Schedule extends BaseDocument {
	/**
	 * 排班时间,example:yyyy-MM-dd
	 */
	private String date;

	/**
	 * 可挂号总数
	 */
	private Integer registerNumTotal = 0;

	/**
	 * 已挂号数
	 */
	private Integer registerNum = 0;

	/**
	 * 类别编码
	 */
	private String categoryCode;

	/**
	 * 类别名称
	 */
	private String category;

	/**
	 * 挂号金额,example:7.00
	 */
	private String amount;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getRegisterNumTotal() {
		return registerNumTotal;
	}

	public void setRegisterNumTotal(Integer registerNumTotal) {
		this.registerNumTotal = registerNumTotal;
	}

	public Integer getRegisterNum() {
		return registerNum;
	}

	public void setRegisterNum(Integer registerNum) {
		this.registerNum = registerNum;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
