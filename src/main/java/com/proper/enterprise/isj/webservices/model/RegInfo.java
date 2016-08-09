package com.proper.enterprise.isj.webservices.model;

import com.proper.enterprise.isj.webservices.model.reginfo.RegDoctor;

import java.util.List;

public class RegInfo {

    private String hosId;

    private String deptId;

    private List<RegDoctor> regDoctorList;

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public List<RegDoctor> getRegDoctorList() {
        return regDoctorList;
    }

    public void setRegDoctorList(List<RegDoctor> regDoctorList) {
        this.regDoctorList = regDoctorList;
    }
}
