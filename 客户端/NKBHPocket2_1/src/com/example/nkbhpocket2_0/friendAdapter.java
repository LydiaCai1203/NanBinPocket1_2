package com.example.nkbhpocket2_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.example.nkbhpocket2_0.R.drawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class friendAdapter extends ArrayAdapter<Friend> {

	private int resourceID;
	
	private Gson gson;
	
	private String friendImgName;
	
	private Integer friendImgID;
	
	private String friendName;
	
	private String friendID;
	
	public friendAdapter(Context context, int resource, List<Friend> objects) {
		super(context, resource, objects);
		resourceID=resource;
		gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
		friendID=Fragment3.stuID;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Friend item = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceID, null);
		
	    Integer imgId = item.getFriendImgId();
	    String friendName=item.getFriendName();
	    
	    setFriendInfo(imgId,friendName,view);
		
	    return view;
		
	}
        
	
	
	
    public void setFriendInfo(final Integer imgId,final String friendName,final View view) {
    	
    	AsyncTask<Void, String, Void> mytask=new AsyncTask<Void, String, Void>() {
    		
    		private ImageView friend_img;
    		private TextView friend_name;
    		
    		@Override
    		protected void onPreExecute() {
    			// TODO Auto-generated method stub
    			super.onPreExecute();
    			friend_img=(ImageView) view.findViewById(R.id.frie_ava_iv);
    			friend_name=(TextView) view.findViewById(R.id.frie_name_tv);
    		}
    		
			@Override
			protected Void doInBackground(Void... params) {
				
				String[] values=new String[2];
				
				//---------------------找头像-----------------------------------------------------------------
				URL imgUrl;
				try {
					imgUrl = new URL("http://192.168.1.109:8080/ChatBinhai/findAvatarById.jsp?avaID="+imgId);
					
//					imgUrl = new URL("http://172.20.10.3:8080/ChatBinhai/findAvatarById.jsp?avaID="+imgId);
					BufferedReader avaReader=new BufferedReader(new InputStreamReader(imgUrl.openStream()));
					StringBuffer avabuf=new StringBuffer();
					String avaline;
					while((avaline=avaReader.readLine())!=null) {
						avabuf.append(avaline);
					}
					String avajson=avabuf.toString();
					Avatar avatar=gson.fromJson(avajson, Avatar.class);
				    friendImgName=avatar.getImgName();    //----------------------1
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    

				publishProgress(friendImgName,friendName);
                    
			    return null;
			}
			
			
			
			@Override
			protected void onProgressUpdate(String... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
				
				//----------设置好友头像图片----------------------
				Class<drawable> drawable = R.drawable.class;
			    Field field=null;
			    try {
					field=drawable.getField(values[0]);
					friendImgID=field.getInt(field.getName());
					friend_img.setBackgroundResource(friendImgID);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			    //---------设置好友名字---------------------------
			    friend_name.setText(values[1]);
			    
			}
			
		};

		mytask.execute();
    }

}
