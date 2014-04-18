package com.example.meditation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class InstructorActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
	}
	
	public TextView getQuestions() {
		return (TextView) findViewById(R.id.questions);
	}

}
