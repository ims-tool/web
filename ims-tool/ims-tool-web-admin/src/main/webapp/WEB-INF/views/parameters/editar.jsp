<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="estiloDiv">
	<div style="margin: 10px; border: #dddddd 1px solid; padding: 10px;">
		<form id="confirmarRegraForm" name="confirmarRegraForm" method="post" action="<c:url value="/parameters/editar/${parameterRow.id}"></c:url>">
			<c:if test="${parameterRow!=null}">
				<input type="hidden" id="action" name="action" value="save">
				<input type="hidden" id="parameterId" name="parameterId" value="${parameterRow.id}" required="required">
				 
				 <div class="form-group">
				 	<label>Nome</label>
  						<input type="text" class="form-control" id="name" name="name" value="${parameterRow.name}" maxlength="30" required="required">
				</div>
				 <div class="form-group">
				 	<label>Descrição</label>
  						<input type="text" class="form-control" id="description" name="description" value="${parameterRow.description}" required="required">
				</div>
				 <div class="form-group">
				 	<label>Tipo</label>
  						<select class="form-control" id="type" name="type" selectedValue="${parameterRow.type}">
  							<option value="boolean">boolean</option>
  							<option value="int">int</option>
  							<option value="string">string</option>
  						</select>
				</div>
				 <div class="form-group div-value">
				 	<label>Valor</label>
  						<input type="text" class="form-control" id="value" name="value" value="${parameterRow.value}" required="required">
				</div>
				<div class="form-group">
				 	<label>Owner</label>
  						<input type="text" class="form-control" id="owner" name="owner" value="${parameterRow.owner}" required="required" readonly="readonly">
				</div>
			</c:if>
			<table width="100%">
				<tr width="100%">
					<td >
						<div style="width: 100%;text-align: right;padding-top: 10px;">
							<c:if test="${parameterRow!=null}">
							<button type="submit" class="btn btn-success" title="Confirmar alteração">
										<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
							</button>
							</c:if>
							<c:if test="${parameterRow!=null}">
								<a class="btn btn-danger"  href="<c:url value="/parameters"></c:url>" title="Cancelar">
											<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
								</a>
							</c:if>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<c:if test="${msgErroConfirmar!=null}">
			<div class="alert alert-danger" role="alert">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
			  	<h4>${msgErroConfirmar}</h4>
			</div>
		</c:if>
		
		<c:if test="${msgSucesso!=null}">
			<div class="alert alert-success" role="alert">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
			  	<h4>${msgSucesso}</h4>
			</div>
		</c:if>
	</div>	

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