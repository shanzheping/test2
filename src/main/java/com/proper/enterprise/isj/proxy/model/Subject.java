package com.proper.enterprise.isj.proxy.model;

import java.util.ArrayList;
import java.util.List;

import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/16 0016. 学科
 */
public class Subject extends BaseDocument {

	/**
	 * 学科Id
	 */
	private String id;

	/**
	 * 学科名称
	 */
	private String name;

	/**
	 * 下级学科
	 */
	private List<Subject> subjects = new ArrayList<>();

	/**
	 * 是否有下级
	 */
	private boolean hasChild;

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

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
}
