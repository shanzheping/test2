package com.proper.enterprise.isj.user.model.enums;

import java.util.EnumSet;

/**
 * Created by think on 2016/8/13 0013.
 * ֤证件类型
 */
public enum CredentialsEnum {
    /**
     * 证件类型֤
     */
    MILITARY_CARD("军人证֤");

    private String value;


    private CredentialsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static void main(String[] args) {
        EnumSet<CredentialsEnum> collSet = EnumSet.allOf(CredentialsEnum.class);
        for (CredentialsEnum coll : collSet) {
            System.out.println(coll.name());
        }
        CredentialsEnum[] vals = CredentialsEnum.values();
        for (int i = 0; i < vals.length; i++) {
            System.out.println(vals[i].getValue());
        }
    }
}
