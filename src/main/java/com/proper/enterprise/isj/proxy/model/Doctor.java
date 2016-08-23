package com.proper.enterprise.isj.proxy.model;

import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/16 0016. 医生列表
 */
public class Doctor extends BaseDocument {

	/**
	 * id
	 */
	private String id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 性别编码
	 */
	private String sexCode;

	/**
	 * 职称名称
	 */
	private String title;

	/**
	 * 院区Id
	 */
	private String districtId;

	/**
	 * 院区名称
	 */
	private String district;

	/**
	 * 科室Id
	 */
	private String deptId;

	/**
	 * 科室名称
	 */
	private String dept;

	/**
	 * 擅长
	 */
	private String skill;

	/**
	 * 简介
	 */
	private String summary;

	/**
	 *
	 */
	private String hospital;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
}
