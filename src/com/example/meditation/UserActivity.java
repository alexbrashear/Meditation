package com.example.meditation;

import com.parse.ParsePush;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
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

	public void addKeyListener() {
		custom_q = (EditText) findViewById(R.id.custom_q);
		custom_q.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && 
						keyCode == KeyEvent.KEYCODE_ENTER) {
					ParsePush push = new ParsePush();
					push.setChannel("Question");
					push.setMessage(custom_q.getText().toString());
					push.sendInBackground();
					Toast.makeText(UserActivity.this, custom_q.getText(), Toast.LENGTH_LONG).show();
					return true;
				}
				return false;
			}
			
		});
		
	}
	
}
