package com.example.nkbhpocket2_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.nkbhpocket2_0.R.drawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * 
 * @author ����ݼ
 * ���ܣ���ȡ���ݿ������ѧ����Ϣ
 *
 */
public class Fragment1 extends Fragment {
	
	private TextView stuName;
	
	private TextView stuId;
	
	private TextView stuPwd;
	
	private TextView stuStatus;
	
	public static TextView stuImg;     //��ͷ�������õ���¼�ʵ������ѡ��ͷ��Ĺ���  
	
	private Bundle bundle; 
	
	public static int avatarId=0;
	
	private ChooseAvaFragment avaFragment;

	private FragmentManager fragmentManager;
	
	public static String stuID=null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		avaFragment=new ChooseAvaFragment();
		
		View v = inflater.inflate(R.layout.fragment_one, container,false);
		
		initCmp(v);
		
		bundle=getArguments();
		stuID=bundle.getString("stuID");
		Log.d("stuID", stuID);                               //��ȡ��ǰѧ����ID��
		
		findStuById(stuID);
		
		stuImg.setOnClickListener(new OnClickListener() {    //�����ťʱ��chooseMenu.xml��ʾͷ��ĵ�ѡ��
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addFragment(avaFragment);
				
			}
		});
		
		return v;
	}
	
	private void addFragment(Fragment fragment) {
		
		fragmentManager=getFragmentManager();
		
		FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
		
		fragmentTransaction.add(R.id.fragment_container, fragment);
//		fragmentTransaction.replace(R.id.fragment_container, fragment);
		
		fragmentTransaction.commit();
	
	}
	
	/**
	 * �ӵ�ǰѧ����ID���ҵ�ѧ������ѧ���������ҵ���ǰ��¼��ѧ����Ϣ
	 * Ȼ��ӵ�ǰѧ�������ҵ���Ӧ��imgID
	 * �ҵ���Ӧ��imgID�Ժ���ͷ��������ҵ���ӦID��ͼƬ����
	 * ͨ�����䣬����ͼƬ�����ҵ���R�ļ������ID��Ȼ�����ý�TextView����
	 * @param stuID
	 */
	public void findStuById(final String stuID) {
	
		AsyncTask<Void, Avatar, Stuinfo> findStu=new AsyncTask<Void, Avatar, Stuinfo>() {
			
			Gson gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
			@Override
			protected Stuinfo doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					//--------------------��ȡStudent����Ϣ-----------------------------------------------
					URL url=new URL("http://192.168.1.109:8080/ChatBinhai/findStudentById.jsp?stuID="+stuID);
					BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream()));
					StringBuffer buf=new StringBuffer();
					String line;
					while((line=reader.readLine())!=null) {
						buf.append(line);
					}
					String json=buf.toString();
					
//					System.out.println(json);
					
					Stuinfo student=gson.fromJson(json, Stuinfo.class);
					
//					Log.d("student", student.toString());
					
					//--------------------------��ȡavatar,ֱ�Ӵ����ݿ�����ȡ---------------------------------------
					Integer imgId = student.getStuImg();
					URL imgUrl=new URL("http://192.168.1.109:8080/ChatBinhai/findAvatarById.jsp?avaID="+imgId);
					BufferedReader avaReader=new BufferedReader(new InputStreamReader(imgUrl.openStream()));
					StringBuffer avabuf=new StringBuffer();
					String avaline;
					while((avaline=avaReader.readLine())!=null) {
						avabuf.append(avaline);
					}
					String avajson=avabuf.toString();
					Avatar avatar=gson.fromJson(avajson, Avatar.class);
					publishProgress(avatar);
					//-----------------------------------------------------------------------------------
					return student;
		                
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}
			

			/**
			 * ʵ��ͷ��Ļ�ȡ��֮��Ҫʵ��ͷ���ѡ����
			 */
			@Override
			protected void onProgressUpdate(Avatar... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			    String imgName=values[0].getImgName();
			    
			    Class<drawable> drawable = R.drawable.class;
			    Field field=null;
                try {
					field=drawable.getField(imgName);
				    avatarId=field.getInt(field.getName());
				    stuImg.setBackgroundResource(avatarId);
				    
                } catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			    
			}
			
			@Override
			protected void onPostExecute(Stuinfo student) {
				// TODO Auto-generated method stub
				super.onPostExecute(student);
				
				stuName.setText(student.getStuName());
				stuId.setText(student.getStuId());
				stuPwd.setText(student.getStuPwd());
				stuStatus.setText(student.getStuStatus());
				
			}
		};
		
		findStu.execute();
	}
	
	public void initCmp(View v) {
		
		stuName=(TextView) v.findViewById(R.id.infoname_content_tv);
		stuId=(TextView) v.findViewById(R.id.infoid_content_tv);
		stuPwd=(TextView) v.findViewById(R.id.infopwd_content_tv);
		stuStatus=(TextView) v.findViewById(R.id.infostatus_content_tv);
		stuImg=(TextView) v.findViewById(R.id.stuImg);
		
	}
}
