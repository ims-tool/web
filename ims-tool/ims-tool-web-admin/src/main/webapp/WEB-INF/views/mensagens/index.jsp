<%@ include file="/WEB-INF/views/template/includes.jsp" %>
<c:if test="${msgSucesso!=null}">
	<div class="alert alert-success" role="alert">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
	  	<h4>${msgSucesso}</h4>
	</div>
</c:if>
<table class="table table-striped small">
	<thead>
		<tr>
			<th>Id</th>
			<th>Nome</th>
			<th>Mensagem</th>
			<th>Indisponibilidade</th>
			<th>Ordem</th>
			<th>Status</th>
			<th>Inicio</th>
			<th>Fim</th>
			<th>DDD_IN</th>
			<th>DDD_NOT_IN</th>
			<th>Ponto</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="item">
		<tr>
			<td>${item.id}</td>
			<td>${item.nome}</td>
			<td>${item.mensagem}</td>
			<td>${item.indisponibilidade}</td>
			<td>${item.ordem}</td>
			<td>
				<div class="btn-group btn-toggle status" data-status="${item.status}" data-id="${item.id}"> 
			    	<button class="btn on btn-xs <c:choose><c:when test="${item.status eq 'ATIVA'}">active btn-success</c:when><c:otherwise>btn-default</c:otherwise></c:choose>">ON</button>
			    	<button class="btn off btn-xs <c:choose><c:when test="${item.status eq 'INATIVA'}">active btn-danger</c:when><c:otherwise>btn-default</c:otherwise></c:choose>">OFF</button>
		    	</div>
			</td>
			<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${item.inicio}" /></td>
			<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${item.fim}" /></td>
			<td>${item.ddd_in}</td>
			<td>${item.ddd_not_in}</td>
			<td>${item.ponto}</td>
			<td>
				<a href="<c:url value="/mensagens/editar/${item.id}"></c:url>" class="btn btn-warning btn-editar-parameter" title="Editar Parametro"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> </a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>