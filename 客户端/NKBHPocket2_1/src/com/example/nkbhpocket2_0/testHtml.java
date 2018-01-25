package com.example.nkbhpocket2_0;




import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.widget.TextView;

public class testHtml extends Activity{
	
	public static Handler handler;
	
	private TextView manifest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.testhtml_layout);
	  
	    manifest=(TextView) findViewById(R.id.response);
	
        handler=new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				// TODO Auto-generated method stub
				String res=(String) msg.obj;
				manifest.setText(res);
				return true;
			}
		});
	}
	
}
