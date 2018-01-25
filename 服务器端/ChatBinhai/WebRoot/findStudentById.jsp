<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page  import="com.service.StuService"%>
<%@page  import="com.database.Stuinfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

    //http://xxxx/findStudentById.jsp?id=2
    String idString = request.getParameter("stuID");
    StuService service=new StuService();
    Stuinfo stu=service.findById(idString);
    Gson gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
    String s=gson.toJson(stu,Stuinfo.class);
    out.print(s);
%>
