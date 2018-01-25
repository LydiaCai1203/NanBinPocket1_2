package com.example.nkbhpocket2_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 
 * @author ����ݼ
 * ���ܣ��������itemѡ����������ﵽĿ�ģ�ÿ���һ������item������һ���µ�Fragment
 *��һ�����⣺Ϊʲô���Fragment���ҳ�涼������ִ��onCreateView������list���滹����ֵ
 *���listռ�еĿռ��ǲ��ᱻ�ͷŵĻ�����ôΪʲôpublic static int i=0 ����0��
 */
public class Fragment3 extends Fragment {

	private ListView friendContainer;
	
	public Bundle bundle;
	
	public static String stuID;
	
	private friendAdapter fa;
	
	private Gson gson;
	
	List<Friend> list=new ArrayList<Friend>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.fargment_three, container,false);
		friendContainer=(ListView) view.findViewById(R.id.friend_container);
		
		bundle=getArguments();
		stuID=bundle.getString("stuID");
		Log.d("stuIDforFragment3", stuID);
		
		
		fillList();
		
		
		return view;
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	    list.clear();
	}
	
	//------------position��ֵ�Ǵ�0��ʼ��--------------------------------------------------------------------
	public void selectItem(int position) {
	
		//�˴�Ӧ�ÿ���һ���µ�Fragment��Ϊ�������,�ѵ��item��id����MsgFragment
		MsgFragment msgFragment=new MsgFragment();
		
		Friend[] friend=list.toArray(new Friend[0]);
		
		
		Bundle bundle=new Bundle();
		bundle.putString("friendName", friend[position].getFriendName());
		msgFragment.setArguments(bundle);
		
		//Ӧ�ø�����������Ϊ������������
		
		FragmentManager fragmentManager=getFragmentManager();
		fragmentManager.beginTransaction().add(R.id.fragment_container,msgFragment).commit();
		
	}
	
	public void fillList() {
		
		gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
		
		AsyncTask<Void, Void, Void> mytask=new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				URL friendUrl;
			    
				try {
					 friendUrl = new URL("http://192.168.1.109:8080/ChatBinhai/findMyFriend.jsp?myNumber="+stuID);
//					 friendUrl = new URL("http://172.20.10.3:8080/ChatBinhai/findMyFriend.jsp?myNumber="+stuID);
					
					 BufferedReader frieReader=new BufferedReader(new InputStreamReader(friendUrl.openStream()));
				    	
				     StringBuffer friebuf=new StringBuffer();
							    	
					 String frieline;
							    	
					 while((frieline=frieReader.readLine())!=null) {
							    		
					  		friebuf.append(frieline);
				  	 }
					 String friejson=friebuf.toString();
						    	
			     	 Friend[] friends=gson.fromJson(friejson, Friend[].class);
							    	
				     for(int i=0;i<friends.length;i++) {
						    list.add(friends[i]);
				     }
								    
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				fa=new friendAdapter(getActivity(), R.layout.choosefriend_sublayout, list);
				friendContainer.setAdapter(fa);
				friendContainer.setOnItemClickListener(new OnItemClickListener() {
					
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						selectItem(position);     //��ʱ������msgFragment
					}
				});
			}
		};
		mytask.execute();
				    
	}
}
