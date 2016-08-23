package com.proper.enterprise.isj.proxy.model;

import java.util.List;

import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/16 0016.
 */
public class Floor extends BaseDocument {
    /**
     * 楼层Id
     */
    private String id;
    /**
     * 楼层名称
     */
    private String name;
    /**
     * 层内科室
     */
    private List<String> depts;

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

    public List<String> getDepts() {
        return depts;
    }

    public void setDepts(List<String> depts) {
        this.depts = depts;
    }
}
