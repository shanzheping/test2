package com.proper.enterprise.isj.webservices.model.enmus;

import com.proper.enterprise.platform.core.enums.IntEnum;

public enum RegStatus implements IntEnum {

    /**
     * 停诊
     */
    STOP(0),

    /**
     * 出诊
     */
    NORMAL(1),

    /**
     * 暂未开放
     */
    CLOSED(2);

    private int code;

    RegStatus(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return 0;
    }
}
