package com.proper.enterprise.isj.webservices.model.res.deptinfo;

import com.proper.enterprise.isj.webservices.model.enmus.DeptLevel;
import com.proper.enterprise.isj.webservices.model.enmus.Status;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DEPT_LIST")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dept {

    /**
     * 科室ID,HIS系统中科室唯一ID
     * 必填
     */
    @XmlElement(name = "DEPT_ID")
    private String deptId;

    /**
     * 科室名称
     * 必填
     */
    @XmlElement(name = "DEPT_NAME")
    private String deptName;

    /**
     * 上级科室ID，顶级时为-1
     * 必填
     */
    @XmlElement(name = "PARENT_ID")
    private String parentId;

    /**
     * 介绍
     */
    @XmlElement(name = "DESC")
    private String desc;

    /**
     * 科室主治
     */
    @XmlElement(name = "EXPERTISE")
    private String expertise;

    /**
     * 科室等级，详见 “科室等级”
     */
    @XmlElement(name = "LEVEL")
    private int level;

    /**
     * 科室所在位置
     */
    @XmlElement(name = "ADDRESS")
    private String address;

    /**
     * 状态，详见 “状态”
     * 必填
     */
    @XmlElement(name = "STATUS")
    private int status;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public DeptLevel getLevel() {
        return DeptLevel.codeOf(level);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Status getStatus() {
        return Status.codeOf(status);
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
