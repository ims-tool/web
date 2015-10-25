<%@ include file="/WEB-INF/views/template/includes.jsp" %>
<link href="<c:url value="/static/css/horarios.css" />" rel="stylesheet" />
<c:if test="${msgSucess!=null}">
	<div class="alert alert-success" role="alert">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
	  	<h4>${msgSucess}</h4>
	</div>
</c:if>
<c:if test="${msgErro!=null}">
			<div class="alert alert-danger" role="alert">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
		  		<h4>${msgErro}</h4>
			</div>
		</c:if>
<form>
    <div class="well carousel-search hidden-sm">
    	<div style="width:120px; float:left; font-weight: bold; padding: 6px 12px; margin-bottom: 0; font-size: 14px;">Tipo Serviço:</div>
        <div class="btn-group"> <a style="width:230px; float:right"  class="btn btn-default dropdown-toggle btn-select2" data-toggle="dropdown" href="#"><c:choose><c:when test="${tipoServico != null}">${tipoServico}</c:when><c:otherwise>Selecione um Tipo de Serviço</c:otherwise></c:choose><span class="caret"></span></a>
            <ul class="dropdown-menu">
        	<c:forEach items="${tipoServicos}" var="tipoServico">
        		<li><a href="#">${tipoServico.descricao}</a></li>
			</c:forEach>
        </ul>
        </div>
    </div>
</form>
	<div class="estiloDiv" style="z-index:1; overflow: scroll; "  >
						<table  id="tblExport" class="table table-bordered table-striped">
							<thead>
								<tr>
									<th class="primary colunaCentralizada">Dia da Semana</th>
									<th class="primary colunaCentralizada">Horário de Início</th>
									<th class="primary colunaCentralizada">Horario de Fim</th>
									<th class="primary colunaCentralizada">Alterado Por</th>
									<th class="primary colunaCentralizada">Editar</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${list}" var="item">
							<tr>
								<td class="colunaCentralizada">${item.diaSemanaExtenso}</td>
								<td class="colunaCentralizada">${item.horaInicio}</td>
								<td class="colunaCentralizada">${item.horafim}</td>
<%-- 								<td class="colunaCentralizada"><a id="inicio" data-type="combodate"data-template="HH:mm" data-format="HH:mm" data-viewformat="HH:mm"  class="param-inicio"  data-pk="${item.diaSemana}" data-name="${item.tipoServico}">${item.horaInicio}</a></td> --%>
<%-- 								<td class="colunaCentralizada"><a id="fim" data-type="combodate"data-template="HH:mm" data-format="HH:mm" data-viewformat="HH:mm"  class="param-fim" data-pk="${item.diaSemana}" data-name="${item.tipoServico}">${item.horafim}</a></td> --%>
					<%-- 			<td><fmt:formatDate pattern="dd/MM/yyyy H:m:s" value="${item.dataCriacao}" /></td> --%>
								<td class="colunaCentralizada">${item.usuario}</td>
								<td class="colunaCentralizada"><a id="editar" href="<c:url value="/horario/editar/${item.tipoServico}/${item.diaSemana}"></c:url>" class="btn btn-warning btn-editar-parameter" title="Editar Parametro"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> </a></td>
							</tr>
							</c:forEach>
							</tbody>
						</table>
	</div>
