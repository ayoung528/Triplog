package com.ssafy.trip.help;

import java.time.Instant;
/*
 UserProfile
{id:"sdfs",username:"sdfsdfsdf", name:"ddssffsdfsffsdfs"}
 */
public class UserProfile {
    private Long num;
    private String email;
    private String nickname;
    private String name;
    private Instant joinedAt;
    private String imagesrc;
    private String intro;
    private boolean valid;
        
	public UserProfile(Long num, String email, String nickname, String name, Instant joinedAt, String imagesrc,
			String intro, boolean valid) {
		super();
		this.num = num;
		this.email = email;
		this.nickname = nickname;
		this.name = name;
		this.joinedAt = joinedAt;
		this.imagesrc = imagesrc;
		this.intro = intro;
		this.valid = valid;
	}
	
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Instant getJoinedAt() {
		return joinedAt;
	}
	public void setJoinedAt(Instant joinedAt) {
		this.joinedAt = joinedAt;
	}
	public String getImagesrc() {
		return imagesrc;
	}
	public void setImagesrc(String imagesrc) {
		this.imagesrc = imagesrc;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
    
    
}
