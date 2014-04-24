package com.example.meditation;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// The first screen the user sees - the login screen.
public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        // Grab the username/password
        final EditText username = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        
        // Handle the login click
        ((Button) findViewById(R.id.submitbutton)).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Send data to Parse.com for verification
                ParseUser.logInInBackground(username.getText().toString(),
                		                    password.getText().toString(),
                    new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
                                // If user exist and authenticated, send user to CallActivity
                                Intent intent = new Intent(
                                        MainActivity.this,
                                        CallActivity.class);
                                startActivity(intent);
                                String text = user.getBoolean("Instructor") ? 
                                		"instructor" : "participant";
                                Toast.makeText(getApplicationContext(),
                                        "Successfully logged in as " + text,
                                        Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "No such user exists, please sign up.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
        });	
        
        // Open a connection to Parse
        Parse.initialize(this, "E6UyOjr7jeVe9BJCO1WdCU96os1j5vHw8YnSFUXG", 
        		"V5ySNKCxjCwY8C24agPPmA23p65QRIceRc8GKlho");
	}

}
