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
 * @author 蔡箐菁
 * 这里还有一个需要完善的地方就是需要再加一个进度条，因为显示的时候实在是太慢了
 *
 */
public class AsyncTaskOfMine extends AsyncTask<String,Void, Element > {
	//---------------------------全部都是第一行的内容——--------------------------------

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
		
		scoreOne=(TextView) (Fragment4.v).findViewById(R.id.score_one_tv);   //平时成绩
		
		scoreTwo=(TextView) (Fragment4.v).findViewById(R.id.score_two_tv);   //期末成绩
		
		finalScore=(TextView) (Fragment4.v).findViewById(R.id.final_score_tv); //总评成绩
		
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
			getHtml(scoreUrl);           //注意这一步执行完以后temporaryPage的值再次发生了改变
			scoreTable=getScoreTable();
			
			fillScoreList(scoreTable);   //将表格里面的数据全部填进list里面
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return scoreTable;   
	}
	

	//不要把显示页面的全部工作都放在onPostExecute当中，之后把程序实现在publishProgress当中
	@Override
	protected void onPostExecute(Element scoreTable) {
		// TODO Auto-generated method stub
		super.onPostExecute(scoreTable);
		
		//这里要做的工作只有一个就是不断填充itemScore，然后全部填充到list里面
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
		for(Element tr:scoreTable.select("tr"))   //循环结束list里面就应该有东西了
		{
			if(i==0) {    //因为第一行是不要的
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
	
	//只负责把ScoreTable那个表格拉取出来
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
		
	    Element table=tables.get(7); //成绩查询 在第六个表格里 
		
	    Element tr=table.select("tr").first();
	    
	    Element td=tr.select("td").get(6);
	    
	    Elements links=td.select("a");
		 
	    //由于存在绝对URL和相对URL，所以取出来以后要进行判断有没有"../"这样的字符子串
	    //最后还要根据之前的URL来进行绝对URL的转换
	    
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
				 * 当AsyncTask和okHttp3的异步请求放在一起调用的时候千万千万
				 * 要注意这里有一个大坑！！！！！！！！！！！！！！！！！！！
				 * 
				 * 异步调用这里新开起了一个线程，由于是网络请求，在还没有得到
				 * 回应的时候，doInBackground这个线程就已经执行完毕，所以会出现
				 * callback（）不被调用的问题！
				 * 
				 * 重写overide callback()是否能解决问题？
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
