package com.example.meditation;

import com.parse.ParseObject;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// The screen for the user, which allows him to submit questions.
public class UserActivity extends Activity {
	public static Activity ua;
	private EditText custom_q;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ua = this;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// If user presses enter, he must be in the custom question.  Submit it!
		if (event.getAction() == KeyEvent.ACTION_DOWN && 
				event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
			sendCustomQToInstructor(null);
		}
		
		return super.dispatchKeyEvent(event);
	}
	
	// Handle all the builtin questions.
	public void sendToInstructor(View v) {
		Button b = (Button) v;
		sendQuestion(b.getText().toString(), false);
		Toast.makeText(UserActivity.this, "Question sent", Toast.LENGTH_LONG).show();
	}
	
	// Send a custom question to the instructor.
	public void sendCustomQToInstructor(View view) {
		custom_q = (EditText) findViewById(R.id.custom_q);
		sendQuestion(custom_q.getText().toString(), true);
		Toast.makeText(UserActivity.this, custom_q.getText(), Toast.LENGTH_LONG).show();
		custom_q.setText("");			
	}
	
	// Helper to send a question.
	public void sendQuestion(String text, Boolean custom) {
		ParseObject q = new ParseObject("Question");
		q.put("custom", custom);
		q.put("text", text);
		q.saveInBackground();
	}	
}
