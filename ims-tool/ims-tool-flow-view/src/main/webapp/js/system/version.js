var Version = {
		
		init: function ()
		{
			this.loadVersions();
			this.saveVersion();
			this.editVersion();
			this.addVersion();
		},
		
		loadVersions: function ()
		{
			$('input.selecao-version').select2(Util.search('version'));
		},
		
		cleanVersion: function ()
		{
			$('.form-version').find('[name="id"]').val('');
			$('.form-version').find('[name="description"]').val('');
			$('.form-version').find('[name="deploydate"]').val('');
		},
		
		addVersion: function ()
		{
			$('#versionModal .btn-add-element').unbind().click(function (){
				Version.cleanVersion();
			});
		},
		
		editVersion: function ()
		{
			$('#versionModal .btn-edit-element').unbind().click(function (){
				var id = $('#versionModal input.selecao-version').val();
				Version.loadVersion(id);
			});
		},
		
		show: function ()
		{
			$('#versionModal').modal();
			$('#versionModal .row').css('display','block');
			Version.cleanVersion();
			Version.init();
		},
		
		showAdd: function ()
		{
			$('#versionModal').modal();
			$('#versionModal .row').css('display','none');
			Version.cleanVersion();
			Version.init();
		},
		
		loadVersion: function (id)
		{
			$.get('Crud?action=loadElement&id='+id+'&type=Version',function (version){
				
				$('.form-version').find('[name="id"]').val(version.id);
				$('.form-version').find('[name="description"]').val(version.description);
				
				version.deploydate = Util.dateFormat(version.deploydate);
				
				$('.form-version').find('[name="deploydate"]').val(version.deploydate);
			});
		},
		
		validate: function ()
		{
			var msg = '';
			
			if($('.form-version').find('[name="description"]').val() == '')
				msg += 'Informe o campo descrição <br />';
			if($('.form-version').find('[name="deploydate"]').val() == '')
				msg += 'Informe o campo data de publicação <br />';
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else
				return true;
		},
		
		saveVersion: function ()
		{
			$('.btn-save-version').unbind().click(function (){
				var data = $('.form-version').serialize();
				
				if(Version.validate())
				{
					$.post("Crud?action=save&elementType=Version&"+data,function (res){
						APP.success("Versão salvo com sucesso!");
						$('#versionModal').modal('hide');
					});
					
					APP.success("Versão salvo com sucesso!");
					$('#versionModal').modal('hide');
				}
			});
		}
};


$(document).ready(function (){
	Version.init();
	
	  $('.form_datetime').datetimepicker({
	        language:  'pt-BR',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			forceParse: 0,
	        showMeridian: 1,
	        format: "dd/mm/yyyy hh:ii:ss"
	    });
});