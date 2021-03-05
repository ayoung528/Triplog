package com.ssafy.trip.help;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class ArticleRequestWithFile {
	
	private Long num;
	private Long usernum;
	private Long trippackage_num;
	private String title;
	private String place;
	private String content;
	private String thumbnail;
	private Boolean temp;
	private Date created_at;
	private Date date_start;
	private Date date_end;
	private int likeCount;
	private MultipartFile file;
	
	public ArticleRequestWithFile(Long num, Long usernum, Long trippackage_num, String title, String place,
			String content, String thumbnail, Boolean temp, Date created_at, Date date_start, Date date_end,
			int likeCount, MultipartFile file) {
		super();
		this.num = num;
		this.usernum = usernum;
		this.trippackage_num = trippackage_num;
		this.title = title;
		this.place = place;
		this.content = content;
		this.thumbnail = thumbnail;
		this.temp = temp;
		this.created_at = created_at;
		this.date_start = date_start;
		this.date_end = date_end;
		this.likeCount = likeCount;
		this.file = file;
	}
	
	public ArticleRequestWithFile() {
		super();
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
	public Long getTrippackage_num() {
		return trippackage_num;
	}
	public void setTrippackage_num(Long trippackage_num) {
		this.trippackage_num = trippackage_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Boolean getTemp() {
		return temp;
	}
	public void setTemp(Boolean temp) {
		this.temp = temp;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getDate_start() {
		return date_start;
	}
	public void setDate_start(Date date_start) {
		this.date_start = date_start;
	}
	public Date getDate_end() {
		return date_end;
	}
	public void setDate_end(Date date_end) {
		this.date_end = date_end;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
