var Tag = {
		
		init: function ()
		{
			this.loadTags();
			this.loadVersions();
			this.saveTag();
			this.editTag();
			this.addTag();
		},
		
		loadTags: function ()
		{
			$('input.selecao-tag').select2(Util.search('tag'));
		},
		
		loadVersions: function ()
		{
			$('.form-tag input.selecao-version').select2(Util.search('Version'));
		},
		
		cleanTag: function ()
		{
			$('.form-tag').find('[name="id"]').val('');
			$('.form-tag').find('[name="tagtypeid"]').val('');
			$('.form-tag').find('[name="description"]').val('');
			$('.form-tag').find('[name="versionid"]').val('');
			$('.form-tag input.selecao-version').select2(Util.search('Version'));
			trySelectVersion();
		},
		
		addTag: function ()
		{
			$('#tagModal .btn-add-element').unbind().click(function (){
				Tag.cleanTag();
			});
		},
		
		editTag: function ()
		{
			$('#tagModal .btn-edit-element').unbind().click(function (){
				var id = $('#tagModal input.selecao-tag').val();
				Tag.loadTag(id);
			});
		},
		
		show: function ()
		{
			$('#tagModal').modal();
			this.cleanTag();
			$('#tagModal .row').css('display','block');
		},
		
		showAdd: function ()
		{
			$('#tagModal').modal();
			Tag.cleanTag();
			$('#tagModal .row').css('display','none');
		},
		
		loadTag: function (id)
		{
			$.get('Crud?action=loadElement&id='+id+'&type=Tag',function (tag){
				
				$('.form-tag').find('[name="id"]').val(tag.id);
				$('.form-tag').find('[name="tagtypeid"]').val(tag.tagtypeid);
				$('.form-tag').find('[name="description"]').val(tag.description);
				
				$.get('Crud?action=loadElement&id='+tag.versionid+'&type=Version',function (version){
					$('.form-tag input.selecao-version').select2('data',{id: version.id, text: version.description});
				});
			});
		},
		
		validate: function ()
		{
			var msg = '';
			
			if($('.form-tag').find('[name="tagtypeid"]').val() == '')
				msg += 'Informe o campo tipo da tag <br />';
			if($('.form-tag').find('[name="description"]').val() == '')
				msg += 'Informe o campo descrição<br />';
			if($('.form-tag input.selecao-version').val() == '')
				msg += 'Informe o campo versão<br />';
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else
				return true;
		},
		
		saveTag: function ()
		{
			$('.btn-save-tag').unbind().click(function (){
				
				var data = $('.form-tag').serialize();
				
				if(Tag.validate())
					$.post("Crud?action=save&elementType=Tag&"+data,function (res){
						APP.success("Tag salvo com sucesso!");
						$('#tagModal').modal('hide');
						$('.selecao-tag').select2('data',{id: res.id, text: res.description });
					});
			});
		}
};


$(document).ready(function (){
	Tag.init();
});