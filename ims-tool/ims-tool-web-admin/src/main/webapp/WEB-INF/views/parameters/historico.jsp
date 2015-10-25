<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" >
 <link rel="stylesheet" media="screen" href="<c:url value="/resources/css/jquery-ui.css" />" >
 <link rel="stylesheet" media="screen" href="<c:url value="/resources/css/jquery-ui.structure.css" />" >
 <link rel="stylesheet" media="screen" href="<c:url value="/resources/css/jquery-ui.theme.css" />" >
 <link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet" media="screen">
 
 <style type="text/css">
	    	.colunaCentralizada{
	    		text-align: center;
	    		font-size: xx-small;
	    	}
	    	.colunaEsquerda{
	    		text-align: left;
	    		font-size: xx-small;
	    	}
	    	.estiloDiv{
	    		/*display: inline-block;*/
	    		min-width: 100%;
	    	}
	    	.outracor { 
	    		background: #e0e0e0; 
	    	}
	    	.primary { 
	    		background-color: #003366;
	    		color: white;
	    		vertical-align: middle !important;
	    	}
	    	table, td, th {
			    border: 1px solid white;
			}
			#accordion .ui-accordion-content {
    			height: auto !important;
			}
			.test{
				z-index:1 !important;
				overflow: scroll !important;
			}
		</style>
  
    	<script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script>
	    <script type="text/javascript" src="<c:url value="/resources/js/jquery-ui.js" />"></script>
	    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />"></script>
	    <script>
  $(function() {
    $( "#accordion" ).accordion({
      event: "click",
      fillSpace: true
    	 
    });
  });
 
  /*
   * hoverIntent | Copyright 2011 Brian Cherne
   * http://cherne.net/brian/resources/jquery.hoverIntent.html
   * modified by the jQuery UI team
   */
  $.event.special.hoverintent = {
    setup: function() {
      $( this ).bind( "click", jQuery.event.special.hoverintent.handler );
    },
    teardown: function() {
      $( this ).unbind( "click", jQuery.event.special.hoverintent.handler );
    },
    handler: function( event ) {
      var currentX, currentY, timeout,
        args = arguments,
        target = $( event.target ),
        previousX = event.pageX,
        previousY = event.pageY;
 
      function track( event ) {
        currentX = event.pageX;
        currentY = event.pageY;
      };
 
      function clear() {
        target
          .unbind( "mousemove", track )
          .unbind( "mouseout", clear );
        clearTimeout( timeout );
      }
 
      function handler() {
        var prop,
          orig = event;
 
        if ( ( Math.abs( previousX - currentX ) +
            Math.abs( previousY - currentY ) ) < 7 ) {
          clear();
 
          event = $.Event( "hoverintent" );
          for ( prop in orig ) {
            if ( !( prop in event ) ) {
              event[ prop ] = orig[ prop ];
            }
          }
          // Prevent accessing the original event since the new event
          // is fired asynchronously and the old event is no longer
          // usable (#6028)
          delete event.originalEvent;
 
          target.trigger( event );
        } else {
          previousX = currentX;
          previousY = currentY;
          timeout = setTimeout( handler, 100 );
        }
      }
 
      timeout = setTimeout( handler, 100 );
      target.bind({
        mousemove: track,
        mouseout: clear
      });
    }
  };
  $(document).ready(function(){
	
	$("#accordion").accordion( "option", "autoHeight", false );
	  
  });
  
  </script>
	    
  </head>
  
<body>
			<nav class="navbar navbar-default" role="navigation" style="width: 100%;">
				<div class="navbar-header" >
			  		<a class="navbar-brand" href="#">
			  			<img width="250px" src="<c:url value="/resources/img/logogvt.png" />" alt="">
			  		</a>
			  		<h3>Histórico de Regra de Transferência</h3>
			 	</div>
			</nav>
			<c:if test="${historico[0].listaAntes[0].logRoot==null}">
				<div class="alert alert-info" role="alert" style="width: 100%;">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
				  		<h4>Nenhum registro de Histórico encontrado</h4>
					</div>
			</c:if>
	<c:if test="${historico[0].listaAntes[0].logRoot != null}">
			
			<div style="width: 50%" >
  						<form id="informaLogRoot" name="informaLogRoot" method="post" action="consulta">
							Regra de Transferência:
							<input type="text" maxlength="10" name="logRoot" id="logRoot" value="${historico[0].listaAntes[0].logRoot}" disabled="disabled" />
							
						</form>
  					</div>
<div id="ajustaFormato" >
	<div id="accordion" class="ui-accordion ui-widget ui-helper-reset" role="tablist" width="100%" >
		<c:forEach var="hist" items="${historico}">
		<h3 class="ui-accordion-header ui-state-default ui-accordion-icons ui-accordion-header-active ui-state-active ui-corner-top" role="tab" id="ui-id-1" aria-controls="ui-id-2" aria-selected="true" aria-expanded="true" tabindex="0" style="width: 100%;" >
			<span class="ui-accordion-header-icon ui-icon ui-icon-triangle-1-s">
			</span>
				${hist.dataAtualizacao} - ${hist.usuario} 
		</h3>
		<div class="test" id="ui-id-2" aria-labelledby="ui-id-1" role="tabpanel" aria-hidden="false" style="display: block; height: 14px; width: 100%;">
			<!-- ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active -->
			<br />ANTES<br />
			<table  id="tbAntes" class="table table-bordered table-striped" width="100%" >
							<thead>
								<tr>
									<th class="primary colunaCentralizada">LOGROOT</th>
									<th class="primary colunaCentralizada">ASSUNTO</th>
									<th class="primary colunaCentralizada">CENÁRIO</th>
									<th class="primary colunaCentralizada">PRODUTO</th>
									<th class="primary colunaCentralizada">REGRAGRUPO</th>
									<th class="primary colunaCentralizada">REGRA</th>
									<th class="primary colunaCentralizada">FILHO</th>
									<th class="primary colunaCentralizada">TAG</th>
									<th class="primary colunaCentralizada">CAMPO</th>
									<th class="primary colunaCentralizada">NOME CAMPO</th>
									<th class="primary colunaCentralizada">TIPO</th>
									<th class="primary colunaCentralizada">OPERAÇÃO</th>
									<th class="primary colunaCentralizada">VALOR1</th>
									<th class="primary colunaCentralizada">VALOR2</th>
									<th class="primary colunaCentralizada">VALOR3</th>
									<th class="primary colunaCentralizada">VALOR4</th>
									<th class="primary colunaCentralizada">VALOR5</th>
									<th class="primary colunaCentralizada">VALOR6</th>
									<th class="primary colunaCentralizada">VALOR7</th>
									<th class="primary colunaCentralizada">VALOR8</th>
									<th class="primary colunaCentralizada">VALOR9</th>
									<th class="primary colunaCentralizada">VALOR10</th>
									<th class="primary colunaCentralizada">SAÍDA</th>
									<th class="primary colunaCentralizada">VDN SAÍDA</th>
									<th class="primary colunaCentralizada">SKILL 1</th>
									<th class="primary colunaCentralizada">SKILL 2</th>
									<th class="primary colunaCentralizada">MSG SAÍDA</th>
									<th class="primary colunaCentralizada">TAG SAÍDA</th>
									<th class="primary colunaCentralizada">DESC. PONTO</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="antes" items="${hist.listaAntes}">
									<tr>
										<td class="colunaEsquerda">${antes.logRoot}</td>
										<td class="colunaEsquerda"><span style="width: 200px; display: inline-block;">${antes.assunto}</span></td>
										<td class="colunaEsquerda"><span style="width: 200px; display: inline-block;">${antes.cenario}</span></td>
										<td class="colunaEsquerda">${antes.produto}</td>
										<td class="colunaCentralizada">${antes.regraGrupo}</td>
										<td class="colunaCentralizada">${antes.regra}</td>
										<td class="colunaCentralizada">${antes.filho}</td>
										<td class="colunaCentralizada">${antes.log}</td>
										<td class="colunaCentralizada">${antes.campo}</td>
										<td class="colunaEsquerda">${antes.nomeCampo}</td>
										<td class="colunaEsquerda">${antes.tipo}</td>
										<td class="colunaCentralizada">${antes.operacao}</td>
										<td class="colunaEsquerda">${antes.valor1}</td>
										<td class="colunaEsquerda">${antes.valor2}</td>
										<td class="colunaEsquerda">${antes.valor3}</td>
										<td class="colunaEsquerda">${antes.valor4}</td>
										<td class="colunaEsquerda">${antes.valor5}</td>
										<td class="colunaEsquerda">${antes.valor6}</td>
										<td class="colunaEsquerda">${antes.valor7}</td>
										<td class="colunaEsquerda">${antes.valor8}</td>
										<td class="colunaEsquerda">${antes.valor9}</td>
										<td class="colunaEsquerda">${antes.valor10}</td>
										<td class="colunaCentralizada">${antes.saida}</td>
										<td class="colunaCentralizada">${antes.vdnSaida}</td>
										<td class="colunaEsquerda">${antes.skill1}</td>
										<td class="colunaEsquerda">${antes.skill2}</td>
										<td class="colunaCentralizada">${antes.msgSaida}</td>
										<td class="colunaCentralizada">${antes.logSaida}</td>
										<td class="colunaEsquerda">${antes.descPonto}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<br />DEPOIS<br />
						<table  id="tbdepois" class="table table-bordered table-striped" width="100%">
							<thead>
								<tr>
									<th class="primary colunaCentralizada">LOGROOT</th>
									<th class="primary colunaCentralizada">ASSUNTO</th>
									<th class="primary colunaCentralizada">CENÁRIO</th>
									<th class="primary colunaCentralizada">PRODUTO</th>
									<th class="primary colunaCentralizada">REGRAGRUPO</th>
									<th class="primary colunaCentralizada">REGRA</th>
									<th class="primary colunaCentralizada">FILHO</th>
									<th class="primary colunaCentralizada">TAG</th>
									<th class="primary colunaCentralizada">CAMPO</th>
									<th class="primary colunaCentralizada">NOME CAMPO</th>
									<th class="primary colunaCentralizada">TIPO</th>
									<th class="primary colunaCentralizada">OPERAÇÃO</th>
									<th class="primary colunaCentralizada">VALOR1</th>
									<th class="primary colunaCentralizada">VALOR2</th>
									<th class="primary colunaCentralizada">VALOR3</th>
									<th class="primary colunaCentralizada">VALOR4</th>
									<th class="primary colunaCentralizada">VALOR5</th>
									<th class="primary colunaCentralizada">VALOR6</th>
									<th class="primary colunaCentralizada">VALOR7</th>
									<th class="primary colunaCentralizada">VALOR8</th>
									<th class="primary colunaCentralizada">VALOR9</th>
									<th class="primary colunaCentralizada">VALOR10</th>
									<th class="primary colunaCentralizada">SAÍDA</th>
									<th class="primary colunaCentralizada">VDN SAÍDA</th>
									<th class="primary colunaCentralizada">SKILL 1</th>
									<th class="primary colunaCentralizada">SKILL 2</th>
									<th class="primary colunaCentralizada">MSG SAÍDA</th>
									<th class="primary colunaCentralizada">TAG SAÍDA</th>
									<th class="primary colunaCentralizada">DESC. PONTO</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="depois" items="${hist.listaDepois}">
									<tr>
										<td class="colunaEsquerda">${depois.logRoot}</td>
										<td class="colunaEsquerda"><span style="width: 200px; display: inline-block;">${depois.assunto}</span></td>
										<td class="colunaEsquerda"><span style="width: 200px; display: inline-block;">${depois.cenario}</span></td>
										<td class="colunaEsquerda">${depois.produto}</td>
										<td class="colunaCentralizada">${depois.regraGrupo}</td>
										<td class="colunaCentralizada">${depois.regra}</td>
										<td class="colunaCentralizada">${depois.filho}</td>
										<td class="colunaCentralizada">${depois.log}</td>
										<td class="colunaCentralizada">${depois.campo}</td>
										<td class="colunaEsquerda">${depois.nomeCampo}</td>
										<td class="colunaEsquerda">${depois.tipo}</td>
										<td class="colunaCentralizada">${depois.operacao}</td>
										<td class="colunaEsquerda">${depois.valor1}</td>
										<td class="colunaEsquerda">${depois.valor2}</td>
										<td class="colunaEsquerda">${depois.valor3}</td>
										<td class="colunaEsquerda">${depois.valor4}</td>
										<td class="colunaEsquerda">${depois.valor5}</td>
										<td class="colunaEsquerda">${depois.valor6}</td>
										<td class="colunaEsquerda">${depois.valor7}</td>
										<td class="colunaEsquerda">${depois.valor8}</td>
										<td class="colunaEsquerda">${depois.valor9}</td>
										<td class="colunaEsquerda">${depois.valor10}</td>
										<td class="colunaCentralizada">${depois.saida}</td>
										<td class="colunaCentralizada">${depois.vdnSaida}</td>
										<td class="colunaEsquerda">${depois.skill1}</td>
										<td class="colunaEsquerda">${depois.skill2}</td>
										<td class="colunaCentralizada">${depois.msgSaida}</td>
										<td class="colunaCentralizada">${depois.logSaida}</td>
										<td class="colunaEsquerda">${depois.descPonto}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				</div>
			</c:forEach>
		</div>
	</div>
	</c:if>
	<div style="float:right; text-align: right; ">
						<%-- 
							<a href="javascript:window.history.go(-1)"> <img style="height: 25px;width: 25px;padding-right: 10px;" src="<c:url value="/resources/img/iconevoltar.png" />" title="Voltar" /></a> 
						--%>
						<button type="button" class="btn btn-warning" onclick="javascript:window.history.go(-1)">
  								<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Voltar
						</button>
	</div>
</body>
</html>