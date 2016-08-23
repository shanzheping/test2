package com.proper.enterprise.isj.proxy.model;

import com.proper.enterprise.platform.core.document.BaseDocument;

/**
 * Created by think on 2016/8/15 0015.
 */
public class Messages extends BaseDocument {

	/**
	 * 消息ID
	 */
	private String id;
	/**
	 * 消息时间,格式:yyyy-MM-dd hh24:mi
	 */
	private String date;
	/**
	 * 消息内容
	 */
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
