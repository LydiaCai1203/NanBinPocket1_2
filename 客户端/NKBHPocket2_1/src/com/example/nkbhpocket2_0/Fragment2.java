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
 * @author 蔡箐菁
 * 
 * 功能：实现修改密码
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
		
		//------------------获取控件----------------------------------------------
		account_et=(EditText) v.findViewById(R.id.mody_account_et);
		oldPwd_et=(EditText) v.findViewById(R.id.old_password_et);
		newPwd_et=(EditText) v.findViewById(R.id.new_password_et);
		modifyPwd_btn=(Button) v.findViewById(R.id.modify_btn);
		
		//-------------------------------------------------------------------------
		
		//----------解析网页获取修改密码的部分---------------------------------------
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
		
		tables=ChooseMenu.tables;   //之前已经解析过一次了，这次就不再解析了
	    
	    Elements tds=tables.get(1).select("tr").select("td");  
	    
	    for(int i=0;i<tds.size();i++) {
	 
	    	divs=tds.get(i).select("div");
	    }
	    
	    Elements links=divs.first().getElementsByTag("a");
	 
	    Element link=links.last();
	    
	    String linkHref=link.attr("href");    //因为查看源网页得知最后超链接就是我想要的修改密码的页面了
	    
	    String modifyPwdHref=baseUrl+linkHref;    //得到完整的URL再发送给服务器获取修改密码的页面
	    
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
  		// 将html转为Document对象
  		Document document=Jsoup.parse(html);
  		
  		//获得需要的数据
  	    String __VIEWSTATE=document.select("input[name='__VIEWSTATE']").val();
  		String __EVENTVALIDATION=document.select("input[name='__EVENTVALIDATION']").val();
  	    //将所有POST需要传递的对象都封装进一个对象当中去
  		PostInfo postInfo=new PostInfo(__VIEWSTATE, __EVENTVALIDATION, account, oldPwd,newPwd);
  		
  		postInfo.logOvject();  //用来测试数据有没有被封装进去
  			
  		//POST,将需要传递的参数封装好的对象传进去
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
				
				Login.sendMessage(text, myHandler);  //将反馈消息发回到UI线程
				
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
    //Params:我们传递给异步任务时的参数类型
	//Progress：我们的异步任务在执行的时候将执行的进度返回给UI线程的参数的类型
	//Result:异步任务执行完后返回给UI线程的结果的类型
	public void setCookie() {
        
		pref=getActivity().getSharedPreferences("myCookies", Context.MODE_PRIVATE);
		editor=pref.edit();
		
		gson=new Gson();
		
		storeInfo.okHttpClient=new OkHttpClient()
				.newBuilder()
				.cookieJar(new CookieJar() {
					
//					private final HashMap<String, List<okhttp3.Cookie>>cookieStore=new HashMap<String, List<Cookie>>();
					@Override
					
					//在服务端给客户端发送Cookie时调用。
					public void saveFromResponse(HttpUrl httpUrl, List<okhttp3.Cookie> list) {
						// TODO Auto-generated method stub
						
						storeInfo.cookieStore.put(httpUrl.host(), list);
					
					}
					
					@Override
					//每当这个client访问到某一个域名时，就会通过此方法获取保存的cookie,并且发送给服务器
					public List<okhttp3.Cookie> loadForRequest(HttpUrl httpUrl) {
						// TODO Auto-generated method stub
					    List<okhttp3.Cookie> cookies_first = storeInfo.cookieStore.get(httpUrl.host());
					    //为什么if-else里的两条语句都会执行


					    //将文件里面的cookies都取出来
					    String cookie=null;

					    cookie=pref.getString("cookies"+(storeInfo.cookieNumber-2), null);
					    
					    //由于Java泛型的实现机制，使用了泛型的代码在运行期间相关的泛型参数的类型都会被擦除
					    //我们无法在运行期间获得泛型参数的具体类型（所有的泛型类型在运行时都是Object类型）
					    //Gson借助TypeToken类来解决这个问题
					    Type listClass = new TypeToken<List<okhttp3.Cookie>>() {}.getType();
					    
					    List<okhttp3.Cookie> cookies_second=gson.fromJson(cookie, listClass);
					    
					    cookies_first=cookies_second;
					    
					    return cookies_first!=null?cookies_first:(new ArrayList<okhttp3.Cookie>());
					}
				})
				.build();
	}
	
	
}
