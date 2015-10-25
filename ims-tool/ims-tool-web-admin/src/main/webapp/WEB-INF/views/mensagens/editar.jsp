<%@ include file="/WEB-INF/views/template/includes.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<div>
	<form method="post" action="<c:url value="/mensagens/editar"></c:url>">
		<c:if test="${msgErro!=null}">
			<div class="alert alert-danger" role="alert">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
		  		<h4>${msgErro}</h4>
			</div>
		</c:if>

		<label>Nome: ${item.nome}</label><br />
		
		<label>Mensagem</label><br />
    	<textarea required="required" cols="50" rows="3" name="mensagem">${item.mensagem}</textarea><br />
    	
    	<label>Indisponibilidade</label>
    	<select name="indisponibilidade" required="required" >
    		<option value="SIM" <c:if test="${item.indisponibilidade eq 'SIM' or item.indisponibilidade eq 'S'}">selected="selected"</c:if>>SIM</option>
    		<option value="NÃO" <c:if test="${item.indisponibilidade eq 'NÃO' or item.indisponibilidade eq 'N'}">selected="selected"</c:if>>NÃO</option>
    	</select>
    	<br />
    	
    	<label>Ordem</label>
    	<input type="number" class="form-control" name="ordem" value="${item.ordem}" maxlength="3" required="required" />
    	
    	<label>Inicio (Formato: DD/MM/AAAA hh:mm)</label>
    	<input type="text" class="form-control datahora" name="dinicio" value="<fmt:formatDate value="${item.inicio}" pattern="dd/MM/yyyy HH:mm" />" />
    	
    	<label>Fim (Formato: DD/MM/AAAA hh:mm)</label>
    	<input type="text" class="form-control datahora" name="dfim" value="<fmt:formatDate value="${item.fim}" pattern="dd/MM/yyyy HH:mm" />" />
    	
    	<label>DDD IN (DDD(s) que deve(m) ouvir a mensagem (separados por vírgulas))</label>
    	<input type="text" class="form-control" name="dddin" value="${item.ddd_in}" />
    	
    	<label>DDD NOT IN (DDD(s) que não deve(m) ouvir a mensagem (separados por vírgulas))</label>
    	<input type="text" class="form-control" name="dddnotin" value="${item.ddd_not_in}" />
    	
    	<label><h3>Pontos de mensagem</h3></label>
    	
    	<table style="width:100%;" class="text-info">
    		<thead>
    			<tr>
    				<th colspan="4">Pontos compartilhados pelas arquiteturas nova e antiga</th>
    			</tr>
    		</thead>
    		<tbody>
	    		<tr>
    				<td colspan="4"><input type="checkbox" id="" name="ponto" value="INICIO" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'INICIO' or x eq ''}">checked</c:if></c:forEach> /><label for="">INICIO</label><br />             </td>
	    		</tr>
	    		<tr>
    				<td><input type="checkbox" id="CO" name="ponto" value="CO" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'CO'}">checked</c:if></c:forEach> /><label for= "CO">CO</label><br />          </td>
    				<td><input type="checkbox" id="BA" name="ponto" value="BA" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'BA'}">checked</c:if></c:forEach> /><label for= "BA">BA</label><br />          </td>
					<td><input type="checkbox" id="NC_TT" name="ponto" value="NC_TT" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'NC_TT'}">checked</c:if></c:forEach> /><label for= "NC_TT">NC_TT - Nova Cidade TT</label><br /></td>
					<td><input type="checkbox" id="TT" name="ponto" value="TT" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'TT'}">checked</c:if></c:forEach> /><label for= "TT">TT</label><br />                        </td>
	    		</tr>
	    		<tr>
					<td><input type="checkbox" id="SN" name="ponto" value="SN" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'SN'}">checked</c:if></c:forEach> /><label for= "SN">SN - Segundo Nivel</label><br />        </td>
					<td><input type="checkbox" id="BA_BL" name="ponto" value="BA_BL" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'BA_BL'}">checked</c:if></c:forEach> /><label for= "BA_BL">BA_BL - BA Banda Larga</label><br />   </td>
					<td><input type="checkbox" id="BA_VF" name="ponto" value="BA_VF" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'BA_VF'}">checked</c:if></c:forEach> /><label for= "BA_VF">BA_VF - BA VDSL ou Fibra</label><br /> </td>
					<td><input type="checkbox" id="CENSUP" name="ponto" value="CENSUP" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'CENSUP'}">checked</c:if></c:forEach> /><label for= "CENSUP">CENSUP (TL)</label><br />                    </td>
	    		</tr>
	    		<tr>
					<td><input type="checkbox" id="CONTROLE" name="ponto" value="CONTROLE" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'CONTROLE'}">checked</c:if></c:forEach> /><label for= "CONTROLE">CONTROLE</label><br />                 </td>
					<td><input type="checkbox" id="IMPRODUTIVAS" name="ponto" value="IMPRODUTIVAS" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'IMPRODUTIVAS'}">checked</c:if></c:forEach> /><label for= "IMPRODUTIVAS">IMPRODUTIVAS</label><br /> </td>
	    		</tr>
    		</tbody>
    	</table>
    	<br>
    	<table style="width:100%;" class="text-danger">
    		<thead>
    			<tr>
    				<th colspan="4">Pontos existentes somente na arquitetura antiga</th>
    			</tr>
    		</thead>
    		<tbody>
				<tr>
					<td><input type="checkbox" id="SE" name="ponto" value="SE" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'SE'}">checked</c:if></c:forEach> /><label for= "SE">SE - Suporte Especializado</label><br /></td>
					<td><input type="checkbox" id="NT" name="ponto" value="NT" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'NT'}">checked</c:if></c:forEach> /><label for= "NT">NT - Novas Tecnologias</label><br />    </td>
					<td><input type="checkbox" id="BA_TV" name="ponto" value="BA_TV" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'BA_TV'}">checked</c:if></c:forEach> /><label for= "BA_TV">BA_TV - BA TV</label><br />            </td>
					<td><input type="checkbox" id="BA_VOZ" name="ponto" value="BA_VOZ" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'BA_VOZ'}">checked</c:if></c:forEach> /><label for= "BA_VOZ">BA_VOZ - BA Voz</label><br />       </td>
				</tr>
				<tr>
					<td><input type="checkbox" id="TT_TV" name="ponto" value="TT_TV" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'TT_TV'}">checked</c:if></c:forEach> /><label for= "TT_TV">TT_TV - TT TV</label><br />             </td>
					<td><input type="checkbox" id="TT_BL" name="ponto" value="TT_BL" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'TT_BL'}">checked</c:if></c:forEach> /><label for= "TT_BL">TT_BL - TT Banda Larga</label><br />    </td>
					<td><input type="checkbox" id="TT_VOZ" name="ponto" value="TT_VOZ" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'TT_VOZ'}">checked</c:if></c:forEach> /><label for= "TT_VOZ">TT_VOZ - TT Voz</label><br />        </td>
					<td><input type="checkbox" id="TT_VF" name="ponto" value="TT_VF" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'TT_VF'}">checked</c:if></c:forEach> /><label for= "TT_VF">TT_VF - TT VDSL ou Fibra</label><br />  </td>
				</tr>
				<tr>
					<td><input type="checkbox" id="ALT_BAS" name="ponto" value="ALT_BAS" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'ALT_BAS'}">checked</c:if></c:forEach> /><label for= "ALT_BAS">ALT_BAS - Controle de Campo</label><br /> </td>
					<td><input type="checkbox" id="NC_BA" name="ponto" value="NC_BA" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'NC_BA'}">checked</c:if></c:forEach> /><label for= "NC_BA">NC_BA - Nova Cidade BA</label><br /></td>
					<td><input type="checkbox" id="NC_SE" name="ponto" value="NC_SE" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'NC_SE'}">checked</c:if></c:forEach> /><label for= "NC_SE">NC_SE - Nova Cidade SE</label><br /></td>
					<td><input type="checkbox" id="NC_NT" name="ponto" value="NC_NT" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'NC_NT'}">checked</c:if></c:forEach> /><label for= "NC_NT">NC_NT - Nova Cidade NT</label><br /></td>
				</tr>
				<tr>
					<td><input type="checkbox" id="NC_SN" name="ponto" value="NC_SN" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'NC_SN'}">checked</c:if></c:forEach> /><label for= "NC_SN">NC_SN - Nova Cidade SN</label></td>
				</tr>
			</tbody>
    	</table>
    	<br>
    	<table style="width:100%" class="text-success">
    		<thead>
    			<tr>
    				<th colspan="4">Pontos existentes somente na arquitetura nova</th>
    			</tr>
    		</thead>
    		<tbody>
				<tr>
					<td><input type="checkbox" id="NC_DW" name="ponto" value="NC_DW" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'NC_DW'}">checked</c:if></c:forEach> /><label for= "NC_DW">NC_DW - Nova Cidade DW</label></td>
					<td><input type="checkbox" id="NC_ETA" name="ponto" value="NC_ETA" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'NC_ETA'}">checked</c:if></c:forEach> /><label for= "NC_ETA">NC_ETA - Nova Cidade ETA</label><br /></td>
					<td><input type="checkbox" id="ETA" name="ponto" value="ETA" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'ETA'}">checked</c:if></c:forEach> /><label for= "ETA">ETA - ETA</label><br /></td>
					<td><input type="checkbox" id="COOFF" name="ponto" value="COOFF" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'COOFF'}">checked</c:if></c:forEach> /><label for= "COOFF">COOFF - COOFF</label><br /></td>
				</tr>
				<tr>
					<td><input type="checkbox" id="DW" name="ponto" value="DW" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'DW'}">checked</c:if></c:forEach> /><label for= "DW">DW - DW</label></td>
					<td><input type="checkbox" id="CL" name="ponto" value="CL" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'CL'}">checked</c:if></c:forEach> /><label for= "CL">CL - CL</label><br /></td>
					<td><input type="checkbox" id="ETA_TV" name="ponto" value="ETA_TV" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'ETA_TV'}">checked</c:if></c:forEach> /><label for= "ETA_TV">ETA_TV - ETA_TV</label><br /></td>
					<td><input type="checkbox" id="ETA_VOZ" name="ponto" value="ETA_VOZ" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'ETA_VOZ'}">checked</c:if></c:forEach> /><label for= "ETA_VOZ">ETA_VOZ - ETA_VOZ</label><br /></td>
				</tr>
				
				<tr>
					<td><input type="checkbox" id="INJECAO" name="ponto" value="INJECAO" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'INJECAO'}">checked</c:if></c:forEach> /><label for= "INJECAO">INJECAO - INJECAO</label></td>
					<td><input type="checkbox" id="SUPORTE_BA" name="ponto" value="SUPORTE_BA" <c:forEach var="x" items="${pontos}"><c:if test="${x eq 'SUPORTE_BA'}">checked</c:if></c:forEach> /><label for= "SUPORTE_BA">SUPORTE_BA - SUPORTE_BA</label><br /></td>
					<td></td>
					<td></td>
				</tr>
    		</tbody>
    	</table>
    	<input type="hidden" name="action" value="save" />
    	<input type="hidden" name="id" value="${item.id}" />
<!--     	<input type="submit" class="btn-xs" value="Salvar" name="btn" /> -->
<!--     	<input type="submit" class="btn-xs" value="Cancelar" name="btn" /> -->
    	
    	<button type="submit" class="btn btn-success" title="Confirmar alteração" name="btn" value="Salvar">
			<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
		</button>
		
		<a href="<c:url value="/mensagens"></c:url>" class="btn btn-danger" title="Cancelar" name="btn" value="Cancelar">
			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
		</a>
    	
<!--     	<a class="btn btn-danger"  href="listagem" title="Cancelar"> -->
<!-- 			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> -->
<!-- 		</a> -->
    	
	</form>    	
</div>