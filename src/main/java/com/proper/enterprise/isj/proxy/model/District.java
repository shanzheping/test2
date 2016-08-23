package com.proper.enterprise.isj.proxy.model;

import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/16 0016.
 * 院区
 */
public class District  extends BaseDocument {

    /**
     * 院区ID
     */
    private String id;
    /**
     * 院区名称
     */
    private String name;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
