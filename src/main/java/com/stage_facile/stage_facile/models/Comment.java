package com.stage_facile.stage_facile.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	private @Id @GeneratedValue Long id;
	private @ManyToOne User writer;
	private @Column(columnDefinition="TEXT") String content;
	private Date postedOn;
	private @ElementCollection(targetClass = Long.class) Set<Long> upvoters;
	private @ElementCollection(targetClass = Long.class) Set<Long> downvoters;
	
	public Comment() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getWriter() {
		return writer;
	}
	public void setWriter(User writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPostedOn() {
		return postedOn;
	}
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}
	public Set<Long> getUpvoters() {
		return upvoters;
	}
	public void setUpvoters(Set<Long> upvoters) {
		this.upvoters = upvoters;
	}
	public Set<Long> getDownvoters() {
		return downvoters;
	}
	public void setDownvoters(Set<Long> downvoters) {
		this.downvoters = downvoters;
	}
	
}
