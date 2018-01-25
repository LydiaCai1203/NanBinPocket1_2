package com.example.nkbhpocket2_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.DeletedContacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MsgFragment extends Fragment {
	
	private ListView msgListView;
	
	private EditText inputText;
	
	private Button send;
	
	private MsgAdapter ma;
	
	private List<Chatlog> msgList=new ArrayList<Chatlog>();
	
	private String senderID;
	
	private String receiverID;
	
	private String contentIsend;
	
	private static int curQuantity=0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View v=inflater.inflate(R.layout.chatlog_fragment,container,false);
		
		
		msgListView=(ListView) v.findViewById(R.id.msg_list_view);
		inputText=(EditText) v.findViewById(R.id.input_text);
		send=(Button) v.findViewById(R.id.send_msg_btn);
		
		Bundle bundle=getArguments();
		String friendName=bundle.getString("friendName");  //为了取对方的学号
		senderID=Fragment3.stuID;
		
		fillList(friendName);
		
		
		//点击按钮时发布信息,这时候receiverID和senderID已经全部都有了
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				contentIsend=inputText.getText().toString();    //获取输入内容
				publishContent(contentIsend);
                inputText.setText("");
			}
		});
		
		return v;
	}


	protected void publishContent(String content) {
		
		AsyncTask<String, Void, String> mytask=new AsyncTask<String, Void, String>() {
			
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				try {
					URL url=new URL("http://192.168.1.109:8080/ChatBinhai/publishChat.jsp?senderID="+senderID+"&receiverID="+receiverID+"&content="+params[0]);
					BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream()));
				    String line;
				    StringBuffer buf=new StringBuffer();
				    while((line=reader.readLine())!=null) {
				    	buf.append(line);
				    }
				    String s=buf.toString();
				    return s;    
				    
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}

			}
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//刷新界面
				Toast.makeText(getActivity(),result, Toast.LENGTH_SHORT).show();
				flush(senderID, receiverID);
			}
		};
		mytask.execute(content);		
	}

	
	private void flush(final String senderID,final String receiverID) {
		
		AsyncTask<Void, Void, Void> mytask=new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected Void doInBackground(Void... params) {
				
				Gson gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
				// TODO Auto-generated method stub
				try {
					URL myurl=new URL("http://192.168.1.109:8080/ChatBinhai/findNewChatlog.jsp?senderID="+senderID+"&receiverID="+receiverID+"&curQuantity="+curQuantity);
					
//					URL myurl=new URL("http://172.20.10.3:8080/ChatBinhai/findChatlog.jsp?senderID="+senderID+"&receiverID="+receiverID);
					
					
					BufferedReader reader=new BufferedReader(new InputStreamReader(myurl.openStream()));
					
					StringBuffer buf=new StringBuffer();
					
					String line;
					
					while((line=reader.readLine())!=null) {
						buf.append(line);
					}
					String chatlog=buf.toString();
					Chatlog[] logs=gson.fromJson(chatlog, Chatlog[].class);
					
					for(int i=0;i<logs.length;i++) {
						msgList.add(logs[i]);
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
				
				//分配了新的内存，原来new出来的那块会被GC回收掉吗？
				curQuantity=msgList.get(msgList.size()-1).getLogId();   //取出左后一条消息的ID号，就是最大的ID号了
				ma=new MsgAdapter(getActivity(), R.layout.chatlog_sub_fragment, msgList);
				msgListView.setAdapter(ma);
			}
		};
		
		mytask.execute();
	}
	

	private void fillList(final String receiverName) {
		
		AsyncTask<Void, Void, Void> mytask=new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				Gson gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
				//------------------------通过receiverName获取receiverID------------------------------------------------
				try {
					URL url = new URL("http://192.168.1.109:8080/ChatBinhai/findStudentByName.jsp?stuName="+receiverName);
					
//					URL url = new URL("http://172.20.10.3:8080/ChatBinhai/findStudentByName.jsp?stuName="+receiverName);
					
					BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream()));
					
					StringBuffer buf=new StringBuffer();
					
					String line;
					
					while((line=reader.readLine())!=null) {
						buf.append(line);
					}
					
					String s=buf.toString();
					Stuinfo[] receiver=gson.fromJson(s, Stuinfo[].class);   //这里本来应该是Stuinfo[].class因为取出来只有一个
					receiverID=receiver[0].getStuId();
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//-------------------------------按照receiverID和senderID取服务器上面拉数据--------------------------------
				try {
					URL myurl=new URL("http://192.168.1.109:8080/ChatBinhai/findChatlog.jsp?senderID="+senderID+"&receiverID="+receiverID);
					
//					URL myurl=new URL("http://172.20.10.3:8080/ChatBinhai/findChatlog.jsp?senderID="+senderID+"&receiverID="+receiverID);
					
					
					BufferedReader reader=new BufferedReader(new InputStreamReader(myurl.openStream()));
					
					StringBuffer buf=new StringBuffer();
					
					String line;
					
					while((line=reader.readLine())!=null) {
						buf.append(line);
					}
					String chatlog=buf.toString();
					Chatlog[] logs=gson.fromJson(chatlog, Chatlog[].class);
					
					for(int i=0;i<logs.length;i++) {
						msgList.add(logs[i]);
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
				
				curQuantity=msgList.get((msgList.size()-1)).getLogId();   //取出左后一条消息的ID号，就是最大的ID号了
				ma=new MsgAdapter(getActivity(), R.layout.chatlog_sub_fragment, msgList);
				msgListView.setAdapter(ma);
			}
		};
		
		mytask.execute();
	}

}

