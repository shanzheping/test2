package com.proper.enterprise.isj.webservices.model.reginfo;

import java.util.Date;
import java.util.List;

public class Reg {

    private Date regDate;

    private String regWeekday;

    private List<RegTime> regTimeList;

    public Date getRegDate() {
        return (Date) regDate.clone();
    }

    public void setRegDate(Date regDate) {
        this.regDate = (Date) regDate.clone();
    }

    public String getRegWeekday() {
        return regWeekday;
    }

    public void setRegWeekday(String regWeekday) {
        this.regWeekday = regWeekday;
    }

    public List<RegTime> getRegTimeList() {
        return regTimeList;
    }

    public void setRegTimeList(List<RegTime> regTimeList) {
        this.regTimeList = regTimeList;
    }
}
