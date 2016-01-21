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
	<script>
		window.onload = function ()
		{			
			try{
				$(parent.window.document).find('#forms').val('<%= request.getAttribute("formId") %>');
			} catch(e){
				console.log(e);
			}
		};
	</script>
</head>
    <body style="cursor:move;" oncontextmenu="return false;">
   
	<input type="hidden" name="formId" value="<%= request.getAttribute("formId") %>" id="formId"/>
	
	 <div class="parentForm">
	 	<ul>
	 		<li>Antecessores:</li>
	 		<li>
		 		<select class="beforeCurrentForm">
		 			<option value=""></option>
			 		<c:forEach var="item" items="${parentForm}">
						<option value="${item.id}">(<c:out value="${item.typeDesc}"/>) <c:out value="${item.name}"/></option>
					</c:forEach>
				</select>
			</li>
	 	</ul>
	 </div>
	 <div class="mermaid" id="mermaid">
		graph TD;
		<%= request.getAttribute("diagram") %>
	 </div>
	 
	 <div class="teste popover fade right" style="width: 30%; height: auto; border: 1px solid rgb(204, 204, 204); position: fixed; z-index: 1000; top: 0px; margin-left: 807px; margin-top: 126px; background: #fafafa; color: #555; border-radius: 6px; padding: 3px 8px;display: none;"></div>
	 
	 <script type="text/javascript" src="<c:url value='js/mermaid.full.js'/>"></script>
	 <script src="js/jquery-1.11.1.min.js"></script>
	 <script src="js/bootstrap.min.js"></script>
	 <script type="text/javascript" src="<c:url value='js/jquery.mousewheel.js'/>"></script>
     <script type="text/javascript" src="<c:url value='js/diagram.js'/>"></script>
   
</body>
</html>