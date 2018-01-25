package com.example.nkbhpocket2_0;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MsgAdapter extends ArrayAdapter<Chatlog> {
	
	private int resourceId;

	public MsgAdapter(Context context, int resource, List<Chatlog> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId=resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Chatlog msg=getItem(position);
		
		Log.d("msg", msg.toString());
		
		View view;
		
		ViewHolder viewHolder;
		
		if(convertView==null) {
			view=LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder=new ViewHolder();
			viewHolder.leftLayout=(LinearLayout) view.findViewById(R.id.left_layout);
			viewHolder.rightLayout=(LinearLayout) view.findViewById(R.id.right_layout);
			viewHolder.leftMsg=(TextView) view.findViewById(R.id.left_msg);
			viewHolder.rightMsg=(TextView) view.findViewById(R.id.right_msg);
			viewHolder.showTime=(TextView) view.findViewById(R.id.show_time_tv);
			
			view.setTag(viewHolder);
		}else {
			view=convertView;
			viewHolder=(ViewHolder) view.getTag();
		}
		
		//如果自己不是发送端，就显示左边的消息布局
		if(msg.getSenderId().equals(Fragment3.stuID)) {

			viewHolder.leftLayout.setVisibility(View.GONE);
			viewHolder.rightLayout.setVisibility(View.VISIBLE);
			viewHolder.rightMsg.setText(msg.getContent());

		}else {
			
	    //如何自己是发送端，则显示右边的消息布局			
			viewHolder.leftLayout.setVisibility(View.VISIBLE);
			viewHolder.rightLayout.setVisibility(View.GONE);
			viewHolder.leftMsg.setText(msg.getContent());

		}
		viewHolder.showTime.setText(msg.getPubTime().toString());
		return view;
			
			
	}
	
	
	

}


class ViewHolder{
	
	LinearLayout leftLayout;
	
	LinearLayout rightLayout;
	
	TextView leftMsg;
	
	TextView rightMsg;
	
	TextView showTime;
}
