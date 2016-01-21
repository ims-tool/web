var Nomatchinput = {
		
		init: function ()
		{
			this.loadPrompts();
			this.loadNomatchinputs();
			this.loadVersions();
			this.saveNomatchinput();
			this.editNomatchinput();
			this.addNomatchinput();
		},
		
		loadPrompts: function ()
		{
			$('input.selecao-prompt').select2(Util.search('Prompt'));
		},
		
		loadTags: function ()
		{
			$('input.selecao-tag').select2(Util.search('Tag'));
		},
		
		loadNomatchinputs: function ()
		{
			$('input.selecao-nomatchinput').select2(Util.search('nomatchinput'));
		},
		
		loadVersions: function ()
		{
			$('.form-nomatchinput input.selecao-version').select2(Util.search('Version'));
		},
		
		cleanNomatchinput: function ()
		{
			$('.form-nomatchinput').find('[name="id"]').val('');
			$('.form-nomatchinput').find('[name="type"]').val('');
			$('.form-nomatchinput').find('[name="threshold"]').val('');
			$('.form-nomatchinput').find('[name="prompt"]').val('');
			$('.form-nomatchinput').find('[name="name"]').val('');
			$('.form-nomatchinput').find('[name="tag"]').val('');
			$('.form-nomatchinput').find('[name="versionid"]').val('');
			
			this.loadPrompts();
			this.loadTags();
			this.loadVersions();
			trySelectVersion();
		},
		
		addNomatchinput: function ()
		{
			$('#nomatchinputModal .btn-add-element').unbind().click(function (){
				Nomatchinput.cleanNomatchinput();
			});
		},
		
		editNomatchinput: function ()
		{
			$('#nomatchinputModal .btn-edit-element').unbind().click(function (){
				var id = $('#nomatchinputModal input.selecao-nomatchinput').val();
				Nomatchinput.loadNomatchinput(id);
			});
		},
		
		show: function ()
		{
			$('#nomatchinputModal').modal();
			$('#nomatchinputModal .row').css('display','block');
		},
		
		showAdd: function ()
		{
			$('#nomatchinputModal').modal();
			Nomatchinput.cleanNomatchinput();
			$('#nomatchinputModal .row').css('display','none');
		},
		
		loadNomatchinput: function (id)
		{
			$.get('Crud?action=loadElement&id='+id+'&type=Nomatchinput',function (nomatchinput){
				
				$('.form-nomatchinput').find('[name="id"]').val(nomatchinput.id);
				
				$('.form-nomatchinput').find('[name="type"]').val(nomatchinput.type);
				$('.form-nomatchinput').find('[name="threshold"]').val(nomatchinput.threshold);
				
				$.get('Crud?action=loadElement&id='+nomatchinput.prompt+'&type=Prompt',function (prompt){
					$('.form-nomatchinput input.selecao-prompt').select2('data',{id: prompt.id, text: prompt.name});
				});
				
				$('.form-nomatchinput').find('[name="name"]').val(nomatchinput.name);
				
				$.get('Crud?action=loadElement&id='+nomatchinput.tag+'&type=Tag',function (tag){
					$('.form-nomatchinput input.selecao-tag').select2('data',{id: tag.id, text: tag.description});
				});
				
				$.get('Crud?action=loadElement&id='+nomatchinput.versionid+'&type=Version',function (version){
					$('.form-nomatchinput input.selecao-version').select2('data',{id: version.id, text: version.description});
				});
			});
		},
		
		validate: function ()
		{
			var msg = '';
			
			if($('.form-nomatchinput').find('[name="type"]').val() == '')
				msg += 'Informe o campo tipo<br />';
			if($('.form-nomatchinput').find('[name="threshold"]').val() == '')
				msg += 'Informe o campo threshold<br />';
			if($('.form-nomatchinput').find('[name="prompt"]').val() == '')
				msg += 'Informe o campo prompt<br />';
			if($('.form-nomatchinput').find('[name="name"]').val() == '')
				msg += 'Informe o campo nome<br />';
			if($('.form-nomatchinput').find('[name="tag"]').val() == '')
				msg += 'Informe o campo tag<br />';
			if($('.form-nomatchinput').find('[name="versionid"]').val() == '')
				msg += 'Informe o campo versão<br />';
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else
				return true;
		},
		
		saveNomatchinput: function ()
		{
			$('.btn-save-nomatchinput').unbind().click(function (){
				
				var data = $('.form-nomatchinput').serialize();
				
				if(Nomatchinput.validate())
					$.post("Crud?action=save&elementType=NoMatchInput&"+data,function (){
						APP.success("Nomatchinput salvo com sucesso!");
						$('#nomatchinputModal').modal('hide');
					});
			});
		}
};


$(document).ready(function (){
	Nomatchinput.init();
});