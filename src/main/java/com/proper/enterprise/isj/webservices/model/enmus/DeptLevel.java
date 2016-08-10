package com.proper.enterprise.isj.webservices.model.enmus;

import com.proper.enterprise.platform.core.enums.IntEnum;

/**
 * 科室等级
 */
public enum DeptLevel implements IntEnum {

    /**
     * 其他
     */
    OTHER(0),

    /**
     * 一级
     */
    ONE(1),

    /**
     * 二级
     */
    TWO(2),

    /**
     * 三级
     */
    THREE(3);

    private int code;

    DeptLevel(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    public static DeptLevel codeOf(int code) {
        for (DeptLevel level : values()) {
            if (level.getCode() == code) {
                return level;
            }
        }
        return null;
    }
}
