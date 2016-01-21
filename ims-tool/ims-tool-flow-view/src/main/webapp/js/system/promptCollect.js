var PromptCollect = {
		
		init: function ()
		{
			this.loadPromptCollects();
			this.loadTags();
			this.loadPrompts();
			this.loadNoinputs();
			this.loadNomatches();
			this.loadGrammars();
			this.loadVersions();
			this.savePromptCollect();
			this.editPromptCollect();
			this.addPromptCollect();
		},
		
		loadPromptCollects: function ()
		{
			$('#promptCollectModal input.selecao-promptcollect').select2(Util.search('PromptCollect'));
		},
		
		loadTags: function ()
		{
			$('.form-promptcollect input.selecao-tag').select2(Util.search('Tag'));
		},
		
		loadPrompts: function ()
		{
			$('.form-promptcollect input.selecao-prompt').select2(Util.search('Prompt'));
		},
		
		loadNomatches: function ()
		{
			
			$('.form-promptcollect input.selecao-nomatch').select2({
				minimumInputLength: 2,
			    ajax: {
			      url: "Crud?action=searchNomatchInput&type=nomatch",
			      dataType: 'json',
			      data: function (term, page) {
			        return {
			          q: term
			        };
			      },
			      results: function (data, page) {
			        return { results: data };
			      }
			    }
			});
		},
		
		loadNoinputs: function ()
		{
			$('.form-promptcollect input.selecao-noinput').select2({
				minimumInputLength: 2,
			    ajax: {
			      url: "Crud?action=searchNomatchInput&type=noinput",
			      dataType: 'json',
			      data: function (term, page) {
			        return {
			          q: term
			        };
			      },
			      results: function (data, page) {
			        return { results: data };
			      }
			    }
			});
		},
		
		loadGrammars: function ()
		{
			$('.form-promptcollect input.selecao-grammar').select2(Util.search('Grammar'));
		},
		
		loadVersions: function ()
		{
			$('.form-promptcollect input.selecao-version').select2(Util.search('Version'));
		},
		
		cleanPromptCollect: function ()
		{
			$('.form-promptcollect').find('[name="id"]').val('');
			$('.form-promptcollect').find('[name="name"]').val('');
			$('.form-promptcollect').find('[name="description"]').val('');
			$('.form-promptcollect').find('[name="grammar"]').val('');
			$('.form-promptcollect').find('[name="prompt"]').val('');
			$('.form-promptcollect').find('[name="noinput"]').val('');
			$('.form-promptcollect').find('[name="nomatch"]').val('');
			$('.form-promptcollect').find('[name="fetchtimeout"]').val('');
			$('.form-promptcollect').find('[name="interdigittimeout"]').val('');
			$('.form-promptcollect').find('[name="terminatingtimeout"]').val('');
			$('.form-promptcollect').find('[name="tag"]').val('');
			$('.form-promptcollect').find('[name="versionid"]').val('');
			
			this.loadPromptCollects();
			
			this.loadGrammars();
			this.loadPrompts();
			this.loadNoinputs();
			this.loadNomatches();
			this.loadTags();
			this.loadVersions();
			
			trySelectVersion();
			
		},
		
		addPromptCollect: function ()
		{
			$('#promptCollectModal .btn-add-element').unbind().click(function (){
				PromptCollect.cleanPromptCollect();
			});
		},
		
		editPromptCollect: function ()
		{
			$('#promptCollectModal .btn-edit-element').unbind().click(function (){
				var id = $('#promptCollectModal input.selecao-promptcollect').val();
				PromptCollect.loadPromptCollect(id);
			});
		},
		
		show: function ()
		{
			$('#promptCollectModal').modal();
			$('#promptCollectModal .row').css('display','block');
			this.cleanPromptCollect();
		},
		
		showAdd: function ()
		{
			$('#promptCollectModal').modal();
			PromptCollect.cleanPromptCollect();
			$('#promptCollectModal .row').css('display','none');
			this.cleanPromptCollect();
		},
		
		loadPromptCollect: function (id)
		{
			$.get('Crud?action=loadElement&id='+id+'&type=PromptCollect',function (promptCollect){
				PromptCollect.cleanPromptCollect();
				$('.form-promptcollect').find('[name="id"]').val(promptCollect.id);
				$('.form-promptcollect').find('[name="name"]').val(promptCollect.name);
				$('.form-promptcollect').find('[name="description"]').val(promptCollect.description);
				
				$('.form-promptcollect').find('[name="fetchtimeout"]').val(promptCollect.fetchtimeout);
				$('.form-promptcollect').find('[name="interdigittimeout"]').val(promptCollect.interdigittimeout);
				$('.form-promptcollect').find('[name="terminatingtimeout"]').val(promptCollect.terminatingtimeout);
				
				$.get('Crud?action=loadElement&id='+promptCollect.grammar+'&type=Grammar',function (grammar){
					$('.form-promptcollect input.selecao-grammar').select2('data',{id: grammar.id, text: grammar.name});
				});
				
				$.get('Crud?action=loadElement&id='+promptCollect.prompt+'&type=Prompt',function (prompt){
					$('.form-promptcollect input.selecao-prompt').select2('data',{id: prompt.id, text: prompt.name});
				});
				
				$.get('Crud?action=loadElement&id='+promptCollect.nomatch+'&type=Nomatchinput',function (nomatchinput){
					$('.form-promptcollect input.selecao-nomatch').select2('data',{id: nomatchinput.id, text: nomatchinput.name});
				});
				
				$.get('Crud?action=loadElement&id='+promptCollect.noinput+'&type=Nomatchinput',function (nomatchinput){
					$('.form-promptcollect input.selecao-noinput').select2('data',{id: nomatchinput.id, text: nomatchinput.name});
				});
				
				$.get('Crud?action=loadElement&id='+promptCollect.tag+'&type=Tag',function (tag){
					$('.form-promptcollect input.selecao-tag').select2('data',{id: tag.id, text: tag.description});
				});
				
				$.get('Crud?action=loadElement&id='+promptCollect.versionid+'&type=Version',function (version){
					$('.form-promptcollect input.selecao-version').select2('data',{id: version.id, text: version.description});
				});
			});
		},
		
		validate: function ()
		{
			var msg = '';
			
			if($('.form-promptcollect').find('[name="name"]').val() == '')
				msg += 'Informe o campo nome <br />';
			if($('.form-promptcollect').find('[name="description"]').val() == '')
				msg += 'Informe o campo descrição<br />';
			if($('.form-promptcollect').find('[name="nomatch"]').val() == '')
				msg += 'Informe o campo nomatch<br />';
			if($('.form-promptcollect').find('[name="noinput"]').val() == '')
				msg += 'Informe o campo noinput<br />';
			if($('.form-promptcollect').find('[name="fetchtimeout"]').val() == '')
				msg += 'Informe o campo fetch time out<br />';
			if($('.form-promptcollect').find('[name="interdigittimeout"]').val() == '')
				msg += 'Informe o campo inter digit time out<br />';
			if($('.form-promptcollect').find('[name="terminatingtimeout"]').val() == '')
				msg += 'Informe o campo terminating time out<br />';
			if($('.form-promptcollect input.selecao-tag').val() == '')
				msg += 'Informe o campo tag<br />';
			if($('.form-promptcollect input.selecao-version').val() == '')
				msg += 'Informe o campo versão<br />';
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else
				return true;
		},
		
		savePromptCollect: function ()
		{
			$('.btn-save-promptcollect').unbind().click(function (){
				var data = $('.form-promptcollect').serialize();
				
				if(PromptCollect.validate())
					$.post("Crud?action=saveElement&elementType=PromptCollect&"+data,function (){
						APP.success("PromptCollect salvo com sucesso!");
						$('#promptCollectModal').modal('hide');
					});
			});
		}
};


$(document).ready(function (){
	PromptCollect.init();
});