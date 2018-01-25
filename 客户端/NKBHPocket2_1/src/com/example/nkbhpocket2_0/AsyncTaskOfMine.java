package com.example.nkbhpocket2_0;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 
 * @author ����ݼ
 * ���ﻹ��һ����Ҫ���Ƶĵط�������Ҫ�ټ�һ������������Ϊ��ʾ��ʱ��ʵ����̫����
 *
 */
public class AsyncTaskOfMine extends AsyncTask<String,Void, Element > {
	//---------------------------ȫ�����ǵ�һ�е����ݡ���--------------------------------

	private TextView courseName;
	
	private TextView startTerm;
			
	private TextView courseType;
	
	private TextView credit;
	
	private TextView scoreOne;
	
	private TextView scoreTwo;
	
	private TextView finalScore;
	
	private ProgressBar score_pgb;
	//-------------------------------------------------------------
	
	private String url;
	
	private List<StuScore> scoreList;
	
	private StuScore itemScore;
	
	private ScoreAdapter sa;
	
	private ListView scoreView;
	
    private SharedPreferences pref;
	
	private Gson gson;
	
	private String temporaryPage;

	public AsyncTaskOfMine(String url) {
		super();
		this.url=url;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		courseName=(TextView) (Fragment4.v).findViewById(R.id.course_name_tv);
		
		startTerm=(TextView) (Fragment4.v).findViewById(R.id.start_term_tv);
				
		courseType=(TextView) (Fragment4.v).findViewById(R.id.coruse_type_tv);
		
		credit=(TextView) (Fragment4.v).findViewById(R.id.credit_tv);
		
		scoreOne=(TextView) (Fragment4.v).findViewById(R.id.score_one_tv);   //ƽʱ�ɼ�
		
		scoreTwo=(TextView) (Fragment4.v).findViewById(R.id.score_two_tv);   //��ĩ�ɼ�
		
		finalScore=(TextView) (Fragment4.v).findViewById(R.id.final_score_tv); //�����ɼ�
		
		scoreList=new ArrayList<StuScore>();
		
		scoreView=(ListView) (Fragment4.v).findViewById(R.id.score_container);
		
		score_pgb=(ProgressBar) (Fragment4.v).findViewById(R.id.score_porgbar);
	}
	
	@Override
	protected Element doInBackground(String... params) {
		// TODO Auto-generated method stub
		setCookie();
		
		Element scoreTable=null;
		try {
			getHtml(url);
			String scoreUrl=getScoreUrl();
			getHtml(scoreUrl);           //ע����һ��ִ�����Ժ�temporaryPage��ֵ�ٴη����˸ı�
			scoreTable=getScoreTable();
			
			fillScoreList(scoreTable);   //��������������ȫ�����list����
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return scoreTable;   
	}
	

	//��Ҫ����ʾҳ���ȫ������������onPostExecute���У�֮��ѳ���ʵ����publishProgress����
	@Override
	protected void onPostExecute(Element scoreTable) {
		// TODO Auto-generated method stub
		super.onPostExecute(scoreTable);
		
		//����Ҫ���Ĺ���ֻ��һ�����ǲ������itemScore��Ȼ��ȫ����䵽list����
		getItemScore(scoreTable);
		
		score_pgb.setVisibility(View.GONE);
		sa=new ScoreAdapter(Fragment4.chooseMenu, R.layout.socre_sub_layout,scoreList);
		scoreView.setAdapter(sa);     
	}
	
	
	
	
	private StuScore getItemScore(Element scoreTable) {
		
		StuScore item=new StuScore();
		
		Elements tr=scoreTable.select("tr");
		fillFirstRow(tr.first());
//		fillScoreList(scoreTable);
		
		return item;
		
	}
	
	private void fillScoreList(Element scoreTable) {
		// TODO Auto-generated method stub
		
		int i=0;
		for(Element tr:scoreTable.select("tr"))   //ѭ������list�����Ӧ���ж�����
		{
			if(i==0) {    //��Ϊ��һ���ǲ�Ҫ��
				i++;
				continue;
			}else {  
				itemScore=new StuScore();
				Elements tds=tr.select("td");
				itemScore.setCourseName(tds.first().text());
				itemScore.setStartTerm(tds.get(4).text());
				itemScore.setCourseType(tds.get(5).text());
				itemScore.setCredit(tds.get(6).text());
				itemScore.setSocreOne(tds.get(7).text());
				itemScore.setScoreTwo(tds.get(9).text());
				itemScore.setFinalScore(tds.get(10).text());
				scoreList.add(itemScore);
			}
		}
		
	}

	private void fillFirstRow(Element row) {
		
		Elements tds=row.select("td");
		courseName.setText(tds.first().text());
		startTerm.setText(tds.get(4).text());
		courseType.setText(tds.get(5).text());
		credit.setText(tds.get(6).text());
		scoreOne.setText(tds.get(7).text());
		scoreTwo.setText(tds.get(9).text());
		finalScore.setText(tds.get(10).text());
		
	}
	
	//ֻ�����ScoreTable�Ǹ������ȡ����
	private Element getScoreTable() {
		
		Element scoreTable=null;
		
		Document doc=Jsoup.parse(temporaryPage);
		
		Elements tables=doc.select("table");
		
		scoreTable=tables.get(7);
		
		return scoreTable;
	}
	
	private String getScoreUrl() throws Exception{
		
//		Document doc=Jsoup.parse(stuUrl, 3*1000);
		
		Document doc=Jsoup.parse(temporaryPage);
		
		Elements tables=doc.select("table");
		
	    Element table=tables.get(7); //�ɼ���ѯ �ڵ���������� 
		
	    Element tr=table.select("tr").first();
	    
	    Element td=tr.select("td").get(6);
	    
	    Elements links=td.select("a");
		 
	    //���ڴ��ھ���URL�����URL������ȡ�����Ժ�Ҫ�����ж���û��"../"�������ַ��Ӵ�
	    //���Ҫ����֮ǰ��URL�����о���URL��ת��
	    
	    String linkHref="http://222.30.63.15/nkemis/Student/"+links.first().attr("href");  
	    
		return linkHref;
		
	}

	private void getHtml(String myUrl) {
		// TODO Auto-generated method stub
	
		final Request request=new Request.Builder()
					.url(myUrl)
					.addHeader("content-type", "application/x-www-form-urlencoded")
				    .addHeader("host", "222.30.63.15")
					.build();

				Call call = storeInfo.okHttpClient.newCall(request);
				
				call.enqueue(new okhttp3.Callback() {
					
					@Override
					public void onResponse(Call call, Response response) throws IOException {
						// TODO Auto-generated method stub
						String res=response.body().string();
						Log.d("PageIGet", res);
						temporaryPage=res;
					}
				
					
					@Override
					public void onFailure(Call call, IOException e) {
						// TODO Auto-generated method stub
						Log.e("onFailer", e.getMessage());
						e.printStackTrace();
					}
				});	
				
				/*
				 * ��AsyncTask��okHttp3���첽�������һ����õ�ʱ��ǧ��ǧ��
				 * Ҫע��������һ����ӣ�������������������������������������
				 * 
				 * �첽���������¿�����һ���̣߳����������������ڻ�û�еõ�
				 * ��Ӧ��ʱ��doInBackground����߳̾��Ѿ�ִ����ϣ����Ի����
				 * callback�����������õ����⣡
				 * 
				 * ��дoveride callback()�Ƿ��ܽ�����⣿
				 */
				
				try {
					Thread.sleep(3*1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
	}
	
	
	
	public void setCookie() {
        
		pref=(Fragment4.chooseMenu).getSharedPreferences("myCookies", Context.MODE_PRIVATE);
		
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
