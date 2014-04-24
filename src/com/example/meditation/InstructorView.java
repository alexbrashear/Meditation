package com.example.meditation;

import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;

public class InstructorView extends View {

	private Date questionCutoffTime;
	
	public InstructorView(Context context) {
		super(context);
		init();
	}
	
	public InstructorView(Context context, AttributeSet as) {
		super(context, as);
		init();
	}
	
	private void init() {
		resetCutoff();
		((InstructorActivity) getContext()).setView(this);
		new QuestionThread().execute();
	}
	
	public void resetCutoff() {
		questionCutoffTime = new Date();
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
		            	StringBuilder q = new StringBuilder();
		                for (ParseObject question : questions) {
		                	if (question.getCreatedAt().after(questionCutoffTime)) {
				                q.append(question.get("text"));
				                q.append("\n");
		                	}
		                }
		                ((InstructorActivity) getContext()).getQuestions().setText(q.toString());
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
