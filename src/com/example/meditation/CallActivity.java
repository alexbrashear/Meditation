package com.example.meditation;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallActivity extends Activity {
	
	// monitor phone call states
	private class PhoneCallListener extends PhoneStateListener {

		private boolean phoneCalling = false;

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
				
				// Launch the appropriate activity
				ParseUser user = ParseUser.getCurrentUser();
				if (user != null) {
					if (!user.getBoolean("Instructor")) {
						launchUser(UserActivity.class);
					} else {
						launchUser(InstructorActivity.class);
					}
				} else {
					Toast.makeText(getApplicationContext(), "Error: not logged in", Toast.LENGTH_SHORT).show();
				}
				
				phoneCalling = true;
				
			} else if (TelephonyManager.CALL_STATE_IDLE == state && phoneCalling) {
				// When the call ends launch the main activity again exiting user activities
				if (UserActivity.ua != null) {
					UserActivity.ua.finish();
				} else if (InstructorActivity.ia != null){
					InstructorActivity.ia.finish();
				}
				startActivity(new Intent(CallActivity.this, ViewResultsActivity.class));
				
				phoneCalling = false;
			}
		}
    }
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);				
		
		Button button = (Button) findViewById(R.id.button);

		// Set up the phone call listener
		PhoneCallListener phoneCallListener = new PhoneCallListener();
		TelephonyManager telManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		telManager.listen(phoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);

		// add button listener
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {				
				Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
				ParseUser user = ParseUser.getCurrentUser();
				
				// Dial the appropriate conference call number
				if (user.getBoolean("Instructor")) {
					phoneCallIntent.setData(Uri.parse("tel:8477089465"));
				} else {
					phoneCallIntent.setData(Uri.parse("tel:8477089465"));
				}
				startActivity(phoneCallIntent);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (item.getItemId() == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// Launch the activity for a user with a delay of 1 second.
	// This gives Android enough time to pull up the dialer.
	public void launchUser(Class<?> c) {
		Handler h = new Handler();
		Runnable r = new MyRunnable(this, c);
		h.postDelayed(r, 1000);
		
	}
	
	private class MyRunnable implements Runnable {
		private Activity activity;
		private Class<?> c;
		
		public MyRunnable(Activity activity, Class<?> c) {
			this.activity = activity;
			this.c = c;
		}
		public void run() {
			startActivity(new Intent(activity, c));
		}
	}
	
	public void quitApp(View v) {
		finish();
	}
	
	@Override
	public void finish(){
		ParseUser.logOut();
		super.finish();
	}

}
