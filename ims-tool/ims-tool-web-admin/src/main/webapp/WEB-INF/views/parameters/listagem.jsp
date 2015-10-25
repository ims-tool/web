<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div class="estiloDiv">
	<c:if test="${msgInfo!=null}">
		<div class="alert alert-info" role="alert">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
	  		<h4>${msgInfo}</h4>
		</div>
	</c:if>
	<c:if test="${msgErro!=null}">
		<div class="alert alert-danger" role="alert">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
	  		<h4>${msgErro}</h4>
		</div>
	</c:if>

	<c:if test="${parameters!=null}">
		<div class="estiloDiv">
			<table  id="tblExport" class="table table-bordered table-striped text-center">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nome</th>
						<th>Descrição</th>
						<th>Tipo</th>
						<th>Valor</th>
						<th>Ultima Alteração</th>
						<th>Owner</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="parameter" items="${parameters}">
						<tr>
							<td>${parameter.id}</td>
							<td>${parameter.name}</td>
							<td>${parameter.description}</td>
							<td>${parameter.type}</td>
							<td>${parameter.value}</td>
							<td>${parameter.loginid} ${parameter.startdate}</td>
							<td>${parameter.owner}</td>
							<td>
								<a href="<c:url value="/parameters/editar/${parameter.id}"></c:url>" class="btn btn-warning btn-editar-parameter" title="Editar Parametro"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> </a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		 </div>
	</c:if>
</div>
