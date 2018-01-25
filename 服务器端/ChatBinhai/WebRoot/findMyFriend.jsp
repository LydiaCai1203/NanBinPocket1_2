<%@page import="com.database.Friend"%>
<%@page import="com.service.FriendService"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page  import="com.database.Stuinfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    // http://xxxxx/ChatBinhai/findMyFriend.jsp?myNumber=xxxx
    String myNumber=request.getParameter("myNumber");
    FriendService service=new FriendService();
    Friend[] friends=service.findAllFriends(myNumber);
    Gson gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
    String s=gson.toJson(friends,Friend[].class);
    out.print(s);
%>
