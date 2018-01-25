<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.database.Chatlog"%>
<%@page import="com.service.ChatLogService"%>
<%@page import="com.google.gson.Gson"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

    //http://xxxx/findNewChatlog.jsp?senderID=xxx&reveiverID=xxx&curQuantity=xxx
    String senderID=request.getParameter("senderID");
    String receiverID=request.getParameter("receiverID");
    String max=request.getParameter("curQuantity");
    
    int curQuantity=Integer.parseInt(max);
    
    ChatLogService service=new ChatLogService();
    Chatlog[] log=service.findNewChatlog(curQuantity, senderID, receiverID);
    Gson gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
     
    String logg=gson.toJson(log,Chatlog[].class);
    out.print(logg); 
%>
