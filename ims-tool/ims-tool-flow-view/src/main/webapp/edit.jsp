<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Fluxograma nova URA</title>
    
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link href="select2/select2.css" rel="stylesheet" />
	<link href="select2/select2-bootstrap.css" rel="stylesheet" />
	<link href="css/app.css" rel="stylesheet" />
  </head>

  <body>
  
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Modal title</h4>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="recipient-name" class="control-label">Recipient:</label>
            <input type="text" class="form-control" id="recipient-name">
          </div>
          <div class="form-group">
            <label for="message-text" class="control-label">Message:</label>
            <textarea class="form-control" id="message-text"></textarea>
          </div>
        </form>
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
	           	<span id="main_icon" class="glyphicon glyphicon-download"></span>
	           </li>
	      </ul> 
	      
	      <div class="element-text"></div>
	      
	      <hr />
	      
 	      <div class="btn-action-container"> 

 		      <a operation="del" class="btn btn-sm btn-danger btn-action" href="#">Excluir</a>
 		      <a operation="edit" class="btn btn-sm btn-info btn-action edit-btn" href="#">Editar</a>

<!-- 		      Split button -->
 
				 <div class="btn-group right">
				   <button type="button" class="btn btn-sm btn-success ">Adicionar Abaixo</button>
				   <button type="button" class="btn btn-sm btn-success dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
				     <span class="caret"></span>
				     <span class="sr-only">Toggle Dropdown</span>
				   </button>
				   <ul class="dropdown-menu" role="menu">
				     <li><a href="#" class="add-btn" elementAdd="Announce">Announce</a></li>
				     <li><a href="#" class="add-btn" elementAdd="Menu">Menu</a></li>
				     <li><a href="#" class="add-btn" elementAdd="Choice">Choice</a></li>
				     <li><a href="#" class="add-btn" elementAdd="Decision">Decision</a></li>
				     <li><a href="#" class="add-btn" elementAdd="PromptCollect">PromptCollect</a></li>
				   </ul>
 	      		</div>
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
          <a class="navbar-brand" href="edit.html"><img src="img/logoGVTColorida.png" /> Fluxogramas - Nova URA</a>
        </div>
        
        <div id="navbar" class="collapse navbar-collapse pull-right">
          <ul class="nav navbar-nav">
          <!-- 
          	<li>
          		<select id="base" style="width: 200px;" name="base" class="select2 base-select">
          			<option value="DPHO">Desenvolvimento</option>
          			<option value="QAPHO1">QA1</option>
          			<option value="QAPHO3">QA3</option>
          		</select>
          	</li>
          	 -->
          </ul>			
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div style="margin-top:50px;height: 100%;min-height: 520px;">
    <!-- <div class="container" style="margin-top:50px;">/.container -->
		<iframe src="Main" class="col-sm-12" style="min-height:550px;"></iframe>
	</div>
    <!-- </div>/.container -->
     		</div>
        </div>
      </div>
</div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="select2/select2.min.js"></script>
	<script src="select2/select2_locale_pt-BR.js"></script>
	<script src="js/system/app.js"></script>
  </body>
</html>