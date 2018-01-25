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
 * @author ����ݼ 12/12
 *		AsyncTask<Params, Progress, Result>
	    Params:���Ǵ��ݸ��첽����ʱ�Ĳ�������
		Progress�����ǵ��첽������ִ�е�ʱ��ִ�еĽ��ȷ��ظ�UI�̵߳Ĳ���������
		Result:�첽����ִ����󷵻ظ�UI�̵߳Ľ��������

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

