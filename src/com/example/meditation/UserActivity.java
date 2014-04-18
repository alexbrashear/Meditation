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
        //set pain button
        final Button pain = (Button) findViewById(R.id.pain_button);
        pain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("question: ", "I am experiencing a pain when I do this.");
				ParseObject q = new ParseObject("Question");
				q.add("text", "I am experiencing a pain when I do this.");
				q.saveInBackground();
				Toast.makeText(UserActivity.this, "Question sent", Toast.LENGTH_LONG).show();
			}
		});
        //set slow button
        final Button slow = (Button) findViewById(R.id.slow_button);
        slow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("question: ", "Can you please slow down a little?");
				ParseObject q = new ParseObject("Question");
				q.add("text", "Can you please slow down a little?");
				q.saveInBackground();
				Toast.makeText(UserActivity.this, "Question sent", Toast.LENGTH_LONG).show();
			}
		});
        //set repeat button
        final Button rep = (Button) findViewById(R.id.repeat_button);
        rep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("question: ", "We have already talked about this last week.");
				ParseObject q = new ParseObject("Question");
				q.add("text", "We have already talked about this last week.");
				q.saveInBackground();
				Toast.makeText(UserActivity.this, "Question sent", Toast.LENGTH_LONG).show();
			}
		});
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
	
	public void sendToInstructor(View view) {
		custom_q = (EditText) findViewById(R.id.custom_q);
		Log.e("question: ", custom_q.getText().toString());
		ParseObject q = new ParseObject("Question");
		q.add("text", custom_q.getText().toString());
		q.saveInBackground();
		Toast.makeText(UserActivity.this, custom_q.getText(), Toast.LENGTH_LONG).show();
		custom_q.setText("");			
	}
	
}
