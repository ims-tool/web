var EdicaoVisivel = false;

function trySelectVersion ()
{
	if(localStorage.getItem("versao") != null){
		savedVersion = JSON.parse(localStorage.getItem("versao"));
		$('.selecao-version').select2("data", {id : savedVersion.id, text: savedVersion.description});
	}
}

var Editor = {
		nextformEvent: function ()
		{
			$('.editar-nextform').unbind().click(function (){
				
				$('#isDefault').val('0');
				var id = $(this).attr('elementId');
				var next = $(this).attr('elementNext');
				var type = $(this).attr('elementType');
				
				$('.btn-save-nextform').attr('elementId',id);
				$('.btn-save-nextform').attr('elementType',type);
				
				$('#modalNextForm').modal();
				
				$.get('Crud?action=loadElement&id='+next+'&type=Form',function (form){
					$('#nextform').select2("data", {id : form.id, text: form.name});
				});
				
				trySelectVersion();
			});
			
			$('.editar-defaultnextform').unbind().click(function (){
				$('#isDefault').val('1');
				var id = $(this).attr('elementId');
				var next = $(this).attr('elementNext');
				var type = $(this).attr('elementType');
				
				$('.btn-save-nextform').attr('elementId',id);
				$('.btn-save-nextform').attr('elementType',type);
				
				$('#modalNextForm').modal();
				
				$.get('Crud?action=loadElement&id='+next+'&type=Form',function (form){
					$('#nextform').select2("data", {id : form.id, text: form.name});
				});
				
				trySelectVersion();
			});
		}
};

var APP = {
		
		editor: Editor,
		elementId: 0,
		elementType: '',
		
		init: function ()
		{
			var self = this;
			$('.level-select').select2();
			$('.tempo-select').select2();
			//$('.base-select').select2();
			self.loadForms();
			self.loadNextForms();
			self.formSelect();
			self.levelSelect();
			self.tempoSelect();
			//self.baseSelect();
			self.buttonActions();
			self.download();
			self.adicionar();
			self.editar();
			self.salvarForm();
			self.salvarNextForm();
			self.reloadPeriodo();
			self.callReport();
			
			$('.selecao-version').change(function (){
				var id = $(this).val();
				
				$.get('Crud?action=loadElement&id='+id+'&type=Version',function (version){  
					
					localStorage.setItem("versao",JSON.stringify(version));
				});
				
			});
		},
		
		showLoading: function ()
		{
			$('.loading').show();
		},
		
		hideLoading: function ()
		{
			$('.loading').hide();
		},
		
		message: function (msg,claz)
		{
			$('.userMessage').html(msg);
			$('.userMessage').removeClass("div-success");
			$('.userMessage').removeClass("div-warning");
			$('.userMessage').removeClass("div-danger");
			$('.userMessage').addClass(claz)
			
			$('.userMessage').show('slow',function (){ setTimeout(function (){$('.userMessage').hide('slow'); },3000); });
		},
		
		success: function (msg)
		{
			APP.message(msg,"div-success");
		},
		
		error: function (msg)
		{
			APP.message(msg,"div-danger");
		},
		
		warning: function (msg)
		{
			APP.message(msg,"div-warning");
		},
		
		download: function ()
		{
			$('.yed-download').click(function (){
				var id = $(this).attr('form');
				var level = $('#level').val();
				window.location.href = 'Main?action=export&id='+id+'&level='+level;
				;
			});
		},
		
		adicionar: function ()
		{
			$('.add-btn').click(function (){
				
				var id = null;
				var text = "Adicionar";
				var type = $(this).attr('elementAdd');
				var parent = self.elementId;
				var parentType = self.elementType;
				self.loadHtmlForm(id,type,"add",parent,parentType);
				$('.modal-title').text(text + " " + type);
			});
		},
		
		editar: function ()
		{
			$('.edit-btn').click(function (){
				
				var id = $(this).attr('elementid');
				var type = $(this).attr('elementtype');
				APP.showEditElement(id,type);
			});
		},
		
		showEditElement: function (id,type)
		{
			if(type.toUpperCase() == "MENU" ) {
				console.log('IRRAAAAA');
				Menu.show();
				Menu.loadMenu(id);
			} else if ( type.toUpperCase() == "CHOICE" ) {
				$.get('Crud?action=loadElement&id='+id+'&type=Choice',function (choice){
					Menu.show();
					Menu.loadMenu(choice.menu);
				});
			} else if ( type.toUpperCase() == "NOMATCHINPUT" ) {
				Nomatchinput.show();
				Nomatchinput.loadNomatchinput(id);
			} else if ( type.toUpperCase() == "ANNOUNCE" ) {
				Announce.show();
				$('.div-container-announce').css('display','block');
				Announce.loadAnnounce(id);
			} else if ( type.toUpperCase() == "DECISION" ) {
				Decision.show();
				Decision.loadDecision(id);
			} else if ( type.toUpperCase() == "DECISIONGROUP" ) {
				$.get('Crud?action=loadElement&id='+id+'&type=DecisionGroup',function (decisionGroup){
					Decision.show();
					Decision.loadDecision(decisionGroup.decisionid);
				});
			} else if( type.toUpperCase() == "FLOW" ){
				Flow.show();
				Flow.loadFlow(id);
			} else if( type.toUpperCase() == "TRANSFER" ){
				Transfer.show();
				Transfer.loadTransfer(id);
			} else if ( type.toUpperCase() == "OPERATION" ) {
				Operation.show();
				Operation.cleanOperation();
				Operation.loadOperation(id);
			} else if ( type.toUpperCase() == "DISCONNECT" ) {
				Disconnect.show();
				Disconnect.loadDisconnect(id);
			} else if ( type.toUpperCase() == "PROMPTCOLLECT" ) {
				PromptCollect.show();
				PromptCollect.loadPromptCollect(id);
			}
		},
		
		salvarForm: function ()
		{
			$('.btn-save').click(function (){
				var data = $('.modal-body').find('form').serialize();
				
				$.ajax({
					url: 'Main?action=saveForm',
					data: data,
					type: 'post',
					success: function (res){
						$('#myModal').modal('hide');
						window.frames[0].window.location.reload();
					}
				});
			});
		},
		
		salvarNextForm: function ()
		{
			$('.btn-save-nextform').click(function (){
				
				var data = $('#nextform').val();
				var version = $('#nextformVersion').val();
				var id = $(this).attr('elementId');
				var type = $(this).attr('elementType');
				
				if(data == ''){
					APP.error("Informe o campo nextform");
					return;
				} else if (version == "") {
					APP.error("Informe o campo versão");
					return;
				}
				
				var url = '';
				if($('#isDefault').val() == '1')
					url = 'action=saveNextFormDefault&nextform='+data+'&id='+id+'&type='+type+'&version='+version;
				else 
					url = 'action=saveNextForm&nextform='+data+'&id='+id+'&type='+type+'&version='+version;
				$.ajax({
					url: 'Main',
					data: url,
					type: 'post',
					success: function (res){
						$('#modalNextForm').modal('hide');
						window.frames[0].window.location.reload();
					}
				});
			});
		},
		
		openCondition: function (id)
		{
			window.open('Condition?id='+id);
		},
		
		defaultnextformClick: function ()
		{
			$('.defaultnextform').unbind().click(function (){
				var id = $(this).text();
				APP.frameLoad(id);
			});
		},
		
		showFrameCondition: function (id){
			$('#frameConditionModal').modal();
			$('.modal-title-frame').text("Condition");
			$.get('Crud?action=loadElement&type=Choice&id='+id,function (choice){
				document.getElementById('frameCondition').src= 'http://'+window.location.host+window.location.pathname+'Main?action=showCondition&condition='+choice.condition;
			});
		},
		
		showFrameFlow: function (id){
			$('#frameConditionModal').modal();
			$('.modal-title-frame').text("Flow");
			document.getElementById('frameCondition').src= 'http://'+window.location.host+window.location.pathname+'Main?action=showFlow&form='+id;
		},
		
		getTempoValue: function ()
		{
			var tempo = $('#tempo').val();
			var dataIni = $('[name="periodoDe"]').val();
			var dataAte = $('[name="periodoAte"]').val();
			if(tempo == '' && dataIni != '' && dataAte != '')
				tempo = dataIni + ',' + dataAte;
			
			return tempo;
		},
		
		reloadPeriodo: function ()
		{
			var self = this;
			$('.reloadPeriodo').click(function (){
				self.frameLoad($('#forms').val());
			});
		},
		
		frameLoad: function (formId)
		{
			var id = formId;
			var level = $('#level').val();
			var tempo = this.getTempoValue();
			//var base = $('#base').val();
			//window.frames[0].window.location.href = 'Main?form='+id+'&level='+level+'&base='+base;
			this.showLoading();
			window.frames[0].window.location.href = 'Main?form='+id+'&level='+level+'&tempo='+tempo;
		},
		
		reload: function ()
		{
			this.showLoading();
			window.frames[0].window.location.reload();
		},
		
		zoomIn: function() { 
			window.frames[0].zoomIn(); 
		},
		
		zoomOut: function() { 
			window.frames[0].zoomOut(); 
		},
		
		back: function ()
		{
			window.frames.window.history.back(-1);
		},
		
		loadForms: function (){
			$('.forms-select').select2(Util.search("Form"));
		},
		
		loadNextForms: function (){
			$('.nextform-select').select2(Util.search("Form"));
		},
		
		_select: function (name)
		{
			var self = this;
			$(name).change(function (){
				var id = $('#forms').val();
				var level = $('#level').val();
				var tempo = self.getTempoValue();
				//var base = $('#base').val();
				//window.frames[0].window.location.href = 'Main?form='+id+'&level='+level+'&base='+base;
				self.showLoading();
				window.frames[0].window.location.href = 'Main?form='+id+'&level='+level+'&tempo='+tempo;
				
			});
		},
		
		formSelect: function ()
		{
			this._select('input.forms-select');
		},
		
		/*
		baseSelect: function ()
		{
			this._select('.base-select');
		},
		*/
		
		levelSelect: function ()
		{
			this._select('.level-select');
		},
		
		tempoSelect: function ()
		{
			this._select('.tempo-select');
		},
		
		buttonActions: function ()
		{
			var self = this;
			$('.btn-action').click(function (){
				//var id = $(this).attr('elementid');
				//var type = $(this).attr('elementtype');
				//var text = $(this).text();
				//var operation = $(this).attr('operation');
				//$('.modal-title').text(text + " " + type);
				//self.loadHtmlForm(id,type,operation);
				
			});
		},
		loadHtmlForm: function (id,type,operation,parent,parentType)
		{
			$.get('Main?action=loadFormFields&id='+id+"&type="+type+"&operation="+operation,function (res){
				var html = '<form action="Main?action=saveForm">';
				html += '<input type="hidden" name="operation" value="'+operation+'" />';
				html += '<input type="hidden" name="type" value="'+type+'" />';
				html += '<input type="hidden" name="parent" value="'+parent+'" />';
				html += '<input type="hidden" name="parentType" value="'+parentType+'" />';
				html += res;
				//html += $('#forms').html();
				html += '</form>';
				
				var select = '<div class="form-group">';
				select += '<label for="recipient-name" class="control-label">Abaixo de:</label>';
				select += '<select name="abaixode" class="select2 form-control">'+$('#forms').html()+'</select>';
				select += '</div>';
				
				$('.modal-body').html(html);
				try{
					$('.select2').select2();
				} catch(e){}
				//$(select).appendTo('.modal-body');
				
				//try{ $('.select2').select2(); } catch(e){}
				
				$('#myModal').modal('show');
			});
			
		},
		
		menu: function ()
		{
			$('#newElement').modal('hide');
			Menu.show();
		},
		
		transfer: function ()
		{
			$('#newElement').modal('hide');
			$('#transferModal').modal();
			Transfer.show();
		},
		
		grammar: function ()
		{
			$('#newElement').modal('hide');
			$('#grammarModal').modal();
			Grammar.show();
		},
		
		announce: function ()
		{
			$('#newElement').modal('hide');
			$('#announceModal').modal();
			Announce.init();
		},
		
		disconnect: function ()
		{
			$('#newElement').modal('hide');
			Disconnect.show();
		},
		
		flow: function ()
		{
			$('#newElement').modal('hide');
			Flow.show();
		},
		
		operation : function ()
		{
			$('#newElement').modal('hide');
			$('#operationModal').modal();
			Operation.cleanOperation();
			Operation.init();
		},
		
		condition : function ()
		{
			$('#newElement').modal('hide');
			Condition.init();
			Condition.show();
		},
		
		decision: function ()
		{
			$('#newElement').modal('hide');
			Decision.init();
			Decision.show();
		},
		
		tag: function ()
		{
			$('#newElement').modal('hide');
			$('#tagModal').modal();
			Tag.show();
		},
		
		promptCollect: function ()
		{
			$('#newElement').modal('hide');
			PromptCollect.show();
		},
		
		nomatchinput: function ()
		{
			$('#newElement').modal('hide');
			Nomatchinput.show();
		},
		
		version: function ()
		{
			$('#newElement').modal('hide');
			Version.show();
		},
		
		prompt: function ()
		{
			$('#newElement').modal('hide');
			$('#promptModal').modal();
			$('.form-prompt .row-buttons').css('display','block');
			$('#promptModal .form-container-prompt').css('display','none');
			Prompt.init();
		},
		
		editFlow: function ()
		{
			//window.open("http://localhost:8888/telefonia-ivr-flow-editor/Edit");
			$('#newElement').modal();
		},
		
		callReport: function (){
			$('.enableReport').change(function (){
				if($('.enableReport:checked').length > 0)
				{
					$('.callReportContainer').css('display','block');
				} else {
					$('.tempo-select').select2('val','');
					$('[name="periodoDe"]').val('');
					$('[name="periodoAte"]').val('');
					$('.callReportContainer').css('display','none');
				}
			});
			
			$( ".modal-dialog" ).draggable();
			$( ".modal-dialog" ).resizable();
			
			$( ".dropdown-menu-config" ).draggable();
			$( ".dropdown-menu-config" ).resizable();
			
			$('.definirPeriodo').click(function() {
				$('.periodoPersonalizado').toggle();
				
				if($('.periodoPersonalizado').is(":visible"))
					$('.tempo-select').select2('val','');
			})
			
			$('.tempo-select').change(function (){
				if($('.periodoPersonalizado').is(":visible")){
					$('.periodoPersonalizado').hide();
					$('[name="periodoDe"]').val('');
					$('[name="periodoAte"]').val('');
				}
			});
			
			$('[name="periodoDe"]').datetimepicker({
		        language:  'pt-BR',
		        weekStart: 1,
		        todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				forceParse: 0,
		        showMeridian: 1,
		        format: "dd/mm/yyyy hh:ii"
		    });
			
			$('[name="periodoAte"]').datetimepicker({
		        language:  'pt-BR',
		        weekStart: 1,
		        todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				forceParse: 0,
		        showMeridian: 1,
		        format: "dd/mm/yyyy hh:ii"
		    });
		}
};
		
$(document).ready( function (){
	APP.init();
	
	$('.sidebar-brand .glyphicon-remove').click(function (){
		$("#wrapper").toggleClass("active");
	});
	
	$('[type="number"]').keydown(Util.onlyNumbers);
	
	if(!EdicaoVisivel)
	{
		$('.btn-editar-fluxo').css('display','none');
		$('.edit-btn').css('display','none');
	} else {
		$('.btn-editar-fluxo').css('display','block');
		$('.edit-btn').css('display','block');
	}
});



$(document).ready(function (){
	$('[name="name"]').attr('maxlength',30);
	document.oncontextmenu = function(e){
		 var evt = new Object({keyCode:93});
		 stopEvent(e);
	}
});

function stopEvent(event){
	 if(event.preventDefault != undefined)
	  event.preventDefault();
	 if(event.stopPropagation != undefined)
	  event.stopPropagation();
}