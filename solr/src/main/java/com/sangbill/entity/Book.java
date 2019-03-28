package com.sangbill.entity;

import org.apache.solr.client.solrj.beans.Field;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("book")
public class Book {
	@Field
	@XStreamAlias("id")
	private String id;

	@Field
	@XStreamAlias("name")
	private String name;

	@Field
	@XStreamAlias("summary")
	private String summary;

	@Field
	@XStreamAlias("content")
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}