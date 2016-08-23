package com.proper.enterprise.isj.proxy.model;

import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/16 0016. 登录患者信息
 */
public class Patient extends BaseDocument {

	/**
	 * 患者ID
	 */
	private String id;
	/**
	 * 患者姓名
	 */
	private String name;

	/**
	 * 性别编码
	 */
	private String sexCode;
	/**
	 * 性别名称
	 */
	private String sex;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 电话号码
	 */
	private String phone;
	/**
	 * 关系代码
	 */
	private String memberCode;

	/**
	 * 关系名称
	 */
	private String member;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSexCode() {
		return sexCode;
	}

	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}
}
