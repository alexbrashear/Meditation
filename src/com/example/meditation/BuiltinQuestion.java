package com.example.meditation;

public class BuiltinQuestion {
	private String question;
	private int count;
	public static int totalCount;
	
	public BuiltinQuestion(String question, int count) {
		this.question = question;
		this.count = count;
	}
	
	public String toString() {
		return question + " -- " + count / (double) totalCount;
	}
}
