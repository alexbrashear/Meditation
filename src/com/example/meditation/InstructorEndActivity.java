package com.example.meditation;

import android.os.Bundle;

public class InstructorEndActivity extends InstructorActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		endOfCall = true;
		setContentView(R.layout.view_results);
		super.onCreate(savedInstanceState);
	}

}
