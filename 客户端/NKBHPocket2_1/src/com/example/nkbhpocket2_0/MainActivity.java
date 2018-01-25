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
 * @author ����ݼ
 * 
 * �������ù㲥�ķ�ʽʵ��ʵʱ��������仯�Ĺ���
 * 
 * ˼����Ҫ��Ҫʵ��ֻ����һ�ι㲥�Ĺ���
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
    			//---------------Mobile Dataָ������û��WiFi���ȵ������£��õ�SIM����������----------------------------------

        		//getSystemService()�õ���ConnectivityManager��ʵ��������һ��ϵͳ�����࣬ר�����ڹ����������ӵ�
        		ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	
        		//�õ�ʵ��
    		    NetworkInfo wifiInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

    		    NetworkInfo mobileInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    		    
    		    //����Wi-Fi�Ƿ����
    		    if(wifiInfo!=null&&wifiInfo.isAvailable()) {
    		    	
    		    	if(wifiInfo.isConnected()) {
    		    		Toast.makeText(context, "Wi-Fi is Connective", Toast.LENGTH_SHORT).show();
    		    	}else {
    		    		Toast.makeText(context, "Wi-Fi is NOT Connective", Toast.LENGTH_SHORT).show();
    		    	}
    		    }else {
    		    	Toast.makeText(context, "Wi-Fi can not be used", Toast.LENGTH_SHORT).show();
    		    }
    		    
    		    //�����ƶ����������Ƿ����
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
