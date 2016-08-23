package com.proper.enterprise.isj.user.model.enums;

import java.util.EnumSet;

/**
 * Created by think on 2016/8/12 0012.
 * 家庭成员关系
 */
public enum MemberRelationEnum {
    /**
     * 父亲
     */
    FATHER("父亲"),
    /**
     * 母亲
     */
    MOTHER("母亲"),
    /**
     * 配偶
     */
    MATE("配偶"),

    /**
     * 儿子
     */
    SON("儿子"),

    /**
     * 女儿
     */
    DAUGHTER("女儿"),
    /**
     * 兄弟
     */
    BROTHER("兄弟"),
    /**
     * 姐妹
     */
    SISTER("姐妹"),

    /**
     * 亲属
     */
    KIN("亲属"),
    /**
     * 朋友
     */
    FRIEND("朋友"),
    /**
     * 其他
     */
    OTHER("其他");


    private String value;


    private MemberRelationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static void main(String[] args) {
        EnumSet<MemberRelationEnum> collSet = EnumSet.allOf(MemberRelationEnum.class);
        for (MemberRelationEnum coll : collSet) {
            System.out.println(coll.name());
        }
        MemberRelationEnum[] vals = MemberRelationEnum.values();
        for (int i = 0; i < vals.length; i++) {
            System.out.println(vals[i].getValue());
        }
    }
}
