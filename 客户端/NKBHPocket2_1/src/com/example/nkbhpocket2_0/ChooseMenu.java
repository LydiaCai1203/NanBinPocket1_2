package com.example.nkbhpocket2_0;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler.Callback;

public class ChooseMenu extends Activity implements OnItemClickListener{
	
	private String baseUrl="http://222.30.63.15";
	//-------------------------------------------------------------------
	private DrawerLayout drawerLayout;
	
	private ListView listView;
	
	private FragmentManager fragmentManager;
	
	private Fragment fragment1;
	
	private Fragment fragment2;
	
	private Fragment fragment3;

	private Fragment fragment4;
	
	public static Handler handler;
	
	private String[] str;
	//-------------------------------------------------------------------
	
	private TextView avatarTv;      //�û�ͷ��          �����ݿ�����ȡ
	
    private TextView usernameTv;    //�û�����
    
    private TextView userIDTv;      //�û��������Ϣ
	
    //-------------------------------------------------------------------
    
    private Bundle bundle=new Bundle();   //������Fragment��ֵ�õ�
    
    private boolean isFirst=true;
    //-------------------------------------------------------------------
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	   
		setContentView(R.layout.choose_layout);
	    
	    init(); 
	    setDrawerEvent();
	}
	
	/**
	 * ���ü�������򿪹ر�״̬�ļ�����
	 */
	public void setDrawerEvent() {
		
		drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
				
				if(Fragment1.avatarId!=0) {
					avatarTv.setBackgroundResource(Fragment1.avatarId);
				}
	
			}
		});
		
	}
	
	private void init() {
		
		drawerLayout=(DrawerLayout) findViewById(R.id.my_drawerlayout);
		
		listView=(ListView) findViewById(R.id.form_listview);
	
		avatarTv=(TextView) findViewById(R.id.avatar_tv);
		
		usernameTv=(TextView) findViewById(R.id.username_tv);
		
		userIDTv=(TextView) findViewById(R.id.userID_tv);
		
		str=new String[] {"������Ϣ","�޸�����","�ҵĺ���","�ҵĳɼ�"};
		
		//������������ʱ����Ҫ�������������������ġ��Ӳ��ֵ�id������Դ
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,str);
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(this);
		
		fragment1=new Fragment1();
		
		fragment2=new Fragment2();
		
		fragment3=new Fragment3();
		
		fragment4=new Fragment4();
		
		//ʵ���û�ͷ����������¼״̬������
		
		handler=new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				// TODO Auto-generated method stub
				String res=(String)msg.obj;
				parseUserInfo(res);                //res��ѧ����ҳ
				
				String stuDefau=getStuDefauURL();      //���������urlȡ������Ϊ������fargment��ȡ������url
				bundle.putString("stuDefau", stuDefau);    //��ֵ��Ƕ�׵�Fragment
				return true;
			}
		});
		
	}


	public static Elements tables;   //����ѧ����ҳ�����б����Ϣ
	
	protected void parseUserInfo(String res) {
		// TODO Auto-generated method stub
		Document doc=Jsoup.parse(res);
		//��ȡ�����еı������
		//������ҳԴ����Եó����ڵ�һ�����������username
		//��ȡ���еı��
		tables=doc.select("table");
		//��ȡ��һ������������
		Elements trs=tables.get(0).select("tr");
		//��ȡ��һ���е�����
	    Element tds = trs.get(0);
	    //��ȡ����td�ڵ��������е��ı����ֵ����ݣ����Թ۲쵽
	    Log.d("textNode", tds.text());         //15990046 (����ݼ,Student) 111.161.223.57 2017��12��3�� ��ҳ | ��¼ | ע��
	    //����ֻ��Ҫȡ��������������ݼ���
	    String userInfo = tds.text();
	    
	    int start = userInfo.indexOf("(");    
	    int middle=userInfo.indexOf(",");     
	    int end=userInfo.indexOf(")");

	    //��ѧ��Ҳȡ����
	    String stuId=null;
	    stuId=userInfo.substring(0,8);
//	    Log.d("stuID", stuId);    //15990046
	    bundle.putString("stuID", stuId);    //��ѧ�Ŵ���Fragment1��������URl
	    
	    //�õ�username
	    String username=null;
	    username=userInfo.substring(start+1, middle);
	    Log.d("textNode", username);     //����ݼ
	    
	    //�õ��û������Ϣ
	    String userID=null;
	    userID=userInfo.substring(middle+1, end);    //Student
	    Log.d("textNode",userID);
	    
	    //��װ�ɶ���,�������������Ҫ�õ�������Ϣ����������ó�ȫ�ֱ���
	    //UserInfo user=new UserInfo(username, userID);
	    
	    //���û���Ϣ����TextView
	    usernameTv.setText(username);
	    userIDTv.setText(userID);
	    
	    
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
	    switch(position) {
	    case 0:
		    
	    	replaceFragment(fragment1);
	    	break;
	    case 1:
	    	
	    	replaceFragment(fragment2);
	    	break;
	    case 2:
	    	
	    	replaceFragment(fragment3);
	    	break;
	    case 3:
			
	    	replaceFragment(fragment4);
	        break;
	    	}
	   //���setAugument���BUG
	    if(isFirst==true) {
	    	fragment1.setArguments(bundle);
	    	fragment2.setArguments(bundle);
	    	fragment3.setArguments(bundle);
	    	fragment4.setArguments(bundle);
	    	isFirst=false;
	    }
	    setTitle(str[position]);
	   
	    drawerLayout.closeDrawers();
	}

	private void replaceFragment(Fragment fragment) {
		
		//choose_layout���framelayout��container,���Բ���container��id��Ϊ����������
		
		fragmentManager=getFragmentManager();
		
		FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
		
		fragmentTransaction.replace(R.id.fragment_container, fragment);
		
		fragmentTransaction.commit();
	
	}
	
	private String getStuDefauURL() {
		Elements divs=null;
	    
	    Elements tds=tables.get(1).select("tr").select("td");  
	    
	    for(int i=0;i<tds.size();i++) {
	 
	    	divs=tds.get(i).select("div");
	    }
	    
	    Elements links=divs.first().getElementsByTag("a");
	 
	    Element link=links.last();
	  
	    //��ȡstuDefau��ֵ
	    String stuDefau=baseUrl+links.first().attr("href");    //����ȡ��������URL
	    
	    return stuDefau;
	}
}


class UserInfo{
	
	private String username;
	private String userID;
	
	public UserInfo(String username, String userID) {
		super();
		this.username = username;
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}
