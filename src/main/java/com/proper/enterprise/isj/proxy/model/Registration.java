package com.proper.enterprise.isj.proxy.model;

import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/16 0016.
 * 挂号单
 */
public class Registration extends BaseDocument {

    /**
     * 挂号单ID
     */
    private String id;
    /**
     * 挂号单号
     */
    private String num;
    /**
     * 患者姓名
     */
    private String patientName;
    /**
     * 预约时间", "example": "2016年07月20日 10:00
     */
    private String apptDate;
    /**
     * 门诊号
     */
    private String clinicNum;
    /**
     * 医院名称
     */
    private String hospital;
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
     * 医生Id
     */
    private String doctorId;
    /**
     * 医生名称
     */
    private String doctor;
    /**
     * 挂号金额", "example": "7.00
     */
    private String amount;
    /**
     * 挂号时间
     */
    private String registerDate;
    /**
     * 挂号单状态
     */
    private String statusCode;
    /**
     * 状态名称
     */
    private String status;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getApptDate() {
        return apptDate;
    }

    public void setApptDate(String apptDate) {
        this.apptDate = apptDate;
    }

    public String getClinicNum() {
        return clinicNum;
    }

    public void setClinicNum(String clinicNum) {
        this.clinicNum = clinicNum;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
