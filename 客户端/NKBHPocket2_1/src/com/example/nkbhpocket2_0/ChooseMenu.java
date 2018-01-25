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
	
	private TextView avatarTv;      //用户头像          从数据库里面取
	
    private TextView usernameTv;    //用户名称
    
    private TextView userIDTv;      //用户的身份信息
	
    //-------------------------------------------------------------------
    
    private Bundle bundle=new Bundle();   //用来向Fragment传值用的
    
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
	 * 设置监听抽屉打开关闭状态的监听器
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
		
		str=new String[] {"个人信息","修改密码","我的好友","我的成绩"};
		
		//调用适配器的时候需要传入三个参数：上下文、子布局的id，数据源
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,str);
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(this);
		
		fragment1=new Fragment1();
		
		fragment2=new Fragment2();
		
		fragment3=new Fragment3();
		
		fragment4=new Fragment4();
		
		//实现用户头像、姓名、登录状态的设置
		
		handler=new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				// TODO Auto-generated method stub
				String res=(String)msg.obj;
				parseUserInfo(res);                //res是学生首页
				
				String stuDefau=getStuDefauURL();      //这个完整的url取出来是为了能在fargment中取出绝对url
				bundle.putString("stuDefau", stuDefau);    //传值给嵌套的Fragment
				return true;
			}
		});
		
	}


	public static Elements tables;   //保存学生首页的所有表格信息
	
	protected void parseUserInfo(String res) {
		// TODO Auto-generated method stub
		Document doc=Jsoup.parse(res);
		//获取了所有的表格内容
		//根据网页源码可以得出，在第一个表格里面有username
		//获取所有的表格
		tables=doc.select("table");
		//获取第一个表格的所有行
		Elements trs=tables.get(0).select("tr");
		//获取第一行中的内容
	    Element tds = trs.get(0);
	    //获取所有td节点里面所有的文本部分的内容，可以观察到
	    Log.d("textNode", tds.text());         //15990046 (蔡箐菁,Student) 111.161.223.57 2017年12月3日 首页 | 登录 | 注销
	    //我们只需要取到括号里面的内容即可
	    String userInfo = tds.text();
	    
	    int start = userInfo.indexOf("(");    
	    int middle=userInfo.indexOf(",");     
	    int end=userInfo.indexOf(")");

	    //把学号也取出来
	    String stuId=null;
	    stuId=userInfo.substring(0,8);
//	    Log.d("stuID", stuId);    //15990046
	    bundle.putString("stuID", stuId);    //把学号传给Fragment1便于制造URl
	    
	    //得到username
	    String username=null;
	    username=userInfo.substring(start+1, middle);
	    Log.d("textNode", username);     //蔡箐菁
	    
	    //得到用户身份信息
	    String userID=null;
	    userID=userInfo.substring(middle+1, end);    //Student
	    Log.d("textNode",userID);
	    
	    //封装成对象,如果后续还有需要用到这里信息的情况就设置成全局变量
	    //UserInfo user=new UserInfo(username, userID);
	    
	    //将用户信息填入TextView
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
	   //多次setAugument会出BUG
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
		
		//choose_layout里的framelayout做container,所以不把container的id作为参数传入了
		
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
	  
	    //获取stuDefau的值
	    String stuDefau=baseUrl+links.first().attr("href");    //还是取不出绝对URL
	    
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
