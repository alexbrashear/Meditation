package com.example.meditation;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// The screen for the instructor, which reports the current questions.
// Allows the instructor to clear questions.
public class InstructorActivity extends Activity {
	public static Activity ia;
	private InstructorView mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ia = this;
        setContentView(R.layout.activity_instructor);
	}
	
	// Display the questions pulled from the server
	public void addQuestions(ArrayList<String> customQuestions, ArrayList<BuiltinQuestion> builtinQuestions) {
		Log.e(customQuestions.size() + "", builtinQuestions.size() + "");
		((ListView)this.findViewById(R.id.builtinQuestions)).setAdapter(new CustomAdapterBQ(this,builtinQuestions));
		((ListView)this.findViewById(R.id.customQuestions)).setAdapter(new ArrayAdapter<String>(this, R.layout.custom_list_layout, customQuestions));
		
	}
	
	// Register a view.
	public void setView(InstructorView aView) {
		mView = aView;
	}
	
	// Get the TextView that contains the elapsed refresh time.
	public TextView getTimeSince() {
		return (TextView) findViewById(R.id.time_since);
	}
	
	// Reset the timestamp on the view when the user presses the button.
	public void clearQuestions(View v) {
		mView.resetCutoff();
	}

}
