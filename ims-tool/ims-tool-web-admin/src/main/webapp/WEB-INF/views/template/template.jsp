<!DOCTYPE html>
<%@ include file="/WEB-INF/views/template/includes.jsp" %>

<tiles:importAttribute name="javascripts"/>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
<!--     <link rel="icon" href="../../favicon.ico"> -->

    <title><tiles:insertAttribute name="title" /></title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/static/css/bootstrap.min.css" />" rel="stylesheet" />
    
    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/static/css/bootstrap-editable.css" />" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="<c:url value="/static/css/ivr-admin.css" />" rel="stylesheet" />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="container-fluid">

      <tiles:insertAttribute name="navigation_bar" />
      
      <tiles:insertAttribute name="breadcrumb" />
      
      <tiles:insertAttribute name="errorsMessage" />     	
	  <tiles:insertAttribute name="successMessage" />
	  <tiles:insertAttribute name="infoMessage" />

      <tiles:insertAttribute name="content" />

    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<c:url value="/static/js/jquery.min.js" />"></script>
    <script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="<c:url value="/static/js/ie10-viewport-bug-workaround.js" />"></script>
    <script src="<c:url value="/static/js/bootstrap.touchspin.js" />"></script>
    <script src="<c:url value="/static/js/bootstrap-editable.min.js" />"></script>
	<script src="<c:url value="/static/js/moment.js" />"></script>
    
    <!-- scripts -->
    <c:forEach var="script" items="${javascripts}">
        <script src="<c:url value="${script}"/>"></script>
    </c:forEach>
    <!-- end scripts -->
    <script type="text/javascript">var ctxPath = "${pageContext.request.contextPath}";</script>
  </body>
</html>