package com.example.meditation;

public class BuiltinQuestion {
	private String question;
	private int count;
	public static int totalCount;
	
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
	
	public String toString() {
//		return question + " -- " + count / (double) totalCount;

		return question;
		/*for (int i = 0; i < count; i++) {
			s = s + "+";
		}
		return s;*/

	}
}
