package com.example.meditation;

import android.os.Bundle;
import android.util.Log;

public class InstructorEndActivity extends InstructorActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		endOfCall = true;
		setContentView(R.layout.view_results);
		Log.e("InstructorEnd", "is being launched");
		super.onCreate(savedInstanceState);
	}

}
