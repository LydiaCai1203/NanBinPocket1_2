package com.example.nkbhpocket2_0;

import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;

public class storeInfo {

	public static String baseUrl="http://222.30.63.15";
	
	public static OkHttpClient okHttpClient ;
	
	public static int cookieNumber=0;
	//光是设成全局变量没办法保证所有的cookie的有效期
	//猜想：有可能换了一个Activity，相当于是关闭了Browser，因此有一个cookie就不见了
	public static final HashMap<String, List<okhttp3.Cookie>> cookieStore=new HashMap<String, List<okhttp3.Cookie>>();
	
}
