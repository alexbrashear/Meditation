package com.example.meditation;

import java.util.ArrayList;
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
	private TreeMap<String, Integer> map;
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
		map = new TreeMap<String, Integer>();
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
		            	ArrayList<String> customQuestions = new ArrayList<String>();
		            	ArrayList<String> builtinQuestions = new ArrayList<String>();
		            	
		                for (ParseObject question : questions) {
		                	if (question.getCreatedAt().after(questionCutoffTime)) {
		                		ArrayList<String> toAddTo = null;
		                		if (question.getBoolean("custom")) {
		                			toAddTo = customQuestions;
		                		} else {
		                			toAddTo = builtinQuestions;
		                			BuiltinQuestion.totalCount++;
		                		}
		                		if (question.get("text") != null) {
		                			String text = question.get("text").toString();
			                		if (map.containsKey(text)) {
			                			map.put(text, map.get(text) + 1);
			                		} else {
			                			map.put(text, 1);
			                		}
		                			toAddTo.add(text);
		                		}
		                	}
		                }

		                ArrayList<BuiltinQuestion> builtinsWithCounts = new ArrayList<BuiltinQuestion>();
		                for (String text : builtinQuestions) {
		                	builtinsWithCounts.add(new BuiltinQuestion(text, map.get(text)));
		                }
		                
                		((InstructorActivity) getContext()).addQuestions(customQuestions, builtinsWithCounts);
                		map.clear();
                		BuiltinQuestion.totalCount = 0;

		                //Log.e("Activity", "Still running with " + counter + " questions pulled");
		            } else {
		                //Log.e("Brand", "Error: " + e.getMessage());
		            }
					
					Log.e("Activity", "This is executing!");
					invalidate();
		        }
		    });
			new QuestionThread().execute();
		}
		
	}

}
