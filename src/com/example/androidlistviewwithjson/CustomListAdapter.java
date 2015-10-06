package com.example.androidlistviewwithjson;

import java.util.List;

import com.example.androidlistviewwithjson.model.Match;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class CustomListAdapter extends ArrayAdapter<Match> {

	private final Activity context;
	private List<Match> matches;
	
	public CustomListAdapter(Activity context, List<Match> matches) {
		super(context, R.layout.mylist, matches);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.matches = matches;
		
		
	}
	
	
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.mylist, null,true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
		TextView txtTitle2 = (TextView) rowView.findViewById(R.id.item2);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		ImageView imageView2 = (ImageView) rowView.findViewById(R.id.icon2);
		TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
		TextView extratxt2 = (TextView) rowView.findViewById(R.id.textView2);
		
		Match m=matches.get(position);
		
		txtTitle.setText(m.getHomeTeam());
		txtTitle2.setText(m.getAwayTeam());
	//	imageView.setImageResource(imgid[position]);
   //   imageView2.setImageResource(imgid[position]);		
		extratxt.setText(m.getHomeGoalTeam());
		extratxt2.setText(m.getAwayGoalTeam());
		return rowView;
		
	};
}