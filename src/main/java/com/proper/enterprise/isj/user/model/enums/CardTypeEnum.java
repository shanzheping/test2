package com.proper.enterprise.isj.user.model.enums;

import java.util.EnumSet;

/**
 * Created by think on 2016/8/12 0012.
 *
 */
public enum CardTypeEnum {

    /**
     *病历号
     */
    CASE_NO("病历号");


    private String value;


    private CardTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static void main(String[] args) {
        EnumSet<CardTypeEnum> collSet = EnumSet.allOf(CardTypeEnum.class);
        for (CardTypeEnum coll : collSet) {
            System.out.println(coll.name());
        }
        CardTypeEnum[] vals = CardTypeEnum.values();
        for (int i = 0; i < vals.length; i++) {
            System.out.println(vals[i].getValue());
        }
    }
}
