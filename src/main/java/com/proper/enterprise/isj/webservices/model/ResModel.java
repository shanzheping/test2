package com.proper.enterprise.isj.webservices.model;

import com.proper.enterprise.isj.webservices.model.enmus.ReturnCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ROOT")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResModel {

    @XmlElement(name = "RETURN_CODE")
    private int returnCode;

    @XmlElement(name = "RETURN_MSG")
    private String returnMsg;

    @XmlElement(name = "SIGN_TYPE")
    private String signType;

    @XmlElement(name = "SIGN")
    private String sign;

    @XmlElement(name = "RES_ENCRYPTED")
    private String resEncrypted;

    public ReturnCode getReturnCode() {
        return ReturnCode.codeOf(returnCode);
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResEncrypted() {
        return resEncrypted;
    }

    public void setResEncrypted(String resEncrypted) {
        this.resEncrypted = resEncrypted;
    }
}
