package com.ssafy.trip.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trippackage")
public class TripPackage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	private Long userNum;
	private String name;

	public TripPackage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TripPackage(Long userNum, String name) {
		super();
		this.userNum = userNum;
		this.name = name;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Long getUserNum() {
		return userNum;
	}

	public void setUserNum(Long userNum) {
		this.userNum = userNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TripPackage [num=" + num + ", userNum=" + userNum + ", name=" + name + "]";
	}

}
