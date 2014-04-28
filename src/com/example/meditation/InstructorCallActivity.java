package com.example.meditation;

import android.os.Bundle;

public class InstructorCallActivity extends InstructorActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_instructor);
		endOfCall = false;
		super.onCreate(savedInstanceState);
	}

}
