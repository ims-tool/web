<%@ include file="/WEB-INF/views/template/includes.jsp" %>
<!-- Static navbar -->
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<c:url value="/" /> ">
      	<img style="min-width: 100px" width="100px" class="img-responsive" alt="IVR Admin" src="<c:url value="/static/img/logo-vivo-transp.png" />" >
      </a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Administração <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="<c:url value="/horario/" />">Horário</a></li>
            <li><a href="<c:url value="/mensagens/" />">Mensagens</a></li>
            <li><a href="<c:url value="/parameters/" />">Parameters</a></li>
          </ul>
        </li>
      </ul>
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Links <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a target="_blank" href="<c:url value="http://telefonia/recall/log_ura3.aspx" />">Log URA</a></li>
            <li><a target="_blank" href="<c:url value="http://telefonia/recall/mdin_form.aspx" />">Listagem de Forms</a></li>
            <li><a target="_blank" href="<c:url value="http://svuxpsoa124:8010/telefonia-oper-flow-view/" />">Visualização de Fluxo</a></li>
          </ul>
        </li>
      </ul>
<!--       <ul class="nav navbar-nav navbar-right"> -->
<!--         <li class="active"><a href="./">Default <span class="sr-only">(current)</span></a></li> -->
<!--         <li><a href="../navbar-static-top/">Static top</a></li> -->
<!--         <li><a href="../navbar-fixed-top/">Fixed top</a></li> -->
<!--       </ul> -->
    </div><!--/.nav-collapse -->
  </div><!--/.container-fluid -->
</nav>