package com.proper.enterprise.isj.proxy.model;

import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/16 0016.
 */
public class RegistrationAdd extends BaseDocument {

	/**
	 * 患者ID
	 */
	private String patientId;
	/**
	 * 患者家庭成员关系Id
	 */
	private String memberId;

	/**
	 * 是否预约
	 */
	private String isAppointment;
	/**
	 * 挂号时间", "example": "{2016-08-10 09:10|2016-08-10}
	 */
	private String registerDate;
	/**
	 * 医生ID
	 */
	private String doctorId;
	/**
	 * 挂号类别编码
	 */
	private String categoryCode;
	/**
	 * 挂号金额", "example": "7.00
	 */
	private String amount;
	/**
	 * 支付状态
	 */
	private String payStatus;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getIsAppointment() {
		return isAppointment;
	}

	public void setIsAppointment(String isAppointment) {
		this.isAppointment = isAppointment;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
}
