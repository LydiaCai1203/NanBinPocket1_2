package com.example.nkbhpocket2_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.nkbhpocket2_0.R.drawable;
import com.google.gson.Gson;
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
import android.widget.Toast;

public class ChooseAvaFragment extends Fragment {

	private TextView avatar1; 
	private TextView avatar2;
	private TextView avatar3;
	private TextView avatar4;
	private TextView avatar5;
	private TextView avatar6;
	private TextView avatar7;
	private TextView avatar8;
	private TextView avatar9;
	private FragmentManager fragmentManager;
	
	public static String imgName;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.avatar_fargment, container, false);
		
		avatar1=(TextView) view.findViewById(R.id.avatar1);
		avatar2=(TextView) view.findViewById(R.id.avatar2);
		avatar3=(TextView) view.findViewById(R.id.avatar3);
		avatar4=(TextView) view.findViewById(R.id.avatar4);
		avatar5=(TextView) view.findViewById(R.id.avatar5);
		avatar6=(TextView) view.findViewById(R.id.avatar6);
		avatar7=(TextView) view.findViewById(R.id.avatar7);
		avatar8=(TextView) view.findViewById(R.id.avatar8);
		avatar9=(TextView) view.findViewById(R.id.avatar9);
		
		setAvatar();
		
		saveAvatar();
		
		return view;
	}
	
	public void setAvatarImg(Avatar avatar,TextView tv) {
		
		String imgName=avatar.getImgName();

		Class<drawable> drawable = R.drawable.class;
		Field field=null;

		try {
			field=drawable.getField(imgName);
		    int avatarId=field.getInt(field.getName());
		    tv.setBackgroundResource(avatarId);
		    tv.setTag(avatar);     //这里需要测试这里的avatar引用的值到底是不是正确的，因为没有动态内存分配过
		    
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
	
	/**
	 * 这里要想把信息存进对应的人里面还需要直到当前学号，进服务器里面查出来然后存进去
	 */
	public void updataStu(final String stuID,final int imgID) {
		
		AsyncTask<Void, Void, Void> task=new AsyncTask<Void, Void, Void>() {
					
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				try {
					//将新选的
					
					URL url=new URL("http://192.168.1.109:8080/ChatBinhai/updataStudent.jsp?stuID="+stuID+"&imgID="+imgID);
					
					BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream()));
					
                    StringBuffer buf=new StringBuffer();
					
					String line;
					
					while((line=reader.readLine())!=null) {
						buf.append(line);
					}
					
					String s=buf.toString();
					
					Log.d("changeImg", s);
					
//					Toast.makeText(Fragment1.choosemenu,s, Toast.LENGTH_SHORT).show();
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
				
			}
			
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
				Class<drawable> drawable = R.drawable.class;
				Field field=null;
	            try {
				     field=drawable.getField(imgName);
					 int avatarId=field.getInt(field.getName());
					 Fragment1.stuImg.setBackgroundResource(avatarId);
					    
	            } catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    } catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}		                                                 //设置好头像以后就关闭当前的fragment	    
				
				deleteFragment(ChooseAvaFragment.this);
			}
			
		};
		
		
		task.execute();
	}
	
	private void deleteFragment(Fragment fragment) {
		
		fragmentManager=getFragmentManager();
		
		FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
		
		fragmentTransaction.remove(fragment);
		
		fragmentTransaction.commit();
	
	}
	
	/**
	 * 当点击资源选择框里的头像时，设置监听事件
	 */
	public void saveAvatar() {
		
        avatar1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Avatar avatar=(Avatar) avatar1.getTag();
				int imgID=avatar.getImgId();
				String stuID=Fragment1.stuID;
				imgName=avatar.getImgName();
				updataStu(stuID, imgID);
			}
		});
		
	    avatar2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Avatar avatar=(Avatar) avatar2.getTag();
				int imgID=avatar.getImgId();
				imgName=avatar.getImgName();
				String stuID=Fragment1.stuID;
				updataStu(stuID, imgID);
			}
		});
		
		avatar3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Avatar avatar=(Avatar) avatar3.getTag();
				int imgID=avatar.getImgId();
				imgName=avatar.getImgName();
				String stuID=Fragment1.stuID;
				updataStu(stuID, imgID);
			}
		});
		
		avatar4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Avatar avatar=(Avatar) avatar4.getTag();
				int imgID=avatar.getImgId();
				imgName=avatar.getImgName();
				String stuID=Fragment1.stuID;
				updataStu(stuID, imgID);
			}
		});
		
		avatar5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Avatar avatar=(Avatar) avatar5.getTag();
				int imgID=avatar.getImgId();
				imgName=avatar.getImgName();
				String stuID=Fragment1.stuID;
				updataStu(stuID, imgID);
			}
		});
		
		avatar6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Avatar avatar=(Avatar) avatar6.getTag();
				int imgID=avatar.getImgId();
				imgName=avatar.getImgName();
				String stuID=Fragment1.stuID;
				updataStu(stuID, imgID);
			}
		});
		
		avatar7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Avatar avatar=(Avatar) avatar7.getTag();
				int imgID=avatar.getImgId();
				imgName=avatar.getImgName();
				String stuID=Fragment1.stuID;
				updataStu(stuID, imgID);
			}
		});
		
		avatar8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Avatar avatar=(Avatar) avatar8.getTag();
				int imgID=avatar.getImgId();
				imgName=avatar.getImgName();
				String stuID=Fragment1.stuID;
				updataStu(stuID, imgID);
			}
		});
		
		avatar9.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Avatar avatar=(Avatar) avatar9.getTag();
				int imgID=avatar.getImgId();
				imgName=avatar.getImgName();
				String stuID=Fragment1.stuID;
				updataStu(stuID, imgID);
			}
		});
		
	}
	
	/*
	 * 找到所有数据库里存的头像信息，显示出来
	 */
	public void setAvatar() {
		
		AsyncTask<Void, Avatar, Void> setAvatar=new AsyncTask<Void, Avatar, Void>() {
			
			Gson gson=new Gson();
			
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				try {
					
					URL url=new URL("http://192.168.1.109:8080/ChatBinhai/findAvatar.jsp");
					
					BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream()));
					
					StringBuffer buf=new StringBuffer();
					
					String line;
					
					while((line=reader.readLine())!=null) {
						buf.append(line);
					}
					
					String s=buf.toString();
					
					Avatar[] avatars=gson.fromJson(s, Avatar[].class);

					publishProgress(avatars);
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}
			
			@Override
			protected void onProgressUpdate(Avatar... avatars) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(avatars);
				setAvatarImg(avatars[0],avatar1);
				setAvatarImg(avatars[1],avatar2);
				setAvatarImg(avatars[2],avatar3);
				setAvatarImg(avatars[3],avatar4);
				setAvatarImg(avatars[4],avatar5);
				setAvatarImg(avatars[5],avatar6);
				setAvatarImg(avatars[6],avatar7);
				setAvatarImg(avatars[7],avatar8);
				setAvatarImg(avatars[8],avatar9);
				
			}
			
		};
		
		setAvatar.execute();
	}

}
