package com.ssafy.trip.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;

	@Column(name="user_num")
	private Long usernum;

	@Column(name="article_num")
	private Long articlenum;
	
	private String content;

	@Column(name="user_img")
	private String userimg;
	
	private String paragraph;
	
	public String getParagraph() {
		return paragraph;
	}
	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}
	public String getUserimg() {
		return userimg;
	}
	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}
	public void setUsernickname(String usernickname) {
		this.usernickname = usernickname;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reply_num")
	private Comment reply;
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
	public Long getArticlenum() {
		return articlenum;
	}
	public void setArticlenum(Long articlenum) {
		this.articlenum = articlenum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Comment getReply() {
		return reply;
	}
	public void setReply(Comment reply) {
		this.reply = reply;
	}
	public String getUsernickname() {
		return usernickname;
	}
	public void setUsernicknam(String usernickname) {
		this.usernickname = usernickname;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public Date getCreatedat() {
		return createdat;
	}
	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	@Column(name="user_nickname")
	private String usernickname;

	@Column(name="user_email")
	private String useremail;

	@Column(name="created_at")
	private Date createdat;
}
