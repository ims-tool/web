<%@ include file="/WEB-INF/views/template/includes.jsp" %>
<c:if test="${not empty errors }">
	<div class="alert alert-warning alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		<ul>
		<c:forEach items="${errors}" var="error">
			<li>${error}</li>
		</c:forEach>
		</ul>
	</div>
	<script type="text/javascript">
			setTimeout(function(){
				$(".alert-warning button.close").trigger("click");
			}, 10000);
	</script>
</c:if>