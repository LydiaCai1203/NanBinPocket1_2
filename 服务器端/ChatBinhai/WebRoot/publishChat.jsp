<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.database.Chatlog"%>
<%@page import="com.service.ChatLogService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

    //http://xxxx/publishChat.jsp?senderID=xxx&receiverID=xxx&content=xxxx
    
    String senderID=request.getParameter("senderID");
    String receiverID=request.getParameter("receiverID");
    String content=request.getParameter("content");
    
    ChatLogService service=new ChatLogService();
    String log=service.addChatLog(senderID, receiverID, content);
    
    out.print(log);
%>
