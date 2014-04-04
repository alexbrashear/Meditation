package com.example.meditation;

import com.parse.ParseObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;

public class UserActivity extends Activity {
	
	private EditText custom_q;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
	}

	public void addKeyListener() {
		custom_q = (EditText) findViewById(R.id.custom_q);
		custom_q.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && 
						keyCode == KeyEvent.KEYCODE_ENTER) {
					//we should push this question (custom_q.getText()) onto the server
					//now it's just a meaningless toast
					ParseObject question = new ParseObject("Question");
					question.put("text", custom_q.getText());
					question.saveInBackground();
					Toast.makeText(UserActivity.this, custom_q.getText(), Toast.LENGTH_LONG).show();
					return true;
				}
				return false;
			}
			
		});
		
	}
	
}
