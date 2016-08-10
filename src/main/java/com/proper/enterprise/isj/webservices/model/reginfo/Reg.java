package com.proper.enterprise.isj.webservices.model.reginfo;

import com.proper.enterprise.platform.core.utils.DateUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "REG_LIST")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reg {

    @XmlElement(name = "REG_DATE")
    private String regDate;

    @XmlElement(name = "REG_WEEKDAY")
    private String regWeekday;

    @XmlElement(name = "REG_TIME_LIST")
    private List<RegTime> regTimeList;

    public Date getRegDate() {
        return DateUtil.toDate(regDate);
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
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
