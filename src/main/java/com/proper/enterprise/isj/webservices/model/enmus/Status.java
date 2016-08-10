package com.proper.enterprise.isj.webservices.model.enmus;

import com.proper.enterprise.platform.core.enums.IntEnum;

/**
 * 状态
 */
public enum Status implements IntEnum {

    /**
     * 正常
     */
    NORMAL(1),

    /**
     * 注销
     */
    CANCEL(2);

    private int code;

    Status(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    public static Status codeOf(int code) {
        for (Status status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
