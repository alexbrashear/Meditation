package com.example.meditation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// The screen for the instructor, which reports the current questions.
// Allows the instructor to clear questions.
public class InstructorActivity extends Activity {
	public static InstructorActivity ia;
	private Date questionCutoffTime;
	private TreeMap<String, Integer> map;
	private Date sessionStart;
	protected boolean endOfCall;
	private boolean executeBackground = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ia = this;
        
		// Don't look at questions before this session.
		clearQuestions(null);
		Log.e("InstructorActivity", "Is end of call? " + endOfCall);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null && endOfCall) {
			if (bundle.get("START_TIME") != null) {
				sessionStart = (Date) bundle.get("START_TIME");
			}
		} else {
			sessionStart = new Date();
		}
		map = new TreeMap<String, Integer>();
    
		new QuestionThread().execute();
	}
	
	// Display the questions pulled from the server
	public void addQuestions(ArrayList<String> customQuestions, ArrayList<BuiltinQuestion> builtinQuestions) {
		((ListView)this.findViewById(R.id.builtinQuestions)).setAdapter(new CustomAdapterBQ(this,builtinQuestions));
		((ListView)this.findViewById(R.id.customQuestions)).setAdapter(new ArrayAdapter<String>(this, R.layout.custom_list_layout, customQuestions));
		
	}
	
	// Reset the timestamp on the view when the user presses the button.
	public void clearQuestions(View v) {
		questionCutoffTime = new Date();
	}
	
	public Date getSessionStart() {
		return sessionStart;
	}
	
	public void stopBackground() {
		executeBackground = false;
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
	                			if (endOfCall) {
	                				cutoff = sessionStart;
	                			} else {
	                				cutoff = questionCutoffTime;
	                			}
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
		                
                		addQuestions(customQuestions, builtinsWithCounts);
                		map.clear();
		            }
		        }
		    });
		    
		    if (!endOfCall) {
		    	Date timeSince = new Date(System.currentTimeMillis() - questionCutoffTime.getTime());
		    	String timeText = String.format("%02d",timeSince.getMinutes()) + 
		    			":" + String.format("%02d",timeSince.getSeconds()) +
		    			" since last refresh.";
		    	((TextView) findViewById(R.id.time_since)).setText(timeText);
		    	
		    }
		    if (executeBackground) {
	    		new QuestionThread().execute();
	    	}
		    
		}
		
	}

}
