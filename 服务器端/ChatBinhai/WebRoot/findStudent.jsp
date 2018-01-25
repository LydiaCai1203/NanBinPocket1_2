<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page  import="com.service.StuService"%>
<%@page  import="com.database.Stuinfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    StuService service=new StuService();
    Stuinfo[] stus=service.findAll();
    Gson gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
    String s=gson.toJson(stus,Stuinfo[].class);
    out.print(s);
%>
