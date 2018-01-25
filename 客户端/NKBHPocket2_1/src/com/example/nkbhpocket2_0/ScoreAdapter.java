package com.example.nkbhpocket2_0;

import java.util.List;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScoreAdapter extends ArrayAdapter<StuScore> {

	private int resourceId;

	
	
	//getView()会当子项进入屏幕时，会被调用
	
	public ScoreAdapter(Context context, int resource, List<StuScore> objects) {
		super(context, resource, objects);
		resourceId=resource;
		// TODO Auto-generated constructor stub
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	
		StuScore item=getItem(position);
		View view=LayoutInflater.from(getContext()).inflate(resourceId, null);
		
		TextView courseName=(TextView) view.findViewById(R.id.course_name_tv);
		TextView startTerm=(TextView) view.findViewById(R.id.start_term_tv);
		TextView courseType=(TextView) view.findViewById(R.id.coruse_type_tv);
		TextView credit=(TextView) view.findViewById(R.id.credit_tv);
		TextView scoreOne=(TextView) view.findViewById(R.id.score_one_tv);
		TextView scoreTwo=(TextView) view.findViewById(R.id.score_two_tv);
		TextView finalScore=(TextView) view.findViewById(R.id.final_score_tv);
		
		courseName.setText(item.getCourseName());
		startTerm.setText(item.getStartTerm());
		courseType.setText(item.getCourseType());
		credit.setText(item.getCredit());
		scoreOne.setText(item.getSocreOne());
		scoreTwo.setText(item.getScoreTwo());
		finalScore.setText(item.getFinalScore());
		
		return view;
	}
}
