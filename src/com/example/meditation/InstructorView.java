package com.example.meditation;

import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;
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

	private Date questionCutoffTime;
	private TreeMap<String, Integer> map = new TreeMap<String, Integer>();
	private Date sessionStart;
	
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
		sessionStart = new Date();
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
				Thread.sleep(10);
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
		            	int counter = 0;
		                for (ParseObject question : questions) {
			                
		                	if (question.getCreatedAt().after(questionCutoffTime)) {
				                //q.append(question.get("text"));
				                //q.append("\n");
		                		String ques = (String) question.get("text");
		                		if (map.containsKey(ques)) {
		                			map.put(ques, map.get(ques) + 1);
		                		} else {
		                			map.put(ques, 1);
		                		}
				                counter++;
		                	}
		                }
		                Log.e("Activity", "Still running with " + counter + " questions pulled");
		                StringBuilder q = new StringBuilder();
		                for (String s : map.descendingKeySet()) {
		                	q.append(s);
		                	q.append(": ");
		                	q.append(map.get(s).toString());
		                	q.append("\n");
		                }
		                ((InstructorActivity) getContext()).getQuestions().setText(q.toString());
		                map.clear();
		            } else {
		                //Log.e("Brand", "Error: " + e.getMessage());
		            }
					
					invalidate();
					Log.e("Activity", "This is executing!");
					new QuestionThread().execute();
		        }
		    });
		}
		
	}

}
