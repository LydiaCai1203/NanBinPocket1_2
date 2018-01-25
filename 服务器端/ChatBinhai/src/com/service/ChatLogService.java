package com.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.transaction.SystemException;

import org.hibernate.Query;
import org.hibernate.Transaction;

import com.database.Chatlog;
import com.database.ChatlogDAO;
import com.database.HibernateSessionFactory;

public class ChatLogService {

	public Chatlog[] findChatLogByUerId(String senderID,String receiverID){
		
		ChatlogDAO chatlogDao=new ChatlogDAO();
		
		//creatQuery()里面用的是Hql语句查询
		//这里用的是动态查询绑定参数的方式
		//这里的Chatlog是类名，还是表名。但是表名是chatlog,试过了是错误的，所以这里应该是类名
		Query query = chatlogDao.getSession().createQuery("from Chatlog where (senderID=:uid1 and receiverID=:uid2) or (senderID=:uid2 and receiverID=:uid1)");
		
		query.setString("uid1", senderID);
		
		query.setString("uid2", receiverID);
		
		List<Chatlog> list=query.list();
		
		HibernateSessionFactory.closeSession();
		
		return list.toArray(new Chatlog[0]);
	}
	
	/*
	 * 不能每次刷新都重新清空list，再进行拉取，这样的话随着发的记录越来越多，速度就会越来越慢
	 * 所以只要在当前页面，就只拉取最新的聊天记录填充进list当中
	 */
	public Chatlog[] findNewChatlog(int curQuantity,String senderID,String receiverID){
		
		ChatlogDAO chatlogDao=new ChatlogDAO();
		//注意where语句里面会不会有短路情况出现
		Query query=chatlogDao.getSession()
				     .createQuery("from Chatlog "
				     		+ "where ((senderID=:uid1 and receiverID=:uid2)"
				     		+ " or (senderID=:uid2 and receiverID=:uid1)) "
				     		+ "and (logID>:max)");
		
        query.setString("uid1", senderID);
		
		query.setString("uid2", receiverID);
		
		query.setInteger("max", curQuantity);
		
		List<Chatlog> list=query.list();
		
		HibernateSessionFactory.closeSession();
		
		return list.toArray(new Chatlog[0]);
		
	}
	
	
	public String addChatLog(String senderID,String receiverID,String content) throws Exception{
		
		ChatlogDAO logDao=new ChatlogDAO();
		
		Transaction ts = logDao.getSession().beginTransaction();
		
		try{
			
			Date date=new Date();
			
			Chatlog log=new Chatlog(senderID, receiverID, content, new Timestamp(date.getTime()));
			
			logDao.save(log);
			
			ts.commit();
			
			return "success";
		
		}catch(Exception e){
			
			e.printStackTrace();
			
			ts.rollback();
            
			return e.toString();
	
		}finally{
			
			HibernateSessionFactory.closeSession();
		}
		
	}
}
