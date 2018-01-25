package com.example.nkbhpocket2_0;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 
 * @author caiqj
 * 功能：实现登录教务网&记住登录密码
 * 工具：用的是OKHTTP，在eclipse中，OkHttp内部以来OKio,所以要注意
 * 也要导入OKio这两个包。因为HttpClient再Android5.0之后已经过时
 * 官方不再推荐使用。
 *      还有需要用到谷歌的官方开源库Gson用来解析Json数据
 *      筛选数据的时候用到了Jsoup
 *      
 * 保存cookie,当我们访问教务系统时，服务器都是通过cookie来对我们当前的
 * 状态进行判断以获取我们的登录状态的，那么为了能让我们的登录状态得以
 * 持续，以便我们后续对其它数据的抓取，我们需要在客户端中对cookie进行存储
 * 
 * 还未实现的功能：需要再添加一个按钮使得点一下就可以退出所有得活动
 */
public class Login extends Activity{
	
	private EditText accountET;
	
	private EditText pswET;
	
	private Button loginBTN;
	
	private CheckBox rememberPwd;
	
	private String account;

	private String password;
	
//	private OkHttpClient okHttpClient;
	
	private String baseUrl="http://222.30.63.15/";
	
	public Handler myHandler;
	
	private SharedPreferences pref;    //用来实现持久化存储 
	
	private SharedPreferences.Editor editor;
	
	private Gson gson;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		accountET=(EditText) findViewById(R.id.account_et);
		
		pswET=(EditText) findViewById(R.id.password_et);
		
		loginBTN=(Button) findViewById(R.id.login_btn);
		
		rememberPwd = (CheckBox) findViewById(R.id.remberPsw_cb);
		
		//--------判断上一次复选框的状态--------------------------------
		
		//持久化技术实现记住密码的功能
		//只有本应用可以使用这个文件
		pref=getSharedPreferences("userInfo", MODE_PRIVATE);

		//由于一开始不存在对应的值，所以会使用默认值false,这样就什么都不会发生了
		boolean isRemember=pref.getBoolean("remember_password", false);  //选取key==remember_password对应的值
				
		if(isRemember) {
			//将账号和密码都设置进文本框当中
			String account=pref.getString("account", "");
					
			String password=pref.getString("password", "");
					
			accountET.setText(account);
					
			pswET.setText(password);
					
			rememberPwd.setChecked(true);
		}
		//-------------------------------------------------------------
		
	    //当点击按钮时设置点击事件，在此处检查复选框是否被选中
		loginBTN.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				account=accountET.getText().toString();
		        
				password=pswET.getText().toString();
				
				//---------实现记住密码的功能--------------------------------------
				if(account.equals("15990046")&&password.equals("Cqj951203")) {
					
					editor=pref.edit();
				
					if(rememberPwd.isChecked()) {
						
						editor.putBoolean("remember_password", true);
						editor.putString("account",account);
						editor.putString("password", password);
						
					}else {
						editor.clear();
					}
					
					editor.commit();
					Intent intent=new Intent(Login.this, ChooseMenu.class);
					startActivity(intent);
					finish();
					
				}else {
					Toast.makeText(Login.this, "is invalid", Toast.LENGTH_SHORT).show();
				}				
				
				//-------------------------------------------------------------------------------------------
				
				getDefaultPage();
				
				myHandler=new Handler(new android.os.Handler.Callback() {
					
					@Override
					public boolean handleMessage(Message msg) {
						String html=(String) msg.obj;
						parseHtml(html);
						return true;
					}
				});
				
				
//				Intent intent=new Intent(Login.this, testHtml.class);
				
				//在218行ChooseMenu.handler将服务器传回的页面交给choosemenu的handler进行解析得到userInfo
				//解析数据，在将数据太塞入TextView的所需时间比加载Activity要长，因此在這让线程等待1s，解析完在加载另一个Activity
				//否则会出现页面已经跳转，但是信息栏的内容还是default content
			    try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent=new Intent(Login.this,ChooseMenu.class);
			    startActivity(intent);
			    finish();
			}

		});
		
	}


	protected void getDefaultPage() {
		// TODO Auto-generated method stub
		setCookie();
		getAsynHttp();
	}


	/*
	 * 进行异步GET操作
	 */
    private void getAsynHttp() {       
		// TODO Auto-generated method stub
    	
    	final Request request=new Request.Builder()
				.url(baseUrl+"nkemis/SystemLogin.aspx")
			    .addHeader("content-type", "application/x-www-form-urlencoded")
				.addHeader("host", "222.30.63.15")
				.build();

		Call call = storeInfo.okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
			
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				// TODO Auto-generated method stub
				String res=response.body().string();
				sendMessage(res,myHandler);		
//			    sendMessage(res, testHtml.handler);
			}
		
			
			@Override
			public void onFailure(Call call, IOException e) {
				// TODO Auto-generated method stub
//				Log.e("onFailer", e.getMessage());
				e.printStackTrace();
			}
		});
        
	}

    //用于主线程和子线程直线数据的传输
    public static void sendMessage(String res,Handler handler) {
    	
    	Message message=new Message();
    	
    	message.obj=res;
    	
    	handler.sendMessage(message);
    	
    }
    
    //解析取出的默认页面，目的是为了得到_VIEWSTATE/_EVENTVALIDACTION/...得到以后
  	//封装进一个对象当中.解析页面的工作当然是给Jsoup来完成。
  	
  	protected void parseHtml(String html) {
  		// 将html转为Document对象
  		Document document=Jsoup.parse(html);
  		
  		//获得需要的数据
  	    String __VIEWSTATE=document.select("input[name='__VIEWSTATE']").val();
  		String __EVENTVALIDATION=document.select("input[name='__EVENTVALIDATION']").val();
  	    //将所有POST需要传递的对象都封装进一个对象当中去
  		PostInfo postInfo=new PostInfo(__VIEWSTATE, __EVENTVALIDATION, account, password);
  		
  		postInfo.logOvject();  //用来测试数据有没有被封装进去
  			
  		//POST,将需要传递的参数封装好的对象传进去
          LoginAccount(postInfo);
  		
  	}
    
    
    private void LoginAccount(PostInfo postInfo) {
    	
//    	setCookie();
    	
		// TODO Auto-generated method stub
		FormBody formbody=new FormBody.Builder()
				.add("__EVENTVALIDATION", postInfo.get__EVENTVALIDATION())
				.add("__VIEWSTATE", postInfo.get__VIEWSTATE())
				.add("ImageButton1.x", "7")
				.add("ImageButton1.y", "5")
				.add("txtPasswd", postInfo.getTxtPasswd())
				.add("txtUserID", postInfo.getTxtUserId())
				.build();
		
		final Request request=new Request.Builder()
				.url(baseUrl+"nkemis/SystemLogin.aspx")
				.addHeader("cache-control", "no-cache")
				.addHeader("content-type", "application/x-www-form-urlencoded")
				.post(formbody)
				.build();
		
		Call call=storeInfo.okHttpClient.newCall(request);
//		Log.d("Request", request.headers().toString());
		call.enqueue(new okhttp3.Callback() {
			
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				// TODO Auto-generated method stub
				String res=response.body().string();
				//将post得到的服务器回应传输给ChooseMenu的handler进行解析处理
				sendMessage(res, ChooseMenu.handler);
			}
			
			@Override
			public void onFailure(Call call, IOException e) {
				// TODO Auto-generated method stub
				Log.e("onFailer", e.getMessage());
				e.printStackTrace();
			}
		});
	}

    
//    private final HashMap<String, List<okhttp3.Cookie>>cookieStore=new HashMap<String, List<okhttp3.Cookie>>();
    
	public void setCookie() {	
		
		//-------实现持久化cookie技术---------------------------
		//参数一：文件名；参数二：往已存在的文件里面追加内容，如果没有重新创建一个
		pref=getSharedPreferences("myCookies",MODE_PRIVATE);
		editor=pref.edit();
		gson=new Gson();
		
		// TODO Auto-generated method stub
		storeInfo.okHttpClient=new OkHttpClient()
				.newBuilder()
				.cookieJar(new CookieJar() {
					
//					private final HashMap<String, List<okhttp3.Cookie>>cookieStore=new HashMap<String, List<Cookie>>();
					@Override
					
					//在服务端给客户端发送Cookie时调用。
					public void saveFromResponse(HttpUrl httpUrl, List<okhttp3.Cookie> list) {
						
						// 每一次得到的新cookie都存到文件里面			    
					    storeInfo.cookieStore.put(httpUrl.host(), list);
					    
					    Type listClass = new TypeToken<List<okhttp3.Cookie>>() {}.getType();
					    
						String mycookies=gson.toJson(list,listClass);   //list->Json
					    editor.putString("cookies"+storeInfo.cookieNumber, mycookies);   //存入文件
					    editor.commit();    //千万别忘记!! 
					    
					    //Log出来查看一下对错
                        String cookiePrint=pref.getString("cookies"+storeInfo.cookieNumber, null);
					    Log.d("cookieTest", cookiePrint);
					    storeInfo.cookieNumber++;
					}
					
					@Override
					//每当这个client访问到某一个域名时，就会通过此方法获取保存的cookie,并且发送给服务器
					public List<okhttp3.Cookie> loadForRequest(HttpUrl httpUrl) {
						// TODO Auto-generated method stub
					    List<okhttp3.Cookie> cookies = storeInfo.cookieStore.get(httpUrl.host());
					    //为什么if-else里的两条语句都会执行
					  
					    return cookies!=null?cookies:(new ArrayList<Cookie>());
					}
				})
				.build();
	}

}


class PostInfo{
	
	private String __VIEWSTATE;
	
	private String __EVENTVALIDATION;
	
	private String txtUserId;
	
	private String txtPasswd;
	
	private String ImageButton1X;
	
	private String ImageButton1Y;
	
	private String newtxtPasswd;

	public PostInfo(String __VIEWSTATE, String __EVENTVALIDATION, String txtUserId, String txtPasswd) {
		super();
		this.__VIEWSTATE = __VIEWSTATE;
		this.__EVENTVALIDATION = __EVENTVALIDATION;
		this.txtUserId = txtUserId;
		this.txtPasswd = txtPasswd;
		this.ImageButton1X="0";
		this.ImageButton1Y="0";
	}
	
	public PostInfo(String __VIEWSTATE, String __EVENTVALIDATION, String txtUserId, String txtPasswd,String newtxtPasswd) {
		super();
		this.__VIEWSTATE = __VIEWSTATE;
		this.__EVENTVALIDATION = __EVENTVALIDATION;
		this.txtUserId = txtUserId;
		this.txtPasswd = txtPasswd;
		this.ImageButton1X="0";
		this.ImageButton1Y="0";
		this.newtxtPasswd=newtxtPasswd;
	}

	public String getNewtxtPasswd() {
		return newtxtPasswd;
	}

	public void setNewtxtPasswd(String newtxtPasswd) {
		this.newtxtPasswd = newtxtPasswd;
	}

	public String getTxtUserId() {
		return txtUserId;
	}

	public void setTxtUserId(String txtUserId) {
		this.txtUserId = txtUserId;
	}

	public String getTxtPasswd() {
		return txtPasswd;
	}

	public void setTxtPasswd(String txtPasswd) {
		this.txtPasswd = txtPasswd;
	}
	
	
	
	public String get__VIEWSTATE() {
		return __VIEWSTATE;
	}

	public void set__VIEWSTATE(String __VIEWSTATE) {
		this.__VIEWSTATE = __VIEWSTATE;
	}

	public String get__EVENTVALIDATION() {
		return __EVENTVALIDATION;
	}

	public void set__EVENTVALIDATION(String __EVENTVALIDATION) {
		this.__EVENTVALIDATION = __EVENTVALIDATION;
	}

	

	public String getImageButton1X() {
		return ImageButton1X;
	}

	public void setImageButton1X(String imageButton1X) {
		ImageButton1X = imageButton1X;
	}

	public String getImageButton1Y() {
		return ImageButton1Y;
	}

	public void setImageButton1Y(String imageButton1Y) {
		ImageButton1Y = imageButton1Y;
	}

	public void logOvject() {
		Log.d("PostInfo__EVENTVALIDATION", __EVENTVALIDATION);
		Log.d("PostInfo__VIEWSTATE", __VIEWSTATE);
		Log.d("PostInfo_txtUserId", txtUserId);
		Log.d("PostInfo_txtPasswd", txtPasswd);
	}
	
}



