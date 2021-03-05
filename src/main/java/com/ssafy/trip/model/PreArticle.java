package com.ssafy.trip.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name="pre_article")
public class PreArticle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	@Column(name="user_num")
	private Long usernum;
	private Date date;
	private String comment;
	private String place;
	private String media;
	
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public Long getUsernum() {
		return usernum;
	}
	public void setUsernum(Long user_num) {
		this.usernum = user_num;
	}
	@Override
	public String toString() {
		return "PreArticle [num=" + num + ", usernum=" + usernum + ", date=" + date + ", comment=" + comment
				+ ", place=" + place + ", media=" + media + "]";
	}
	
	
}
