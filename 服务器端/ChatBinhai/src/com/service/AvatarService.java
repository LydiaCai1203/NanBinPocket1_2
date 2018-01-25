package com.service;
import java.util.List;

import com.database.Avatar;
import com.database.AvatarDAO;
import com.database.HibernateSessionFactory;

public class AvatarService {

	public Avatar findAvatarById(Integer id) {
		
		AvatarDAO avaDao=new AvatarDAO();
		
		Avatar avatar=avaDao.findById(id);
		
		HibernateSessionFactory.closeSession();
		
		return avatar;
		
	}
	
	public Avatar[] findAllAvatar(){
		
		AvatarDAO avaDao=new AvatarDAO();
		
		List<Avatar> list=avaDao.findAll();
		
		Avatar[] avatars=list.toArray(new Avatar[0]);
		
		HibernateSessionFactory.closeSession();
		
		return avatars;
	}
}
