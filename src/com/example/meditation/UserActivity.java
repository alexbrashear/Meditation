package com.example.meditation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class UserActivity extends Activity {
	
	private EditText custom_q;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
	}

}
