package com.example.nkbhpocket2_0;

import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;

public class storeInfo {

	public static String baseUrl="http://222.30.63.15";
	
	public static OkHttpClient okHttpClient ;
	
	public static int cookieNumber=0;
	//�������ȫ�ֱ���û�취��֤���е�cookie����Ч��
	//���룺�п��ܻ���һ��Activity���൱���ǹر���Browser�������һ��cookie�Ͳ�����
	public static final HashMap<String, List<okhttp3.Cookie>> cookieStore=new HashMap<String, List<okhttp3.Cookie>>();
	
}
