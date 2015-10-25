<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="estiloDiv">
	<div style="margin: 10px; border: #dddddd 1px solid; padding: 10px;">
		<form id="formHorario" method="post" action="<c:url value="/horario/updateHorario"></c:url>">
		
				<input type="hidden" id="tipoServico" name="tipoServico" value="${horario.tipoServico}">
				 <div class="form-group">
				 	<label>Dia da Semana</label>
  						<input type="text" class="form-control" id="diaSemana" name="diaSemana" value="${horario.diaSemana}" maxlength="30" required="required" readonly="readonly">
				</div>
				 <div class="form-group">
				 	<label>Horário de Início</label>
  					<input type="text" class="form-control" id="horaInicio" name="horaInicio" value="${horario.horaInicio}" maxlength="30" required="required" OnKeyUp="Mascara_Hora(this.value, this)">
				</div>
				 <div class="form-group">
				 	<label>Horário de Fim</label>
				 	<input type="text" class="form-control" id="horaFim" name="horaFim" value="${horario.horafim}" maxlength="30" required="required" OnKeyUp="Mascara_Hora(this.value, this)">
				</div>
				 <div class="form-group div-value">
				 	<label>Data de Criação</label>
  						<input type="text" class="form-control" id="dataCriacao" name="dataCriacao" value="${horario.dataCriacao}" required="required" readonly="readonly">
				</div>
				<div class="form-group">
				 	<label>Alterado Por</label>
  						<input type="text" class="form-control" id="user" name="user" value="${horario.usuario}" required="required" readonly="readonly">
				</div>
				<table>
				<tr>
					<td >
						<div style="width: 100%;text-align: right;padding-top: 10px;">
								<button type="submit" class="btn btn-success" title="Confirmar alteração">
										<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
								</button>
								<a class="btn btn-danger"  href="<c:url value="/horario/pesquisar?tipoServico=${horario.tipoServico}"></c:url>" title="Cancelar alteração">
											<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
								</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>