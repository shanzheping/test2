package com.proper.enterprise.isj.proxy.model;

import java.util.ArrayList;
import java.util.List;

import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/16 0016.
 */
public class HospitalNavigation extends BaseDocument{

    /**
     * 院区ID
     */
    private String id;
    /**
     * 院区名称
     */
    private String name;
    /**
     * 楼列表
     */
    private List<Build> buiders = new ArrayList<Build>();

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

    public List<Build> getBuiders() {
        return buiders;
    }

    public void setBuiders(List<Build> buiders) {
        this.buiders = buiders;
    }
}
