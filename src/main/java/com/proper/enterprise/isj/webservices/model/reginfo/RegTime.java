package com.proper.enterprise.isj.webservices.model.reginfo;

import com.proper.enterprise.isj.webservices.model.enmus.RegLevel;
import com.proper.enterprise.isj.webservices.model.enmus.RegStatus;
import com.proper.enterprise.isj.webservices.model.enmus.TimeFlag;
import com.proper.enterprise.platform.core.enums.WhetherType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "REG_TIME_LIST")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegTime {

    @XmlElement(name = "REG_ID")
    private String regId;

    @XmlElement(name = "TIME_FLAG")
    private int timeFlag;

    @XmlElement(name = "REG_STATUS")
    private int regStatus;

    @XmlElement(name = "TOTAL")
    private int total = 99;

    @XmlElement(name = "OVER_COUNT")
    private int overCount = 99;

    @XmlElement(name = "REG_LEVEL")
    private int regLevel;

    @XmlElement(name = "REG_FEE")
    private long regFee;

    @XmlElement(name = "TREAT_FEE")
    private long treatFee;

    @XmlElement(name = "ISTIME")
    private int isTime;

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public TimeFlag getTimeFlag() {
        return TimeFlag.valueOf(timeFlag + "");
    }

    public void setTimeFlag(int timeFlag) {
        this.timeFlag = timeFlag;
    }

    public RegStatus getRegStatus() {
        return RegStatus.valueOf(regStatus + "");
    }

    public void setRegStatus(int regStatus) {
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
        return RegLevel.valueOf(regLevel + "");
    }

    public void setRegLevel(int regLevel) {
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
        return WhetherType.valueOf(isTime + "");
    }

    public void setIsTime(int isTime) {
        this.isTime = isTime;
    }
}
