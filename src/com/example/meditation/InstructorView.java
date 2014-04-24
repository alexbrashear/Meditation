package com.example.meditation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;
import java.util.List;
import java.util.TreeSet;

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

// The view for the instructor.
// We need this to draw to the canvas and poll Parse for questions.
public class InstructorView extends View {

	private Date questionCutoffTime;
	private TreeMap<String, Integer> map;
	private Date sessionStart;
	private Date timeSince;
	
	public InstructorView(Context context) {
		super(context);
		init();
	}
	
	public InstructorView(Context context, AttributeSet as) {
		super(context, as);
		init();
	}
	
	private void init() {
		
		// Don't look at questions before this session.
		resetCutoff();
		sessionStart = new Date();
		map = new TreeMap<String, Integer>();
		
		// Tell our activity about this view.
		((InstructorActivity) getContext()).setView(this);
		new QuestionThread().execute();
	}
	
	public void resetCutoff() {
		questionCutoffTime = new Date();
	}

	public void onDraw(Canvas c) {
		timeSince = new Date(System.currentTimeMillis() - questionCutoffTime.getTime());
		String timeText = timeSince.getMinutes() + ":" + timeSince.getSeconds() +
				" since last refresh.";
		((InstructorActivity) getContext()).getTimeSince().setText(timeText);
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
			
			// Grab the questions from Parse
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Question");
		    query.findInBackground(new FindCallback<ParseObject>() {

		        @Override
		        public void done(List<ParseObject> questions, ParseException e) {
		            if (e == null) {
		            	ArrayList<String> customQuestions = new ArrayList<String>();
		            	TreeSet<String> builtinQuestions = new TreeSet<String>();
		            	
		            	// Iterate the questions, applying the cutoff
		                for (ParseObject question : questions) {
		                	Date cutoff;
		                	Collection<String> toAddTo = null;
		                	if (question.getBoolean("custom")) {
	                			toAddTo = customQuestions;
	                			cutoff = sessionStart;
	                		} else {
	                			toAddTo = builtinQuestions;
	                			cutoff = questionCutoffTime;
	                			BuiltinQuestion.totalCount++;
	                		}
		                	if (question.getCreatedAt().after(cutoff)) {
		                		if (question.get("text") != null) {
		                			String text = question.get("text").toString();
		                			
		                			// Count how many times the builtin questions appear.
			                		if (map.containsKey(text)) {
			                			map.put(text, map.get(text) + 1);
			                		} else {
			                			map.put(text, 1);
			                		}
		                			toAddTo.add(text);
		                		}
		                	}
		                }

		                // Collate the questions with the counts
		                ArrayList<BuiltinQuestion> builtinsWithCounts = new ArrayList<BuiltinQuestion>();
		                for (String text : builtinQuestions) {
		                	builtinsWithCounts.add(new BuiltinQuestion(text, map.get(text)));
		                }
		                
		                // Reverse custom questions to make the newest ones appear at the top
		                Collections.reverse(customQuestions);
		                
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
