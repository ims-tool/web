var Disconnect = {
	
		init: function ()
		{
			this.loadDisconnects();
			this.loadTags();
			this.loadVersions();
			this.saveDisconnect();
			this.editDisconnect();
			this.addDisconnect();
		},
		
		cleanDisconnect: function ()
		{
			$('.form-disconnect').find('[name="id"]').val('');
			$('.form-disconnect').find('[name="name"]').val('');
			$('.form-disconnect').find('[name="description"]').val('');
			$('.form-disconnect').find('[name="tag"]').val('');
			$('.form-disconnect').find('[name="versionid"]').val('');
			
			this.loadTags();
			this.loadVersions();
			trySelectVersion();
		},
		
		addDisconnect: function ()
		{
			$('#disconnectModal .btn-add-element').unbind().click(function (){
				$('input.selecao-disconnect').select2(Util.search('Disconnect'));
				Disconnect.cleanDisconnect();
			});
		},
		
		editDisconnect: function ()
		{
			$('#disconnectModal .btn-edit-element').unbind().click(function (){
				var id = $('select.selecao-disconnect').val();
				Disconnect.loadDisconnect(id);
			});
		},
		
		show: function ()
		{
			$('#disconnectModal').modal();
			this.cleanDisconnect();
			this.init();
		},
		
		editDisconnect: function ()
		{
			$('#disconnectModal .btn-edit-element').unbind().click(function (){
				var id = $('input.selecao-disconnect').val();
				Disconnect.loadDisconnect(id);
			});
		},
		
		loadDisconnect: function (id)
		{
			$.get('Crud?action=loadElement&id='+id+'&type=Disconnect',function (disconnect){
				Disconnect.cleanDisconnect();
				$('.form-disconnect').find('[name="id"]').val(disconnect.id);
				$('.form-disconnect').find('[name="name"]').val(disconnect.name);
				$('.form-disconnect').find('[name="description"]').val(disconnect.description);
				
				$.get('Crud?action=loadElement&id='+disconnect.tag+'&type=Tag',function (tag){
					$('.form-disconnect input.selecao-tag').select2('data',{id: tag.id, text: tag.description});
				});
				
				$.get('Crud?action=loadElement&id='+disconnect.versionid+'&type=Version',function (version){
					$('.form-disconnect input.selecao-version').select2('data',{id: version.id, text: version.description});
				});
			});
		},
		
		loadDisconnects: function ()
		{
			$('input.selecao-disconnect').select2(Util.search('Disconnect'));
		},
		
		loadTags: function ()
		{
			$('.form-disconnect input.selecao-tag').select2(Util.search('Tag'));
		},
		
		loadVersions: function ()
		{
			$('.form-disconnect input.selecao-version').select2(Util.search('Version'));
		},
		
		validate: function ()
		{
			var msg = '';
				
			if($('.form-disconnect').find('[name="name"]').val() == '')
				msg += 'Informe o campo nome<br />';
			if($('.form-disconnect').find('[name="description"]').val() == '')
				msg += 'Informe o campo descrição<br />';
			if($('.form-disconnect input.selecao-tag').val() == '')
				msg += 'Informe o campo tag<br />';
			if($('.form-disconnect input.selecao-version').val() == '')
				msg += 'Informe o campo versão<br />';
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else {
				return true;
			}
		},
		
		saveDisconnect: function ()
		{
			$('.btn-save-disconnect').unbind().click(function (){
				var data = $('.form-disconnect').serialize();
				if(Disconnect.validate())
					$.post("Crud?action=saveElement&elementType=Disconnect&"+data,function (){
						APP.success("Grammar salvo com sucesso!");
					});
			});
		}
};


$(document).ready(function (){
	Disconnect.init();
});