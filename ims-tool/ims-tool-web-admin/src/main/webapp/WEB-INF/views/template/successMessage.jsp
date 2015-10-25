<%@ include file="/WEB-INF/views/template/includes.jsp" %>
<c:if test="${not empty successMessage }">
	<div class="alert alert-success alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			${successMessage}
	</div>
	<script type="text/javascript">
			setTimeout(function(){
				$(".alert-success button.close").trigger("click");
			}, 10000);
	</script>
</c:if>