package com.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.database.HibernateSessionFactory;
import com.database.Stuinfo;
import com.database.StuinfoDAO;

public class StuService {

	
	public StuService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Stuinfo[] findAll(){
		
		StuinfoDAO stuDao=new StuinfoDAO();
		List<Stuinfo> list=stuDao.findAll();
		HibernateSessionFactory.closeSession();
		return list.toArray(new Stuinfo[0]);
	}
	
	public Stuinfo[] findByName(String name){
		
		StuinfoDAO stuDao=new StuinfoDAO();
		List<Stuinfo> list=stuDao.findByStuName(name);
		HibernateSessionFactory.closeSession();
		Stuinfo[] stus=list.toArray(new Stuinfo[0]);
		return stus;
	}
	
	public Stuinfo findById(String id){
		
		StuinfoDAO stuDao=new StuinfoDAO();
		Stuinfo stu=stuDao.findById(id);
		HibernateSessionFactory.closeSession();
		return stu;
	}
	
	public String addStudent(String id,String name,String pwd,int imgId){
		
		StuinfoDAO stuDao=new StuinfoDAO();
		
		Transaction ts=stuDao.getSession().beginTransaction();
		
		try{
		
			Stuinfo stu=new Stuinfo(id, name, pwd, imgId);
			
			stuDao.save(stu);
			
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
	
	public String updataStudent(String stuID,int imgID){
		
		try{
			
			Session session=HibernateSessionFactory.getSession();
			
			Transaction ts=session.beginTransaction();
			
			Stuinfo student=(Stuinfo) session.get(Stuinfo.class, stuID);
			
			student.setStuImg(imgID);
			
			session.update(student);
			
			ts.commit();
			
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "failed";
		}
		
	}
	
}
