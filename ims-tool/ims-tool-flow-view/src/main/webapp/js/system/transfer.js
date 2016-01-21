Transfer = {
	
		init: function ()
		{
			this.loadTransfers();
			this.loadTags();
			this.loadTransferRules();
			this.loadVersions();
			this.saveTransfer();
			this.addTransfer();
			this.editTransfer();
		},
		
		show: function ()
		{
			$('#transferModal').modal();
			this.cleanTransfer();
		},
		
		addTransfer: function ()
		{
			
			$('#transferModal .btn-add-element').unbind().click(function (){
				
				$('input.selecao-transfer').select2(Util.search("Transfer"));
				Transfer.cleanTransfer();
			});
		},
		
		editTransfer: function ()
		{
			$('#transferModal .btn-edit-element').unbind().click(function (){
				var id = $('input.selecao-transfer').val();
				Transfer.loadTransfer(id);
			});
		},
		
		cleanTransfer: function ()
		{
			$('.form-transfer').find('[name="id"]').val('');
			$('.form-transfer').find('[name="name"]').val('');
			$('.form-transfer').find('[name="description"]').val('');
			
			$('.form-transfer').find('[name="transferruleid"]').val('');
			$('.form-transfer').find('[name="tag"]').val('');
			$('.form-transfer').find('[name="versionid"]').val('');
			
			$('.form-transfer input.selecao-logpontos').select2(Util.search("Logpontos"));
			$('.form-transfer input.selecao-tag').select2(Util.search("Tag"));
			$('.form-transfer input.selecao-version').select2(Util.search("Version"));
			trySelectVersion();
		},
		
		loadTransfer: function (id)
		{
			$.get('Crud?action=loadElement&id='+id+'&type=Transfer',function (transfer){
				$('.form-transfer').find('[name="id"]').val(transfer.id);
				$('.form-transfer').find('[name="name"]').val(transfer.name);
				$('.form-transfer').find('[name="description"]').val(transfer.description);
				
				$.get('Crud?action=loadElement&id='+transfer.transferruleid+'&type=LogPontos',function (transferrule){
					$('.form-transfer input.selecao-logpontos').select2('data',{id: transferrule.id, text: transferrule.nome});
				});
				
				$.get('Crud?action=loadElement&id='+transfer.tag+'&type=Tag',function (tag){
					$('.form-transfer input.selecao-tag').select2('data',{id: tag.id, text: tag.description});
				});
				
				$.get('Crud?action=loadElement&id='+transfer.versionid+'&type=Version',function (version){
					$('.form-transfer input.selecao-version').select2('data',{id: version.id, text: version.description});
				});
				
			});
		},
		
		loadTransfers: function ()
		{	
			$('input.selecao-transfer').select2(Util.search("Transfer"));
		},
		
		loadTags: function ()
		{	
			$('.form-transfer input.selecao-tag').select2(Util.search("Tag"));
		},
		
		loadVersions: function ()
		{	
			$('.form-transfer input.selecao-version').select2(Util.search("Version"));
		},
		
		loadTransferRules: function ()
		{
			
			$('.form-transfer input.selecao-logpontos').select2(Util.search("Logpontos"));
		},
		
		validate: function ()
		{
			var msg = '';
			if($('.form-transfer').find('[name="name"]').val() == '')
				msg += 'Informe o campo nome<br />';
			if($('.form-transfer').find('[name="description"]').val() == '')
				msg += 'Informe o campo descrição<br />';
			if($('.form-transfer input.selecao-logpontos').val() == "")
				msg += 'Informe o campo regra de transferência<br />';
			if($('.form-transfer input.selecao-tag').val() == "")
				msg += 'Informe o campo tag<br />';
			if($('.form-transfer input.selecao-version').val() == "")
				msg += 'Informe o campo versão<br />';
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else
				return true;
		},
		
		saveTransfer: function ()
		{
			$('.btn-save-transfer').unbind().click(function (){
				var data = $('.form-transfer').serialize();
				
				if(Transfer.validate())
					$.post("Crud?action=saveElement&elementType=Transfer&"+data,function (){
						APP.success("Transfer salvo com sucesso!");
						$('#transferModal').modal('hide');
					});
			});
		}
};


$(document).ready(function (){
	Transfer.init();
});