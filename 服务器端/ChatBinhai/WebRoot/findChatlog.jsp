<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.database.Chatlog"%>
<%@page import="com.service.ChatLogService"%>
<%@page import="com.google.gson.Gson"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

    //http://xxxx/findChatlog.jsp?senderID=xxx&reveiverID=xxx
    String senderID=request.getParameter("senderID");
    String receiverID=request.getParameter("receiverID");
    
    ChatLogService service=new ChatLogService();
    Chatlog[] log=service.findChatLogByUerId(senderID, receiverID);
    Gson gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
     
    String logg=gson.toJson(log,Chatlog[].class);
    out.print(logg); 
%>
