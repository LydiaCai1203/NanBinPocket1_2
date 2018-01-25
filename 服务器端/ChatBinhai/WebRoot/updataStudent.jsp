<%@page import="com.service.StuService"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.database.Chatlog"%>
<%@page import="com.service.ChatLogService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

    //http://xxxx/updataStudent.jsp?stuID=xxxx&imgID=xxxx
    
    String stuID=request.getParameter("stuID");
    String imgID=request.getParameter("imgID");
    
    int imgId=Integer.parseInt(imgID);
    
    StuService service=new StuService();
    
    String log=service.updataStudent(stuID, imgId);
    
    out.print(log);
%>
