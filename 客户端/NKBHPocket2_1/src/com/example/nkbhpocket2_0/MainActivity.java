package com.example.nkbhpocket2_0;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * @author 蔡箐菁
 * 
 * 这里我用广播的方式实现实时监听网络变化的功能
 * 
 * 思考：要不要实现只接收一次广播的功能
 *
 */
public class MainActivity extends Activity {

	private IntentFilter intentFilter;
	
	private NetworkChangeReceiver networkChangeReceiver;
	
	public ImageView backWord;
	
	public static boolean flag=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        intentFilter=new IntentFilter();
		
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		
		networkChangeReceiver=new NetworkChangeReceiver();
		
		registerReceiver(networkChangeReceiver, intentFilter);
		
		//--------------------------------------------------------------------------------

		backWord=(ImageView) findViewById(R.id.startup_word_imageView);
		
		ObjectAnimator alphaWord=ObjectAnimator.ofFloat(backWord, "alpha", 0f,0.8f);
	    ObjectAnimator rotationWord=ObjectAnimator.ofFloat(backWord, "rotation", 0f,360f);
		
	    AnimatorSet animSet=new AnimatorSet();
//		animSet.play(rotationWord).with(alphaWord);
	    animSet.play(alphaWord);
		animSet.setDuration(5000);
		animSet.start();
		
		ActionBar actionBar=getActionBar();
		actionBar.hide();
		
		backWord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this, Login.class);
				startActivity(intent);
				finish();
			}
		});
		
		//--------------------------------------------------------------------------------------
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	 
		unregisterReceiver(networkChangeReceiver);
	}
	

    class NetworkChangeReceiver extends BroadcastReceiver{
	
    	@Override
	    public void onReceive(Context context, Intent intent) {
    		// TODO Auto-generated method stub
    		
    		if(flag==true) {
    			//---------------Mobile Data指的是再没有WiFi和热点的情况下，用的SIM卡上网流量----------------------------------

        		//getSystemService()得到了ConnectivityManager的实例，这是一个系统服务类，专门用于管理网络连接的
        		ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	
        		//得到实例
    		    NetworkInfo wifiInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

    		    NetworkInfo mobileInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    		    
    		    //测试Wi-Fi是否可用
    		    if(wifiInfo!=null&&wifiInfo.isAvailable()) {
    		    	
    		    	if(wifiInfo.isConnected()) {
    		    		Toast.makeText(context, "Wi-Fi is Connective", Toast.LENGTH_SHORT).show();
    		    	}else {
    		    		Toast.makeText(context, "Wi-Fi is NOT Connective", Toast.LENGTH_SHORT).show();
    		    	}
    		    }else {
    		    	Toast.makeText(context, "Wi-Fi can not be used", Toast.LENGTH_SHORT).show();
    		    }
    		    
    		    //测试移动数据连接是否可用
    		    if(mobileInfo!=null&&mobileInfo.isAvailable()) {
    		    	
    		    	if(mobileInfo.isConnected()) {
    		    		Toast.makeText(context, "Mobile Data is Connective", Toast.LENGTH_SHORT).show();
    		    	}else {
    		    		Toast.makeText(context, "Mobile Data is NOT Connective", Toast.LENGTH_SHORT).show();
    		    	}
    		    }else {
    		    	Toast.makeText(context, "Mobile Data can not be used", Toast.LENGTH_SHORT).show();
    		    }
    		    
    		    flag=false;
    		}
		    
    	}
    	
    }
}
