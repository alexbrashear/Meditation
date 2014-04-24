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

	private Activity self;
	
	// monitor phone call states
	private class PhoneCallListener extends PhoneStateListener {

			String TAG = "LOGGING PHONE CALL";

			private boolean phoneCalling = false;

			@Override
			public void onCallStateChanged(int state, String incomingNumber) {

				if (TelephonyManager.CALL_STATE_RINGING == state) {
					// phone ringing
					Log.i(TAG, "RINGING, number: " + incomingNumber);
				}

				if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
					// active
					Log.i(TAG, "OFFHOOK");
					ParseUser user = ParseUser.getCurrentUser();
					if (user != null) {
						if (!user.getBoolean("Instructor")) {
							launchUser(UserActivity.class);
						} else {
							launchUser(InstructorActivity.class);
						}
					} else {
						Toast toast = Toast.makeText(getApplicationContext(), "Error: not logged in", Toast.LENGTH_SHORT);
				    	toast.show();
					}
					
					phoneCalling = true;
					
				}

				// When the call ends launch the main activity again
				if (TelephonyManager.CALL_STATE_IDLE == state) {

					Log.i(TAG, "IDLE");

					if (phoneCalling) {

						Log.i(TAG, "restart app");
						//exiting user activities
						if (UserActivity.ua != null) {
							UserActivity.ua.finish();
						} else if (InstructorActivity.ia != null){
							InstructorActivity.ia.finish();
						}
						startActivity(new Intent(self, ViewResultsActivity.class));
						
						phoneCalling = false;
					}

				}
			}
			
		}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		self = this;
		setContentView(R.layout.activity_main);				
		
		Button button = (Button) findViewById(R.id.button);

		PhoneCallListener phoneCallListener = new PhoneCallListener();
		TelephonyManager telManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		telManager.listen(phoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);

		// add button listener
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {				
				Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
				ParseUser user = ParseUser.getCurrentUser();
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
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
