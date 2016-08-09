package com.proper.enterprise.isj.webservices.model.reginfo;

import java.util.List;

public class RegDoctor {

    private String doctorId;

    private String name;

    private String jobTitle;

    private List<Reg> regList;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public List<Reg> getRegList() {
        return regList;
    }

    public void setRegList(List<Reg> regList) {
        this.regList = regList;
    }
}
