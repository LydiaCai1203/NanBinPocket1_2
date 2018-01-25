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
 * ���ܣ�ʵ�ֵ�¼������&��ס��¼����
 * ���ߣ��õ���OKHTTP����eclipse�У�OkHttp�ڲ�����OKio,����Ҫע��
 * ҲҪ����OKio������������ΪHttpClient��Android5.0֮���Ѿ���ʱ
 * �ٷ������Ƽ�ʹ�á�
 *      ������Ҫ�õ��ȸ�Ĺٷ���Դ��Gson��������Json����
 *      ɸѡ���ݵ�ʱ���õ���Jsoup
 *      
 * ����cookie,�����Ƿ��ʽ���ϵͳʱ������������ͨ��cookie�������ǵ�ǰ��
 * ״̬�����ж��Ի�ȡ���ǵĵ�¼״̬�ģ���ôΪ���������ǵĵ�¼״̬����
 * �������Ա����Ǻ������������ݵ�ץȡ��������Ҫ�ڿͻ����ж�cookie���д洢
 * 
 * ��δʵ�ֵĹ��ܣ���Ҫ�����һ����ťʹ�õ�һ�¾Ϳ����˳����еû
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
	
	private SharedPreferences pref;    //����ʵ�ֳ־û��洢 
	
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
		
		//--------�ж���һ�θ�ѡ���״̬--------------------------------
		
		//�־û�����ʵ�ּ�ס����Ĺ���
		//ֻ�б�Ӧ�ÿ���ʹ������ļ�
		pref=getSharedPreferences("userInfo", MODE_PRIVATE);

		//����һ��ʼ�����ڶ�Ӧ��ֵ�����Ի�ʹ��Ĭ��ֵfalse,������ʲô�����ᷢ����
		boolean isRemember=pref.getBoolean("remember_password", false);  //ѡȡkey==remember_password��Ӧ��ֵ
				
		if(isRemember) {
			//���˺ź����붼���ý��ı�����
			String account=pref.getString("account", "");
					
			String password=pref.getString("password", "");
					
			accountET.setText(account);
					
			pswET.setText(password);
					
			rememberPwd.setChecked(true);
		}
		//-------------------------------------------------------------
		
	    //�������ťʱ���õ���¼����ڴ˴���鸴ѡ���Ƿ�ѡ��
		loginBTN.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				account=accountET.getText().toString();
		        
				password=pswET.getText().toString();
				
				//---------ʵ�ּ�ס����Ĺ���--------------------------------------
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
				
				//��218��ChooseMenu.handler�����������ص�ҳ�潻��choosemenu��handler���н����õ�userInfo
				//�������ݣ��ڽ�����̫����TextView������ʱ��ȼ���ActivityҪ����������@���̵߳ȴ�1s���������ڼ�����һ��Activity
				//��������ҳ���Ѿ���ת��������Ϣ�������ݻ���default content
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
	 * �����첽GET����
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

    //�������̺߳����߳�ֱ�����ݵĴ���
    public static void sendMessage(String res,Handler handler) {
    	
    	Message message=new Message();
    	
    	message.obj=res;
    	
    	handler.sendMessage(message);
    	
    }
    
    //����ȡ����Ĭ��ҳ�棬Ŀ����Ϊ�˵õ�_VIEWSTATE/_EVENTVALIDACTION/...�õ��Ժ�
  	//��װ��һ��������.����ҳ��Ĺ�����Ȼ�Ǹ�Jsoup����ɡ�
  	
  	protected void parseHtml(String html) {
  		// ��htmlתΪDocument����
  		Document document=Jsoup.parse(html);
  		
  		//�����Ҫ������
  	    String __VIEWSTATE=document.select("input[name='__VIEWSTATE']").val();
  		String __EVENTVALIDATION=document.select("input[name='__EVENTVALIDATION']").val();
  	    //������POST��Ҫ���ݵĶ��󶼷�װ��һ��������ȥ
  		PostInfo postInfo=new PostInfo(__VIEWSTATE, __EVENTVALIDATION, account, password);
  		
  		postInfo.logOvject();  //��������������û�б���װ��ȥ
  			
  		//POST,����Ҫ���ݵĲ�����װ�õĶ��󴫽�ȥ
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
				//��post�õ��ķ�������Ӧ�����ChooseMenu��handler���н�������
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
		
		//-------ʵ�ֳ־û�cookie����---------------------------
		//����һ���ļ����������������Ѵ��ڵ��ļ�����׷�����ݣ����û�����´���һ��
		pref=getSharedPreferences("myCookies",MODE_PRIVATE);
		editor=pref.edit();
		gson=new Gson();
		
		// TODO Auto-generated method stub
		storeInfo.okHttpClient=new OkHttpClient()
				.newBuilder()
				.cookieJar(new CookieJar() {
					
//					private final HashMap<String, List<okhttp3.Cookie>>cookieStore=new HashMap<String, List<Cookie>>();
					@Override
					
					//�ڷ���˸��ͻ��˷���Cookieʱ���á�
					public void saveFromResponse(HttpUrl httpUrl, List<okhttp3.Cookie> list) {
						
						// ÿһ�εõ�����cookie���浽�ļ�����			    
					    storeInfo.cookieStore.put(httpUrl.host(), list);
					    
					    Type listClass = new TypeToken<List<okhttp3.Cookie>>() {}.getType();
					    
						String mycookies=gson.toJson(list,listClass);   //list->Json
					    editor.putString("cookies"+storeInfo.cookieNumber, mycookies);   //�����ļ�
					    editor.commit();    //ǧ�������!! 
					    
					    //Log�����鿴һ�¶Դ�
                        String cookiePrint=pref.getString("cookies"+storeInfo.cookieNumber, null);
					    Log.d("cookieTest", cookiePrint);
					    storeInfo.cookieNumber++;
					}
					
					@Override
					//ÿ�����client���ʵ�ĳһ������ʱ���ͻ�ͨ���˷�����ȡ�����cookie,���ҷ��͸�������
					public List<okhttp3.Cookie> loadForRequest(HttpUrl httpUrl) {
						// TODO Auto-generated method stub
					    List<okhttp3.Cookie> cookies = storeInfo.cookieStore.get(httpUrl.host());
					    //Ϊʲôif-else���������䶼��ִ��
					  
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



