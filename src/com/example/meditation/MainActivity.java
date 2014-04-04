package com.example.meditation;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
		
	}
	
	public void loginAttemptButton() {
		ParseUser.logInInBackground("Jerry", "showmethemoney", new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			      // Hooray! The user is logged in.
			    } else {
			      // Signup failed. Look at the ParseException to see what happened.
			    }
			  }
			});
	}

}
