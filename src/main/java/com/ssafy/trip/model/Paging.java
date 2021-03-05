package com.ssafy.trip.model;

public class Paging {
	private Long usernum;
	private int limit;

	public Paging() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Paging(Long usernum, int limit) {
		super();
		this.usernum = usernum;
		this.limit = limit;
	}

	public Long getUsernum() {
		return usernum;
	}

	public void setUsernum(Long usernum) {
		this.usernum = usernum;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "Paging [usernum=" + usernum + ", limit=" + limit + "]";
	}

}
