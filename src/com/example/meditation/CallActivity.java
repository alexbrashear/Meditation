package com.example.meditation;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.PushService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallActivity extends Activity {

	private AudioManager myAudioManager;
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
					launchUser();
					phoneCalling = true;
					
				}

				// When the call ends launch the main activity again
				if (TelephonyManager.CALL_STATE_IDLE == state) {

					Log.i(TAG, "IDLE");

					if (phoneCalling) {

						Log.i(TAG, "restart app");

						startActivity(new Intent(self, ViewResultsActivity.class));

						phoneCalling = false;
					}

				}
			}
			
			public boolean getPhoneStatus(){
				return phoneCalling;
			}
		}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		self = this;
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "E6UyOjr7jeVe9BJCO1WdCU96os1j5vHw8YnSFUXG", "V5ySNKCxjCwY8C24agPPmA23p65QRIceRc8GKlho");
				
		
		Button button = (Button) findViewById(R.id.button);

		PhoneCallListener phoneCallListener = new PhoneCallListener();
		TelephonyManager telManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		telManager.listen(phoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);

		// add button listener
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// find out which radio option was selected
				RadioGroup group = (RadioGroup) findViewById(R.id.radioUser);
				int selectedId = group.getCheckedRadioButtonId();
				
				boolean participant = selectedId == 0;
				if (!participant) {
					PushService.subscribe(getBaseContext(), "Questions", ViewResultsActivity.class);
				}
				
				// make call and mute participant
				myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
				Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
				phoneCallIntent.setData(Uri.parse("tel:3015021117"));
				//myAudioManager.setMode(AudioManager.MODE_IN_CALL); 
				//myAudioManager.setMicrophoneMute(participant);
				//myAudioManager.setMicrophoneMute(false);
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
	
	public void launchUser() {
		Handler h = new Handler();
		Runnable r = new MyRunnable(this);
		h.postDelayed(r, 5000);
		
	}
	private class MyRunnable implements Runnable {
		private Activity activity;
		
		public MyRunnable(Activity activity) {
			this.activity = activity;
		}
		public void run() {
			startActivity(new Intent(activity, UserActivity.class));
		}
	}

}
