var Flow = {
	
		init: function ()
		{
			this.loadForms();
			this.loadFlows();
			this.loadTags();
			this.loadVersions();
			this.saveFlow();
			this.editFlow();
			this.addFlow();
		},
		
		cleanFlow: function ()
		{
			$('.form-flow').find('[name="id"]').val('');
			$('.form-flow').find('[name="name"]').val('');
			$('.form-flow').find('[name="description"]').val('');
			$('.form-flow').find('[name="flowname"]').val('');
			$('.form-flow').find('[name="tag"]').val('');
			$('.form-flow').find('[name="versionid"]').val('');
			$('.form-flow').find('[name="tipo"]').val('');
			
			this.loadForms();
			this.loadTags();
			this.loadVersions();
			trySelectVersion();
		},
		
		addFlow: function ()
		{
			$('#flowModal .btn-add-element').unbind().click(function (){
				Flow.cleanFlow();
			});
		},
		
		editFlow: function ()
		{
			$('#flowModal .btn-edit-element').unbind().click(function (){
				var id = $('input.selecao-flow').val();
				Flow.loadFlow(id);
			});
		},
		
		show: function ()
		{
			$('#flowModal').modal();
			this.cleanFlow();
			this.init();
		},
		
		loadFlow: function (id)
		{
			$.get('Crud?action=loadElement&id='+id+'&type=Flow',function (flow){
				
				$('.form-flow').find('[name="id"]').val(flow.id);
				$('.form-flow').find('[name="name"]').val(flow.name);
				$('.form-flow').find('[name="description"]').val(flow.description);
				$('.form-flow').find('[name="tipo"]').val(flow.tipo);
				
				$.get('Crud?action=loadElement&id='+flow.flowname+'&type=Form',function (form){
					$('.form-flow input.selecao-form').select2('data',{id: form.name, text: form.name});
				});
				
				$.get('Crud?action=loadElement&id='+flow.tag+'&type=Tag',function (tag){
					$('.form-flow input.selecao-tag').select2('data',{id: tag.id, text: tag.description});
				});
				
				$.get('Crud?action=loadElement&id='+flow.versionid+'&type=Version',function (version){
					$('.form-flow input.selecao-version').select2('data',{id: version.id, text: version.description});
				});
			});
		},
		
		loadFlows: function ()
		{
			$('input.selecao-flow').select2(Util.search('Flow'));
		},
		
		loadForms: function ()
		{
			$('input.selecao-form').select2(
					//Util.search('Form')
					{
						minimumInputLength: 2,
					    ajax: {
					      url: "Crud?action=search&type=FlowName",
					      dataType: 'json',
					      data: function (term, page) {
					        return {
					          q: term,
					          flow: ''
					        };
					      },
					      results: function (data, page) {
					        return { results: data };
					      }
					    }
					}
			);
		},
		
		loadTags: function ()
		{
			$('input.selecao-tag').select2(Util.search('Tag'));
		},
		
		loadVersions: function ()
		{
			$('input.selecao-version').select2(Util.search('Version'));
		},
		
		validate: function ()
		{
			var msg = '';
			
			if($('.form-flow').find('[name="name"]').val() == '')
				msg += 'Informe o campo nome <br />';
			if($('.form-flow').find('[name="description"]').val() == '')
				msg += 'Informe o campo descrição <br />';
			if($('.form-flow').find('[name="flowname"]').val() == '')
				msg += 'Informe o campo flowname <br />';
			if($('.form-flow').find('[name="tag"]').val() == '')
				msg += 'Informe o campo tag <br />';
			if($('.form-flow').find('[name="versionid"]').val() == '')
				msg += 'Informe o campo versão <br />';
			
			if($('.form-flow').find('[name="tipo"]').val() == '')
				msg += 'Informe o campo tipo <br />';
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else {
				return true;
			}
		},
		
		saveFlow: function ()
		{
			$('.btn-save-flow').unbind().click(function (){
				var data = $('.form-flow').serialize();
				if(Flow.validate())
					$.post("Crud?action=saveElement&elementType=Flow&"+data,function (){
						APP.success("Flow salvo com sucesso!");
						$('#flowModal').modal('hide');
					});
			});
		}
};


$(document).ready(function (){
	Flow.init();
});