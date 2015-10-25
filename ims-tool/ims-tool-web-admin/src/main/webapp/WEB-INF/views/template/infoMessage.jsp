<%@ include file="/WEB-INF/views/template/includes.jsp" %>
<c:if test="${not empty infoMessage }">
	<div class="alert alert-info alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			${infoMessage}
	</div>
	<script type="text/javascript">
			setTimeout(function(){
				$(".alert-info button.close").trigger("click");
			}, 10000);
	</script>
</c:if>