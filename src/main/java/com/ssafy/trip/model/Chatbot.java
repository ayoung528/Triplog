package com.ssafy.trip.model;

public class Chatbot {
	private String text;
	public Chatbot() {
		
	}
	public Chatbot(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
