<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
</head>
<body>
<%-- <div>${userInfo}</div> --%>
<div>用户session已经获取到，可以作跳转</div>
<div>用户昵称：${member.name}</div>
<div>用户头像：<img width="100" height="100" alt="用户头像" src="${member.headimgurl}"></img></div>
</body>
</html>