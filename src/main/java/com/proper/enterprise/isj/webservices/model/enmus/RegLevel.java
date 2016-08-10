package com.proper.enterprise.isj.webservices.model.enmus;

import com.proper.enterprise.platform.core.enums.IntEnum;

public enum RegLevel implements IntEnum {

    /**
     * 普通
     */
    NORMAL(1),

    /**
     * 专家
     */
    EXPERT(2),

    /**
     * 急诊
     */
    EMERGENCY(3);

    private int code;

    RegLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static RegLevel codeOf(int code) {
        for (RegLevel regLevel : values()) {
            if (regLevel.getCode() == code) {
                return regLevel;
            }
        }
        return null;
    }
}
