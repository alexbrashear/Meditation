package com.example.meditation;

public class BuiltinQuestion {
	private String question;
	private int count;
	
	public BuiltinQuestion(String question, int count) {
		this.question = question;
		this.count = count;
	}
	
	public String getCounts() {
		String s = "";
		for (int i = 0; i < count; i++) {
			s += "+";
		}
		return s;
	}
	
	public String toString()
	{
		return question;
	}
}
