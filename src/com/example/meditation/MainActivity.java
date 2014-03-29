package com.example.meditation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MainActivity extends Activity {

	private AudioManager myAudioManager;
	
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

					phoneCalling = true;
				}

				// When the call ends launch the main activity again
				if (TelephonyManager.CALL_STATE_IDLE == state) {

					Log.i(TAG, "IDLE");

					if (phoneCalling) {

						Log.i(TAG, "restart app");

						// restart app
						Intent i = getBaseContext().getPackageManager()
								.getLaunchIntentForPackage(
										getBaseContext().getPackageName());

						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);

						phoneCalling = false;
					}

				}
			}
		}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		
		Button button = (Button) findViewById(R.id.button);

		PhoneCallListener phoneCallListener = new PhoneCallListener();
		TelephonyManager telManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		telManager.listen(phoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);

		// add button listener
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				// find out which radio option was selected
				boolean mute = false;
				RadioGroup group = (RadioGroup) findViewById(R.id.radioUser);
				int selectedId = group.getCheckedRadioButtonId();
				if (selectedId == 0) mute = true;
				
				// make call and mute participant
				myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);				Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
				phoneCallIntent.setData(Uri.parse("tel:7576930722"));
				myAudioManager.setMode(AudioManager.MODE_IN_CALL); 
				myAudioManager.setMicrophoneMute(mute);
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

}
