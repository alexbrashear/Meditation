package com.example.meditation;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button   mButton;
	EditText username;
	EditText password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        mButton = (Button)findViewById(R.id.submitbutton);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        
//        ParseUser user = new ParseUser();
//        user.setUsername("test");
//        user.setPassword("g");
//        user.setEmail("email@example.com");
//        user.put("Instructor", true);
//        user.signUpInBackground(null);
        
        mButton.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                String usernametxt = username.getText().toString();
                String passwordtxt = password.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(
                                            MainActivity.this,
                                            CallActivity.class);
                                    startActivity(intent);
                                    String text =  user.getBoolean("Instructor") ? "instructor" : "participant";
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully logged in as " + text,
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "No such user exists, please signup",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });	
        
        Parse.initialize(this, "E6UyOjr7jeVe9BJCO1WdCU96os1j5vHw8YnSFUXG", "V5ySNKCxjCwY8C24agPPmA23p65QRIceRc8GKlho");
	}

}
