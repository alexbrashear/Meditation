package com.example.meditation;

import android.app.Activity;
import android.os.Bundle;

public class ViewResultsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		UserActivity.ua.finish();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_results);
	}

}
