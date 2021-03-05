package com.ssafy.trip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bloginfo")
public class BlogInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	@Column(name="user_num")
	private Long usernum;
	private String title;
	@Column(name="title_img")
	private String titleimg;

	@Column(name="visit_count")
	private int visitcount;
	
	public BlogInfo() {
	}
	public BlogInfo(Long num, Long usernum, String title, String titleimg, int visitcount ) {
		this.num = num;
		this.usernum = usernum;
		this.title = title;
		this.titleimg = titleimg;
		this.visitcount = visitcount;
	}
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Long getUsernum() {
		return usernum;
	}

	public void setUsernum(Long usernum) {
		this.usernum = usernum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleimg() {
		return titleimg;
	}

	public void setTitleimg(String titleimg) {
		this.titleimg = titleimg;
	}

	public int getVisitcount() {
		return visitcount;
	}

	public void setVisitcount(int visitcount) {
		this.visitcount = visitcount;
	}
	@Override
	public String toString() {
		return "BlogInfo [num=" + num + ", usernum=" + usernum + ", title=" + title + ", titleimg=" + titleimg
				+ ", visitcount=" + visitcount + "]";
	}
	
	
}
