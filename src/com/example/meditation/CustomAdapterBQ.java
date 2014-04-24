package com.example.meditation;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapterBQ extends ArrayAdapter<BuiltinQuestion> {
	private final Context context;
	private final List<BuiltinQuestion> values;

	public CustomAdapterBQ(Context context, List<BuiltinQuestion> values) {
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		TextView question = (TextView) rowView.findViewById(R.id.ques);
		TextView bar = (TextView) rowView.findViewById(R.id.bar);
		question.setText(values.get(position).toString());
		bar.setText(values.get(position).getCounts());
		bar.setTextColor(Color.RED);
		question.setTextColor(Color.BLACK);
		return rowView;
	}
	
}
