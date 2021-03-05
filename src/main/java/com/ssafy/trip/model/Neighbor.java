package com.ssafy.trip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "neighbor")
public class Neighbor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	@Column(name = "user_num")
	private Long userNum;
	@Column(name = "neighbor_num")
	private Long neighborNum;
	@Column(name = "neighbor_nickname")
	private String neighborNickname;

	public Neighbor() {
		super();
	}

	public Neighbor(Long num, Long userNum, Long neighborNum, String neighborNickname) {
		super();
		this.num = num;
		this.userNum = userNum;
		this.neighborNum = neighborNum;
		this.neighborNickname = neighborNickname;
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

	public Long getNeighborNum() {
		return neighborNum;
	}

	public void setNeighborNum(Long neighborNum) {
		this.neighborNum = neighborNum;
	}

	public String getNeighborNickname() {
		return neighborNickname;
	}

	public void setNeighborNickname(String neighborNickname) {
		this.neighborNickname = neighborNickname;
	}

	@Override
	public String toString() {
		return "Neighbor [num=" + num + ", userNum=" + userNum + ", neighborNum=" + neighborNum + ", neighborNickname="
				+ neighborNickname + "]";
	}

}
