<%@page import="com.service.AvatarService"%>
<%@page import="com.database.Avatar"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page  import="com.service.StuService"%>
<%@page  import="com.database.Stuinfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    AvatarService service=new AvatarService();
    Avatar[] avatars=service.findAllAvatar();
    Gson gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
    String s=gson.toJson(avatars,Avatar[].class);
    out.print(s);
%>
