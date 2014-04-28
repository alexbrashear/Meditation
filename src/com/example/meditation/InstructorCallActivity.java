package com.example.meditation;

import android.os.Bundle;

public class InstructorCallActivity extends InstructorActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		endOfCall = false;
		setContentView(R.layout.activity_instructor);
		super.onCreate(savedInstanceState);
	}

}
