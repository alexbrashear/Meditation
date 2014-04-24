package com.example.meditation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InstructorActivity extends Activity {
	public static Activity ia;
	private InstructorView mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ia = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
	}
	
	public TextView getQuestions() {
		return (TextView) findViewById(R.id.questions);
	}
	
	public void setView(InstructorView aView) {
		mView = aView;
	}
	
	public void clearQuestions(View v) {
		mView.resetCutoff();
	}

}
