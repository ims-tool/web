var Grammar = {
	
		init: function ()
		{
			this.loadGrammars();
			this.loadVersions();
			this.saveGrammar();
			this.editGrammar();
			this.addGrammar();
		},
		
		cleanGrammar: function ()
		{
			$('.form-grammar').find('[name="id"]').val('');
			$('.form-grammar').find('[name="name"]').val('');
			$('.form-grammar').find('[name="description"]').val('');
			$('.form-grammar').find('[name="type"]').val('');
			$('.form-grammar').find('[name="sizemax"]').val('');
			$('.form-grammar').find('[name="sizemin"]').val('');
			$('.form-grammar').find('[name="versionid"]').val('');
			$('input.selecao-version').select2(Util.search('Version'));
			trySelectVersion();
		},
		
		addGrammar: function ()
		{
			$('#grammarModal .btn-add-element').unbind().click(function (){
				Grammar.cleanGrammar();
			});
		},
		
		editGrammar: function ()
		{
			$('#grammarModal .btn-edit-element').unbind().click(function (){
				var id = $('#grammarModal input.selecao-grammar').val();
				Grammar.loadGrammar(id);
			});
		},
		
		showAdd: function ()
		{
			$('#grammarModal').modal();
			this.cleanGrammar();
			$('#grammarModal .row').css('display','none');
			Grammar.init();
		},
		
		show: function ()
		{
			$('#grammarModal').css('display','block');
			$('#grammarModal').modal();
			this.cleanGrammar();
			Grammar.init();
		},
		
		
		loadGrammar: function (id)
		{
			$.get('Crud?action=loadElement&id='+id+'&type=Grammar',function (grammar){
				$('.form-grammar').find('[name="id"]').val(grammar.id);
				$('.form-grammar').find('[name="name"]').val(grammar.name);
				$('.form-grammar').find('[name="description"]').val(grammar.description);
				$('.form-grammar').find('[name="type"]').val(grammar.type);
				$('.form-grammar').find('[name="sizemax"]').val(grammar.sizemax);
				$('.form-grammar').find('[name="sizemin"]').val(grammar.sizemin);
				
				$.get('Crud?action=loadElement&id='+grammar.versionid+'&type=Version',function (version){
					$('.form-grammar input.selecao-version').select2('data',{id: version.id, text: version.description});
				});
			});
		},
		
		loadGrammars: function ()
		{
			$('input.selecao-grammar').select2(Util.search('Grammar'));
		},
		
		loadVersions: function ()
		{
			$('input.selecao-version').select2(Util.search('Version'));
		},
		
		validate: function ()
		{
			var msg = '';
			if($('.form-grammar').find('[name="name"]').val() == '')
				msg += 'Informe o campo nome <br />';
			if($('.form-grammar').find('[name="description"]').val() == '')
				msg += 'Informe o campo descrição <br />';
			if($('.form-grammar').find('[name="type"]').val() == '')
				msg += 'Informe o campo tipo <br />';
			if($('.form-grammar').find('[name="sizemin"]').val() == '')
				msg += 'Informe o campo tamanho mínimo <br />';
			if($('.form-grammar').find('[name="sizemax"]').val() == '')
				msg += 'Informe o campo tamanho máximo <br />';
			if($('.form-grammar').find('[name="versionid"]').val() == '')
				msg += 'Informe o campo versão <br />';
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else
				return true;
		},
		
		saveGrammar: function ()
		{
			$('.btn-save-grammar').unbind().click(function (){
				var data = $('.form-grammar').serialize();
				
				if(Grammar.validate())
				$.post("Crud?action=save&elementType=Grammar&"+data,function (){
					APP.success("Grammar salvo com sucesso!");
					$('#grammarModal').modal('hide');
				});
			});
		}
};


$(document).ready(function (){
	Grammar.init();
});