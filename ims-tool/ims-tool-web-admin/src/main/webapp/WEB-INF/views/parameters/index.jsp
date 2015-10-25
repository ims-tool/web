<%@ include file="/WEB-INF/views/template/includes.jsp" %>
<table class="table table-striped small">
	<thead>
		<tr>
			<th>Id</th>
			<th>Método</th>
			<th>Descrição</th>
			<th>Owner</th>
			<th>Sistemas Afetados</th>
			<th>Status</th>
			<th>Timeout</th>
			<th>Login Alteração</th>
			<th>Data Alteração</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="item">
		<tr>
			<td>${item.id}</td>
			<td>${item.methodName}</td>
			<td>${item.description}</td>
			<td>${item.owner}</td>
			<td>${item.referencedBy}</td>
			<td>
				<div class="btn-group btn-toggle status" data-status="${item.status}" data-id="${item.id}"> 
			    	<button class="btn on btn-xs <c:choose><c:when test="${item.status eq 'true'}">active btn-success</c:when><c:otherwise>btn-default</c:otherwise></c:choose>">ON</button>
			    	<button class="btn off btn-xs <c:choose><c:when test="${item.status eq 'false'}">active btn-danger</c:when><c:otherwise>btn-default</c:otherwise></c:choose>">OFF</button>
		    	</div>
			</td>
			<td><div class="col-md-11"><input data-id="${item.id}" class="spinner" value="${item.timeout}" /></div></td>
			<td>${item.loginId}</td>
			<td><fmt:formatDate pattern="dd/MM/yyyy H:m:s" value="${item.startDate}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>