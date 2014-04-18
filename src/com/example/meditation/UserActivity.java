package com.example.meditation;

import com.parse.ParseObject;
import com.parse.ParsePush;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
		custom_q = (EditText) findViewById(R.id.custom_q);
		if (event.getAction() == KeyEvent.ACTION_DOWN && 
				event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
			Log.e("question: ", custom_q.getText().toString());
			ParseObject q = new ParseObject("Question");
			q.add("text", custom_q.getText().toString());
			q.saveInBackground();
			Toast.makeText(UserActivity.this, custom_q.getText(), Toast.LENGTH_LONG).show();
			custom_q.setText("");
		}
		
		return super.dispatchKeyEvent(event);
	}
	
	public void sendToInstructor (View v) {
		Button b = (Button) v;
		Log.e("question: ", b.getText().toString());
		ParseObject q = new ParseObject("Question");
		q.add("text", b.getText().toString());
		q.saveInBackground();
		Toast.makeText(UserActivity.this, "Question sent", Toast.LENGTH_LONG).show();
	}
	
	public void sendCustomQToInstructor (View view) {
		custom_q = (EditText) findViewById(R.id.custom_q);
		Log.e("question: ", custom_q.getText().toString());
		ParseObject q = new ParseObject("Question");
		q.add("text", custom_q.getText().toString());
		q.saveInBackground();
		Toast.makeText(UserActivity.this, custom_q.getText(), Toast.LENGTH_LONG).show();
		custom_q.setText("");			
	}
	
}
