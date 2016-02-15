<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Diagrama</title>
	<meta charset="utf-8" />
	
	<link href="css/diagram.css" rel="stylesheet" />
	<style>
		.table-uras tr {
			height: 80px;
		}
		
		.table-uras tr a {
			border: 1px #000 solid;
		}
	</style>
</head>
    <body style="cursor:move;" oncontextmenu="return false;">
	
	
	<table style="width: 100%;text-align: center;" class="table-uras">
		<tr>
			<td><a href="Main?form=150727151519483&level=2" class="btn btn-lg btn-outline">URA CRM</a></td>
		</tr>
		<tr>
			<td><a href="Main?form=150727173459683&level=2" class="btn btn-lg btn-outline">URA SAN</a></td>
		</tr>
		<tr>
			<td><a href="Main?form=150728152047814&level=2" class="btn btn-lg btn-outline">URA LOCAÇÔES</a></td>
		</tr>
		<tr>
			<td><a href="Main?form=150525161631021&level=2" class="btn btn-lg btn-outline">URA VONO</a></td>
		</tr>
		<tr>
			<td><a href="Main?form=140911190954357&level=2" class="btn btn-lg btn-outline">URA OUVIDORIA</a></td>
		</tr>
	</table>
	 
	 <script type="text/javascript" src="<c:url value='js/mermaid.full.js'/>"></script>
	 	<script src="js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="<c:url value='js/jquery.mousewheel.js'/>"></script>
    <script type="text/javascript" src="<c:url value='js/diagram.js'/>"></script>
   
</body>
</html>