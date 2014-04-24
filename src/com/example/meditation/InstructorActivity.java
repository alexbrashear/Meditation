package com.example.meditation;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
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
	
	public void addQuestions(ArrayList<String> customQuestions, ArrayList<BuiltinQuestion> builtinQuestions) {
		Log.e(customQuestions.size() + "", builtinQuestions.size() + "");
		((ListView)this.findViewById(R.id.customQuestions)).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customQuestions));
		((ListView)this.findViewById(R.id.builtinQuestions)).setAdapter(new ArrayAdapter<BuiltinQuestion>(this, android.R.layout.simple_list_item_1, builtinQuestions));
	}
	
	public void setView(InstructorView aView) {
		mView = aView;
	}
	
	public TextView getTimeSince() {
		return (TextView) findViewById(R.id.time_since);
	}
	
	public void clearQuestions(View v) {
		mView.resetCutoff();
	}

}
