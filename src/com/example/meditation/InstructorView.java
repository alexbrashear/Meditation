package com.example.meditation;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class InstructorView extends View {

	public InstructorView(Context context) {
		super(context);
		init();
	}
	
	public InstructorView(Context context, AttributeSet as) {
		super(context, as);
		init();
	}
	
	private void init() {
		new QuestionThread().execute();
	}

	public void onDraw(Canvas c) {
		
	}
	
	class QuestionThread extends AsyncTask<Void, Void, Void> {
		
		/**
		 * Sleeps for a certain amount of time before calling onPostExecute
		 */
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) { }
			return null;
		}
		
		protected void onPostExecute(Void v) {
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Question");

		    query.findInBackground(new FindCallback<ParseObject>() {

		        @Override
		        public void done(List<ParseObject> questions,
		                ParseException e) {
		            if (e == null) {
		                for (ParseObject question : questions) {
			                Log.e("Brand", "Retrieved " + question.get("text"));
		                }
		            } else {
		                //Log.e("Brand", "Error: " + e.getMessage());
		            }
					
					invalidate();
					new QuestionThread().execute();
		        }
		    });
		}
		
	}

}
