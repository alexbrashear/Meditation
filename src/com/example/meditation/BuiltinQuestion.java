package com.example.meditation;

public class BuiltinQuestion {
	String question;
	int count;
	
	public BuiltinQuestion(String question, int count) {
		this.question = question;
		this.count = count;
	}
	
	public String toString() {
		return question + " -- " + count;
	}
}
