<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
  <!-- sidebar: style can be found in sidebar.less -->
  <section class="sidebar">
    <!-- Sidebar user panel -->
    <div class="user-panel">
      <div class="pull-left image">
        <img src="${pageContext.request.contextPath}/static/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
      </div>
      <div class="pull-left info">
        <p>${sessionScope.user.loginName}</p>
        <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
      </div>
    </div>
    <!-- search form -->
    <form action="#" method="get" class="sidebar-form">
      <div class="input-group">
        <input type="text" name="q" class="form-control" placeholder="Search...">
            <span class="input-group-btn">
              <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
              </button>
            </span>
      </div>
    </form>
    <!-- /.search form -->
    <!-- sidebar menu: : style can be found in sidebar.less -->
    <ul class="sidebar-menu" data-widget="tree">
      <li class="header">主 菜 单</li>
      
      <c:forEach items="${applicationScope.levelMenus}" var="topMenu">
        <li class="treeview">
          <a href="#">
            <i class="fa fa-dashboard"></i> <span>${topMenu.name}</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>   
            </span>
          </a>
          <ul class="treeview-menu">
            <c:forEach items="${topMenu.children}" var="secondMenu">
              <li><a href="${pageContext.request.contextPath}/${secondMenu.URL}"><i class="fa fa-circle-o"></i>${secondMenu.name}</a></li>
            </c:forEach>
          </ul>
        </li>
      </c:forEach>


    </ul>
  </section>
  <!-- /.sidebar -->
</aside>