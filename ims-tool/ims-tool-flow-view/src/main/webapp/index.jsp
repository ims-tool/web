<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>IMS Flow view</title>
    
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    
    <link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
    
    <link href="select2/select2.css" rel="stylesheet" />
	<link href="select2/select2-bootstrap.css" rel="stylesheet" />
	<link href="css/app.css" rel="stylesheet" />
  </head>

  <body>
  <div class="loading">carregando...</div>
  <div class="userMessage">...</div>
  
  <%@include  file="includes/AnnounceModal.html" %>
  <%@include  file="includes/OperationModal.html" %>
  <%@include  file="includes/OperationGroupModal.html" %>
  <%@include  file="includes/OperationParametersModal.html" %>
  <%@include  file="includes/OperationMapModal.html" %>
  <%@include  file="includes/DecisionModal.html" %>
  <%@include  file="includes/DecisionGroupModal.html" %>
  <%@include  file="includes/DecisionMapModal.html" %>
  <%@include  file="includes/DecisionParametersModal.html" %>
  <%@include  file="includes/DecisionChanceModal.html" %>
  <%@include  file="includes/PromptCollectModal.html" %>
  <%@include  file="includes/MenuModal.html" %>
  <%@include  file="includes/ChoiceModal.html" %>
  <%@include  file="includes/NomatchinputModal.html" %>
  <%@include  file="includes/PromptModal.html" %>
  <%@include  file="includes/ConditionModal.html" %>
  <%@include  file="includes/ConditionGroupModal.html" %>
  <%@include  file="includes/ConditionMapModal.html" %>
  <%@include  file="includes/ConditionParametersModal.html" %>
  <%@include  file="includes/TratamentoResultadoModal.html" %>
  <%@include  file="includes/TransferModal.html" %>
  <%@include  file="includes/GrammarModal.html" %>
  <%@include  file="includes/DisconnectModal.html" %>
  <%@include  file="includes/FlowModal.html" %>
  <%@include  file="includes/AudioModal.html" %>
  <%@include  file="includes/PesquisaAudioModal.html" %>
  <%@include  file="includes/newElementsModal.html" %>
  <%@include  file="includes/nextFormModal.html" %>
  <%@include  file="includes/TagModal.html" %>
  <%@include  file="includes/VersionModal.html" %>
  
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Modal title</h4>
      </div>
      <div class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
        <button type="button" class="btn btn-primary btn-save">Salvar</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
  
<div id="wrapper">
      <div id="sidebar-wrapper">
	      <ul id="sidebar_menu" class="sidebar-nav">
	           <li class="sidebar-brand"><cite>Menu</cite>
	           	<span id="main_icon" class="glyphicon glyphicon-remove"></span>
	           	<span id="main_icon" class="glyphicon yed-download"><img src="img/yed.png" style="width: 25px;margin-top: -10px;"></span>
	           </li>
	      </ul> 
	      
	      <div class="element-text"></div>
	      
	      <hr />
	      
 	      <div class="btn-action-container"> 

 		      <!-- <a operation="del" class="btn btn-sm btn-danger btn-action" href="#">Excluir</a> -->
 		      <a operation="edit" class="btn btn-sm btn-info btn-action edit-btn" href="#">Editar</a>  
 		      
 		      
			</div>


      </div>
          
      <!-- Page content -->
      <div id="page-content-wrapper">
        <!-- Keep all page content within the page-content inset div! -->
        <div class="page-content inset">


    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="index.jsp"><img src="img/logo_increase.png" width="100" style="margin-top: 10px;margin-right: 10px;"/>Fluxograma</a>          
        </div>
        
        <div class="btn-group btn-group-justified right" role="group" aria-label="...">
		  <div class="btn-group" role="group">
		  <a class="dropdown-toggle btn btn-xs btn-warning right" href="#" data-toggle="dropdown" style="margin-top: 14px;">Configurações </a>
		        </div>
		  <div class="btn-group" role="group">
		  		<a class="btn btn-warning btn-xs right btn-editar-fluxo" href="javascript:void(0);" onclick="javascript:APP.editFlow()">Editar Fluxo</a>
		   </div>
		</div>
		
        
        <div class="btn-group btn-group-justified right" role="group" aria-label="...">
		  <div class="btn-group" role="group">
        		<a class="btn btn-danger btn-xs  back-button" href="javascript:void(0);" onclick="javascript:APP.back()"><i class="glyphicon glyphicon-share-alt"></i></a>
          </div>
		  <div class="btn-group" role="group">
        		<a class="btn btn-info btn-xs  back-button" href="javascript:void(0);" onclick="javascript:APP.zoomOut()"><i class="glyphicon glyphicon-minus"></i></a>
          </div>
		  <div class="btn-group" role="group">
        		<a class="btn btn-success btn-xs back-button" href="javascript:void(0);" onclick="javascript:APP.zoomIn()"><i class="glyphicon glyphicon-plus"></i></a>
		  </div>
		  <div class="btn-group" role="group">
        		<a class="btn btn-default btn-xs back-button" href="javascript:void(0);" onclick="javascript:APP.reload()"><i class="glyphicon glyphicon-refresh"></i></a>
           </div>
		</div>
        
        
        
        <div id="navbar" class="collapse navbar-collapse pull-right">
          <ul class="nav navbar-nav">
          <li>
          		<input id="forms" style="width: 300px;" name="forms" class="forms-select input-xlarge" type="hidden"/>
          	</li>
   
          </ul>			
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
			       <div style="margin-top:50px;height: 100%;min-height: 520px;">
						<iframe src="Main" class="col-sm-12" style="height:92%;margin-top: 15px;  margin: 0px; padding: 0px;"></iframe>
			     	</div>
        </div>
        
        
        <div class="dropdown-menu dropdown-menu-config" style="padding: 15px 15px 15px 15px; top: 60px;right: 20px;min-width: 400px;left:inherit;">
	<h5 style=" background-color: #FA7116;
	  padding: 20px 20px 20px 11px;color: #fefefe;
	  top: -25px;
	  cursor: pointer;
	  position: relative;
	  left: -15px;
	  width: 108.5%;"><b>Configurações</b> <a class="right dropdown-toggle" href="javascript:void(0);" style="color: #fff;text-decoration:none;">X</a></h5>

				<select id="level" name="level" class="select2 level-select">
          			<option value="2">2</option>
          			<option value="3">3</option>
          			<option value="4">4</option>
          			<option value="5">5</option>
          			<option value="6">6</option>
          			<option value="7">7</option>
          			<option value="8">8</option>
          			<option value="9">9</option>
          			<option value="10">10</option>
          		</select> N&iacute;veis de exibição do diagrama da URA
          	
          	<br /><br />
          	
          	
          		<input type="checkbox" class="enableReport" /> Habilitar relatório de fluxo de ligações 
          		
          		<br /><br />
          		
          		<div class="callReportContainer">
	          		<select id="tempo" name="tempo" class="select2 tempo-select">
	          			<option value=""></option>
	          			<option value="5">5</option>
	          			<option value="10">10</option>
	          			<option value="15">15</option>
	          			<option value="20">20</option>
	          			<option value="25">25</option>
	          			<option value="30">30</option>
	          			<option value="35">35</option>
	          			<option value="40">40</option>
	          			<option value="45">45</option>
	          			<option value="50">50</option>
	          			<option value="55">55</option>
	          			<option value="60">60</option>
	          		</select> Tempo em minutos para consulta de ligações
	          		<br />
	          		<a href="javascript:void(0);" class="definirPeriodo">Definir período personalizado</a>
	          		<br />
	          		<div class="periodoPersonalizado">
	          			<hr />
		          		<div class="input-append date left">
		          			De <input type="text" name="periodoDe" size="15" />
		          		</div> 
		          		<div class="input-append date left">
		          			até <input type="text" name="periodoAte" size="15" />
		          		</div>
		          		<br />
		          		<button type="button" class="btn btn-primary reloadPeriodo btn-xs right" style="margin-top: -18px;">Enviar</button>	
					</div>
				</div>
</div>
      </div>

<%@include  file="includes/frameConditionModal.html" %>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/jquery-ui.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    
    <script src="js/bootstrap-datetimepicker.min.js"></script>
    <script src="js/bootstrap-datetimepicker.pt-BR.js"></script>
    
    <script src="select2/select2.js"></script>
	<script src="select2/select2_locale_pt-BR.js"></script>
	<script src="js/system/app.js"></script>
	<script src="js/system/util.js"></script>
	<script src="js/system/prompt.js"></script> 
	<script src="js/system/menu.js"></script>
	<script src="js/system/transfer.js"></script>
	<script src="js/system/grammar.js"></script>
	<script src="js/system/disconnect.js"></script>
	<script src="js/system/flow.js"></script>
	<script src="js/system/tag.js"></script>
	<script src="js/system/operation.js"></script>
	<script src="js/system/condition.js"></script>
	<script src="js/system/decision.js"></script>
	<script src="js/system/promptCollect.js"></script>
	<script src="js/system/nomatchinput.js"></script>
	<script src="js/system/version.js"></script>
	
	<script>
$(document).ready(function() {
  // Setup drop down menu
 $('.dropdown-toggle').dropdown();
  // Fix input element click problem
  $('.dropdown-toggle').click(function(e) {
    $('.dropdown-menu-config').toggle();
  });
});
</script>
  </body>
</html>