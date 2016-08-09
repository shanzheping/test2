package com.proper.enterprise.isj.webservices.model.reginfo;

import com.proper.enterprise.isj.webservices.model.enmus.RegLevel;
import com.proper.enterprise.isj.webservices.model.enmus.RegStatus;
import com.proper.enterprise.isj.webservices.model.enmus.TimeFlag;
import com.proper.enterprise.platform.core.enums.WhetherType;

public class RegTime {

    private String regId;

    private TimeFlag timeFlag;

    private RegStatus regStatus;

    private int total = 99;

    private int overCount = 99;

    private RegLevel regLevel;

    private long regFee;

    private long treatFee;

    private WhetherType isTime;

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public TimeFlag getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(TimeFlag timeFlag) {
        this.timeFlag = timeFlag;
    }

    public RegStatus getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(RegStatus regStatus) {
        this.regStatus = regStatus;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOverCount() {
        return overCount;
    }

    public void setOverCount(int overCount) {
        this.overCount = overCount;
    }

    public RegLevel getRegLevel() {
        return regLevel;
    }

    public void setRegLevel(RegLevel regLevel) {
        this.regLevel = regLevel;
    }

    public long getRegFee() {
        return regFee;
    }

    public void setRegFee(long regFee) {
        this.regFee = regFee;
    }

    public long getTreatFee() {
        return treatFee;
    }

    public void setTreatFee(long treatFee) {
        this.treatFee = treatFee;
    }

    public WhetherType getIsTime() {
        return isTime;
    }

    public void setIsTime(WhetherType isTime) {
        this.isTime = isTime;
    }
}
