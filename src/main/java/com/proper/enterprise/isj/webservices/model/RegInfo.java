package com.proper.enterprise.isj.webservices.model;

import com.proper.enterprise.isj.webservices.model.reginfo.RegDoctor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "RES")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegInfo {

    @XmlElement(name = "HOS_ID")
    private String hosId;

    @XmlElement(name = "DEPT_ID")
    private String deptId;

    @XmlElement(name = "REG_DOCTOR_LIST")
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
