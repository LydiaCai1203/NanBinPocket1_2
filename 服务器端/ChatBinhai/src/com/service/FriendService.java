package com.service;

import java.util.List;

import com.database.Friend;
import com.database.FriendDAO;
import com.database.HibernateSessionFactory;

public class FriendService {

	public FriendService(){
		
	}
	
	/**
	 * 找到学号为myNumber的所有朋友
	 * @param myNumber
	 * @return
	 */
	public Friend[] findAllFriends(String myNumber){
		
		FriendDAO fridao=new FriendDAO();
		
		List<Friend> friList=fridao.findByFriendOfWho(myNumber);
		
		Friend[] friends=friList.toArray(new Friend[0]);
		
		HibernateSessionFactory.closeSession();
		
		return friends;
	}
	

}
