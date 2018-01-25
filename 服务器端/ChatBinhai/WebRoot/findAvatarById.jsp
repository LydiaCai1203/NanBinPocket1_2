<%@page import="com.service.AvatarService"%>
<%@page import="com.database.Avatar"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page  import="com.service.StuService"%>
<%@page  import="com.database.Stuinfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

    //http://xxxx/findAvatarById.jsp?avaID=2
    String idString = request.getParameter("avaID");
    Integer id=Integer.parseInt(idString);
    AvatarService service=new AvatarService();
    Avatar avatar=service.findAvatarById(id);
    Gson gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
    String s=gson.toJson(avatar,Avatar.class);
    out.print(s);
%>
