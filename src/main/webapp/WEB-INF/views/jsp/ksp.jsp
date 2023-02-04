<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 1/30/2023
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>HuyBlue</title>

  <style><%@include file="/WEB-INF/css/RegisterKSP.css"%></style>
</head>
<body>
<form action="" >
  <div class="container">
    <h1>Đăng kí thành viên KSP</h1>
    <p>Xin hãy nhập biểu mẫu bên dưới để đăng ký.</p>
    <hr>
    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Nhập Email" name="email" required>
    <label for="psw"><b>Mật Khẩu</b></label>
    <input type="password" placeholder="Nhập Mật Khẩu" name="password" required>
    <label for="psw-repeat"><b>Nhập Lại Mật Khẩu</b></label>
    <input type="password" placeholder="Nhập Lại Mật Khẩu" name="psw-repeat" required>
    <label>
      <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Nhớ Đăng Nhập
    </label>
    <div class="clearfix">
      <button type="submit" class="signupbtn">Sign Up</button>
    </div>
  </div>
</form>
<img src="<%=request.getContextPath()%>/resources/images/anhtest.jsp" />

</body>
</html>
