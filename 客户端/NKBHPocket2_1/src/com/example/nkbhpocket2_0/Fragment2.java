package com.example.nkbhpocket2_0;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * @author ����ݼ
 * 
 * ���ܣ�ʵ���޸�����
 *
 */
public class Fragment2 extends Fragment {

	private EditText account_et;
	
	private EditText oldPwd_et;
	
	private EditText newPwd_et;
	
	private Button modifyPwd_btn;
	
	private String account;
	
	private String oldPwd;
	
	private String newPwd;
	
	private String modifyPwdUrl;
	
	//--------------------------------------------------------
	
	private Elements tables;
	
	private String baseUrl="http://222.30.63.15";
	
	private SharedPreferences pref;
	
	private SharedPreferences.Editor editor;
	
	private Gson gson;
	
	private String modiPwdHtml=null;

	private Handler myHandler;
	
	public static String stuDefau=null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v=inflater.inflate(R.layout.fargment_two, container,false);
		
		//------------------��ȡ�ؼ�----------------------------------------------
		account_et=(EditText) v.findViewById(R.id.mody_account_et);
		oldPwd_et=(EditText) v.findViewById(R.id.old_password_et);
		newPwd_et=(EditText) v.findViewById(R.id.new_password_et);
		modifyPwd_btn=(Button) v.findViewById(R.id.modify_btn);
		
		//-------------------------------------------------------------------------
		
		//----------������ҳ��ȡ�޸�����Ĳ���---------------------------------------
		modifyPwdUrl=modifyPwd();	
		setCookie();
		
		modifyPwd_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        account=account_et.getText().toString();
		        oldPwd=oldPwd_et.getText().toString();
		        newPwd=newPwd_et.getText().toString();
		
		        getModifyPwdPage(modifyPwdUrl);
		        
			}
		});
		

		myHandler=new Handler(new android.os.Handler.Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				String text=(String) msg.obj;
				myDialog(text);
				return true;
			}
		});
		
		return v;
	}

	protected void myDialog(String text) {
		// TODO Auto-generated method stub
		
		new AlertDialog.Builder(getActivity())
		               .setTitle("tip:")
		               .setMessage(text)
		               .setPositiveButton("Yes",new android.content.DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							account_et.setText(null);
							oldPwd_et.setText(null);
							newPwd_et.setText(null);
						}
					})
		               .create() 
		               .show();
		
	}

	private String modifyPwd() {
		// TODO Auto-generated method stub
	    
		Elements divs=null;
		
		tables=ChooseMenu.tables;   //֮ǰ�Ѿ�������һ���ˣ���ξͲ��ٽ�����
	    
	    Elements tds=tables.get(1).select("tr").select("td");  
	    
	    for(int i=0;i<tds.size();i++) {
	 
	    	divs=tds.get(i).select("div");
	    }
	    
	    Elements links=divs.first().getElementsByTag("a");
	 
	    Element link=links.last();
	    
	    String linkHref=link.attr("href");    //��Ϊ�鿴Դ��ҳ��֪������Ӿ�������Ҫ���޸������ҳ����
	    
	    String modifyPwdHref=baseUrl+linkHref;    //�õ�������URL�ٷ��͸���������ȡ�޸������ҳ��
	    
	    Log.d("modiPwd", modifyPwdHref);
	    
	    return modifyPwdHref;
	
	}
	
	private void getModifyPwdPage(String url){
		
		final Request request=new Request.Builder()
					.url(url)
					.addHeader("content-type", "application/x-www-form-urlencoded")
				    .addHeader("host", "222.30.63.15")
					.build();

				Call call = storeInfo.okHttpClient.newCall(request);
		        call.enqueue(new okhttp3.Callback() {
					
					@Override
					public void onResponse(Call call, Response response) throws IOException {
						// TODO Auto-generated method stub
						String res=response.body().string();
						Log.d("modiPwdHtml", res);
						parseHtml(res);
					}
				
					
					@Override
					public void onFailure(Call call, IOException e) {
						// TODO Auto-generated method stub
						Log.e("onFailer", e.getMessage());
						e.printStackTrace();
					}
				});
	}
	
	
	protected void parseHtml(String html) {
  		// ��htmlתΪDocument����
  		Document document=Jsoup.parse(html);
  		
  		//�����Ҫ������
  	    String __VIEWSTATE=document.select("input[name='__VIEWSTATE']").val();
  		String __EVENTVALIDATION=document.select("input[name='__EVENTVALIDATION']").val();
  	    //������POST��Ҫ���ݵĶ��󶼷�װ��һ��������ȥ
  		PostInfo postInfo=new PostInfo(__VIEWSTATE, __EVENTVALIDATION, account, oldPwd,newPwd);
  		
  		postInfo.logOvject();  //��������������û�б���װ��ȥ
  			
  		//POST,����Ҫ���ݵĲ�����װ�õĶ��󴫽�ȥ
          LoginAccount(postInfo);
  		
  	}
	
    private void LoginAccount(PostInfo postInfo) {
    	

		FormBody formbody=new FormBody.Builder()
				.add("__EVENTVALIDATION", postInfo.get__EVENTVALIDATION())
				.add("__VIEWSTATE", postInfo.get__VIEWSTATE())
				.add("ImageButton3.x", "6")
				.add("ImageButton3.y", "4")
				.add("txtOldPasswd", postInfo.getTxtPasswd())
				.add("txtNewPasswd", postInfo.getNewtxtPasswd())
				.add("txtNewPasswd1", postInfo.getNewtxtPasswd())
				.build();
		
		final Request request=new Request.Builder()
				.url(modifyPwdUrl)
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
				Log.d("status", res);
				
				Document doc=Jsoup.parse(res);
				Element span=doc.select("span").first();
				String text=span.text();
				Log.d("span", text);
				
				Login.sendMessage(text, myHandler);  //��������Ϣ���ص�UI�߳�
				
			}
			
			@Override
			public void onFailure(Call call, IOException e) {
				// TODO Auto-generated method stub
				Log.e("onFailer", e.getMessage());
				e.printStackTrace();
			}
		});
	}
    
    
	//AsyncTask<Params, Progress, Result>
    //Params:���Ǵ��ݸ��첽����ʱ�Ĳ�������
	//Progress�����ǵ��첽������ִ�е�ʱ��ִ�еĽ��ȷ��ظ�UI�̵߳Ĳ���������
	//Result:�첽����ִ����󷵻ظ�UI�̵߳Ľ��������
	public void setCookie() {
        
		pref=getActivity().getSharedPreferences("myCookies", Context.MODE_PRIVATE);
		editor=pref.edit();
		
		gson=new Gson();
		
		storeInfo.okHttpClient=new OkHttpClient()
				.newBuilder()
				.cookieJar(new CookieJar() {
					
//					private final HashMap<String, List<okhttp3.Cookie>>cookieStore=new HashMap<String, List<Cookie>>();
					@Override
					
					//�ڷ���˸��ͻ��˷���Cookieʱ���á�
					public void saveFromResponse(HttpUrl httpUrl, List<okhttp3.Cookie> list) {
						// TODO Auto-generated method stub
						
						storeInfo.cookieStore.put(httpUrl.host(), list);
					
					}
					
					@Override
					//ÿ�����client���ʵ�ĳһ������ʱ���ͻ�ͨ���˷�����ȡ�����cookie,���ҷ��͸�������
					public List<okhttp3.Cookie> loadForRequest(HttpUrl httpUrl) {
						// TODO Auto-generated method stub
					    List<okhttp3.Cookie> cookies_first = storeInfo.cookieStore.get(httpUrl.host());
					    //Ϊʲôif-else���������䶼��ִ��


					    //���ļ������cookies��ȡ����
					    String cookie=null;

					    cookie=pref.getString("cookies"+(storeInfo.cookieNumber-2), null);
					    
					    //����Java���͵�ʵ�ֻ��ƣ�ʹ���˷��͵Ĵ����������ڼ���صķ��Ͳ��������Ͷ��ᱻ����
					    //�����޷��������ڼ��÷��Ͳ����ľ������ͣ����еķ�������������ʱ����Object���ͣ�
					    //Gson����TypeToken��������������
					    Type listClass = new TypeToken<List<okhttp3.Cookie>>() {}.getType();
					    
					    List<okhttp3.Cookie> cookies_second=gson.fromJson(cookie, listClass);
					    
					    cookies_first=cookies_second;
					    
					    return cookies_first!=null?cookies_first:(new ArrayList<okhttp3.Cookie>());
					}
				})
				.build();
	}
	
	
}
