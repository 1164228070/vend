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
        <a href="#"><i class="fa fa-circle text-success"></i>在线</a>
      </div>
    </div>
    <!-- search form -->
    <form action="#" method="get" id="navigation_form" class="sidebar-form">
      <div class="input-group">
        <input type="text" name="query" class="form-control" placeholder="搜索...">
        <span class="input-group-btn">
          <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
        </span>
      </div>
    </form>
    
    
    <!-- /.search form -->
    <!-- sidebar menu: : style can be found in sidebar.less -->
    <ul class="sidebar-menu" data-widget="tree">
      <li class="header">主 菜 单</li>
      
      <c:forEach items="${applicationScope.levelMenus}" var="topMenu">
        <c:if test="${sessionScope.user.hasMenuByName(topMenu.name)}">
          <li class="treeview">
            <a href="#">
              <i class="fa ${topMenu.icon}"></i> <span>${topMenu.name}</span>
              <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>   
              </span>
            </a>
            <ul class="treeview-menu">
              <c:forEach items="${topMenu.children}" var="secondMenu">
                <c:if test="${sessionScope.user.hasMenuByName(secondMenu.name)}">
                  <li><a href="${pageContext.request.contextPath}${secondMenu.URL}"><i class="fa ${secondMenu.icon}"></i>${secondMenu.name}</a></li>
                </c:if>
              </c:forEach>
            </ul>
          </li>
        </c:if>
        
      </c:forEach>
      <%--<li class="header">常用工具</li>
      <li><a href="${pageContext.request.contextPath}/works"><i class="fa fa-circle-o text-red"></i> <span>工作台</span></a></li>
      <li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>紧急事件</span></a></li>--%>
    </ul>
  </section>
  <!-- /.sidebar -->
</aside>

  <script type="text/javascript">
      $(function(){
    	  //监听
    	  $("#navigation_form").on("submit",function(e){
    		  e.preventDefault();  
       		  //查找菜单
    		  var value = $(this).find("input[name='query']").val();
    		  if(value){
    			  //查找超链接文本
    			   $.each($("ul.sidebar-menu li>a"),function(index,ele){
    				   if($(ele).text().trim().match(value)){
    					   $("li.treeview").filter(".menu_open").removeClass("menu_open");
    					   $("li.treeview").filter(".active").removeClass("active");
    					    
    					   $(ele).closest("li.treeview").addClass("menu_open");  
    					   $(ele).closest("li.treeview").addClass("active"); 
    					   
    					   $("ul.treeview-menu >li.active").removeClass("active"); 
    					   
    					   $(ele).parent().addClass("active");
    				   }
    			  }); 
    	      }
    	  });
      });
  </script>
