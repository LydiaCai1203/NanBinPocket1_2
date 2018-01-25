package com.example.nkbhpocket2_0;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * 
 * @author 蔡箐菁 12/12
 *		AsyncTask<Params, Progress, Result>
	    Params:我们传递给异步任务时的参数类型
		Progress：我们的异步任务在执行的时候将执行的进度返回给UI线程的参数的类型
		Result:异步任务执行完后返回给UI线程的结果的类型

 */
public class Fragment4 extends Fragment{
	
    private String stuDefau=null;
    
    private Bundle bundle;
    
    public static Activity chooseMenu;
    
    public static View v;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fargment_four, container,false);
		
		chooseMenu=getActivity();
		bundle=getArguments();
		stuDefau=bundle.getString("stuDefau");
		
		AsyncTaskOfMine myAsynctack=new AsyncTaskOfMine(stuDefau);
		myAsynctack.execute();

		return v;
	}
	

}

