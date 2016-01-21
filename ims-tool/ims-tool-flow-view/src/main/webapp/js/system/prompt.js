var Prompt = {
		init: function ()
		{
			this.popAudios();
			this.popVersions();
			this.pesquisar();
			EditAudio.adicionarAudio();
			EditAudio.editarAudio();
			EditAudio.removerAudio();
			Util.tableSelect('.table-audio');
			this.pesquisarAudio();
			this.save();
			
			$('.add-button-element').unbind().click(function (){
				$(this).parent().parent().css('display','none');
				$('.form-container-prompt').css('display','block');
				Prompt.cleanForm();
			});
			
			$('.edit-button-element').unbind().click(function (){
				Prompt.editarPrompt();
			});
			
			this.audioFeatures();
		},
		
		removeAudioVar: function()
		{
			$('.remove-audiovar-param').unbind().click(function (){
				$(this).parent().parent().remove();
			});
		},
		
		audioFeatures: function ()
		{
			$('.formatName').change(function (){
				var cssClass = $(this).val();
				if(cssClass == '') return true;
				
				$('.formatParameters').val('');
				$('.formatParametersValue').val('');
				$('.formatParameters option').css('display','none');
				$('.formatParameters .emptyDefault').css('display','block');
				$('.formatParameters .'+cssClass).css('display','block');
			});
			
			$('.formatParameters').change(function (){
				var cssClass = $(this).val();
				if(cssClass == '') return true;
				
				$('.formatParametersValue').val('');
				$('.formatParametersValue option').css('display','none');
				$('.formatParametersValue .emptyDefault').css('display','block');
				$('.formatParametersValue .'+cssClass).css('display','block');
			});
			
			$('.add-audiovar-param').click(function (){
				var formatParameters  = $('.formatParameters').val();
				var formatParametersValue  = $('.formatParametersValue').val();
				
				if(formatParameters != '' && formatParametersValue != '')
				{
					$('.table-audiovar tbody').append('<tr><td>'+formatParameters+'</td><td>'+formatParametersValue+'</td><td><a href="javascript:void(0);" class="remove-audiovar-param btn-danger btn btn-xs">Excluir</a></td></tr>');
					Prompt.removeAudioVar();
				}
			});
		},
		
		loadConditions: function ()
		{
			$('.form-prompt input.selecao-condition').select2(Util.search('Condition'));
		},
		
		cleanForm: function ()
		{
			$('#promptModal').find('[name="name"]').val('');
			$('#promptModal').find('[name="versionid"]').val('');
			this.popVersions();
			$('.table-audio').html('');
			trySelectVersion();
		},
		
		showAdd: function ()
		{
			$('#promptModal').modal();
			$('.form-prompt .row-buttons').css('display','none');
			$('#promptModal .form-container-prompt').css('display','block');
			Prompt.cleanForm();
		},
		
		pesquisarAudio: function ()
		{
			$('.btn-pesquisar-audio').click(function (){
				$('#pesquisaAudioModal').modal();
			});
		},
		
		popAudios: function ()
		{			
			$('#pesquisaAudioModal input.selecao-audio').select2(Util.search('Audio'));
			PesquisarAudio.selecionarAudio();
		},
		
		popVersions: function ()
		{			
			$('#pesquisaAudioModal input.selecao-version').select2(Util.search('Version'));
		},
		
		pesquisar: function (){
			
			$('.pesquisar-audio').click(function (){
				$('#pesquisaAudioModal').modal();
				PesquisarAudio.init();
			});
		},
		
		editarPrompt: function ()
		{
			var id = $('#promptModal').find('.selecao-prompt').select2('val');
			Prompt.loadPrompt(id);
			$('#promptModal .form-container-prompt').css('display','block');
		},
		
		loadPrompt: function (id)
		{
			$.get('Crud?action=loadFullElement&type=Prompt&id='+id,function (promptJson){

				$('#promptModal').find('[name="id"]').val(promptJson.id);
				$('#promptModal').find('[name="name"]').val(promptJson.name);
				$('.table-audio').html('');
				if(promptJson.audios.length > 0)
					$.each(promptJson.audios,function (index,obj){
						PesquisarAudio.popTable(obj);
					});
				
				$(".table-audio tbody").sortable().disableSelection();
				
			});
		},
		
		validate: function ()
		{
			var msg = '';
			var audios = $('.table-audio tr');
			
			if($('#promptModal').find('[name="name"]').val() == '')
				msg += 'Informe o campo nome <br />';
			if(audios.length == 0)
				msg += 'Informe os audios do prompt <br />';
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else 
				return true;
		},
		
		save: function ()
		{
			$('.btn-save-prompt').unbind().click(function (){
				var id = $('.form-prompt').find('[name="id"]').val();
				var namePrompt = $('.form-prompt').find('[name="name"]').val();
				var versionid = $('.form-prompt').find('[name="versionid"]').val();
				
				var audios = "";
				
				$.each($('.table-audio tr'),function (index,obj){
					audios += $(obj).attr('id')+":"+$(obj).find('input.selecao-condition').val()+",";
				});
				
				audios = audios.substr(0,audios.length-1);
				
				if(Prompt.validate())
					$.post('Crud?action=saveElement&id='+id+'&name='+namePrompt+"&audios="+audios+"&versionid="+versionid+"&elementType=Prompt",function (){
						$('#promptModal').modal('hide');
						Announce.loadPrompt();
						APP.success("Prompt salvo com sucesso!");
					});
			});
		}
};

var EditAudio = {
		
		loadForm: function (id,callback)
		{
			EditAudio.cleanAudio();
			
			$.get('Crud?action=loadFullElement&id='+id+'&type=Audio',function (audio){
				$('.form-audio').find('[name="id"]').val(audio.id);
				$('.form-audio').find('[name="type"]').val(audio.type);
				$('.form-audio').find('[name="name"]').val(audio.name);
				$('.form-audio').find('[name="description"]').val(audio.description);
				$('.form-audio').find('[name="path"]').val(audio.path);
				
				$.get('Crud?action=loadElement&id='+audio.versionid+'&type=Version',function (version){
					$('.form-audio input.selecao-version').select2('data',{id: version.id, text: version.description});
				});
				
				$('.table-audiovar tbody').html('');
				
				if(audio.vars.length > 0){
					for(i in audio.vars){
						if(audio.vars[i].paramname == 'formatname')
							$('.form-audio').find('.formatName').val(audio.vars[i].paramvalue);
						else
							$('.table-audiovar tbody').append('<tr><td>'+audio.vars[i].paramname+'</td><td>'+audio.vars[i].paramvalue+'</td><td><a href="javascript:void(0);" class="remove-audiovar-param btn-danger btn btn-xs">Excluir</a></td></tr>');
					}
					$('.audiovar-params-container').show();
				}
				Prompt.removeAudioVar();
				callback();
			});
		},
		
		cleanAudio: function ()
		{
			$('.form-audio').find('[name="id"]').val('');
			$('.form-audio').find('[name="type"]').val('');
			$('.form-audio').find('[name="name"]').val('');
			$('.form-audio').find('[name="description"]').val('');
			$('.form-audio').find('[name="path"]').val('');
			$('.form-audio').find('[name="versionid"]').val('');
			$('.table-audiovar tbody').html('');
			$('.audiovar-params-container').hide();
			trySelectVersion();
		},
		
		adicionarAudio: function ()
		{
			$('.btn-add-audio').click(function (){
				$('#audioModal').modal();
				EditAudio.cleanAudio();
				EditAudio.salvarAudio();
				EditAudio.audioType();
			});
		},
		
		audioType: function ()
		{
			$('.form-audio').find('[name="type"]').click(function (){
				if($(this).val() == 'VAR')
					$('.audiovar-params-container').show();
				else 
					$('.audiovar-params-container').hide();
			});
		},
		
		editarAudio: function ()
		{
			$('.btn-editar-audio').click(function (){
				
				var id = $('.line-selected').attr('id');
				
				EditAudio.loadForm( id , function (){
					$('#audioModal').modal();
					EditAudio.salvarAudio();
				});
				
			});
		},
		
		removerAudio: function ()
		{
			$('.btn-excluir-audio').unbind().click(function (){
				Util.tableRemove('.table-audio');
			});
		},
		
		validate: function ()
		{
			var msg = '';
			
			var formAudio = $('.form-audio');
			
			if(formAudio.find('[name="type"]').val() == '')
				msg += 'Informe o campo tipo <br />';
			if(formAudio.find('[name="name"]').val() == '')
				msg += 'Informe o campo nome <br />';
			if(formAudio.find('[name="description"]').val() == '')
				msg += 'Informe o campo descrição <br />';
			if(formAudio.find('[name="type"]').val() != "TTS" && formAudio.find('[name="path"]').val() == '')
				msg += 'Informe o campo path <br />';
			if(formAudio.find('[name="versionid"]').val() == '')
				msg += 'Informe o campo versão <br />';
			
			if(formAudio.find('[name="type"]').val() != '' && formAudio.find('[name="type"]').val() == 'VAR')
			{
				if(formAudio.find('.formatName').val() == '')
					msg += 'Informe o campo tipo var<br />';
				if($('.table-audiovar tbody tr').length == 0)
					msg += 'Informe os parametros do audio <br />';
			}
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else {
				return true;
			}
		},
		
		salvarAudio: function ()
		{
			var self = this;
			
			$('.btn-save-audio').unbind().click(function (){
				
				if(!self.validate())
					return;
				
				var params = $('.form-audio').serialize();
				var formatName = $('.formatName').val();
				
				var audioVars = ''; 
				$.each($('.table-audiovar tbody tr'),function (index,obj){
					audioVars += $(obj).find('td').eq(0).text()+":"+$(obj).find('td').eq(1).text()+ "|";
				});
				
			if(audioVars != '')
				audioVars = audioVars.substr(0,audioVars.length-1);
				
				$.post("Crud?action=saveElement&"+params+"&elementType=Audio&vars="+audioVars+"&audioVarType="+formatName,function (element){
					$('#audioModal').modal('hide');
					
					if($('.form-audio').find('input[name="id"]').val() != "") {
						var lines = $('.table-audio tbody tr');
						$.each(lines,function (index,obj){
							if($(lines[index]).attr('id') == element.id)
								$(lines[index]).remove();
						})
					}
					
					PesquisarAudio.popTable(element);
				});
			});
		}
};

var PesquisarAudio = {
		
		init: function (){
			this.selecionarAudio();
		},
		
		popTable: function (audio)
		{
			var html = '<tr id="'+audio.id+'">';
				html += '<td style="width: 14%">'+audio.id+'</td>';
				html += '<td style="width: 8%">'+audio.type+'</td>';
				html += '<td>'+audio.name+'</td>';
				html += '<td>'+audio.description+'</td>';
				html += '<td>'+audio.path+'</td>';
				html += '<td>'+audio.versionid+'</td>';
				html += '<td><input class="selecao-condition input-xlarge" type="hidden" name="condition" /> <a href="javascript:Condition.showAdd()">Adicionar novo</a></td>';
			   html += '</tr>';
			
			$('.table-audio').append(html);
			Prompt.loadConditions();
			Util.tableSelect('.table-audio');
			
			$.get('Crud?action=loadElement&id='+audio.condition+'&type=Condition',function (condition){
				$('.table-audio tr').last().find('input.selecao-condition').select2('data',{id: condition.id, text: condition.name});
			});
		},
		
		loadAudio: function (id)
		{
			$.get('Crud?action=loadElement&id='+id+'&type=Audio',function (audio){
				
				PesquisarAudio.popTable(audio);
				EditAudio.removerAudio();
				
				$('#pesquisaAudioModal').modal('hide');
				
				$(".table-audio tbody").sortable().disableSelection();
			});
		},
		
		selecionarAudio: function (){
			
			$('.btn-pequisa-add-audio').unbind().click(function (){
				var value = $('.selecao-audio').select2('val');
				PesquisarAudio.loadAudio(value);
			});
		}
};

var Announce = {
	init: function ()
	{
		this.loadAnnounces();
		this.loadVersions();
		this.loadPrompt();
		this.save();
		this.cleanAnnounce();		
		this.adicionar();
		this.editar();

		$('.form-announce .row').css('display','block');
		$('.div-container-announce').css('display','none');
	},
	
	show: function ()
	{
		$('#announceModal').modal();
		Announce.init();
	},
	
	adicionar: function ()
	{
		$('#announceModal .btn-add-element').unbind().click(function (){
			$('.div-container-announce').css('display','block');
			Announce.cleanAnnounce();
		});
	},
	
	editar: function () {
		$('#announceModal .btn-edit-element').unbind().click(function (){
			$('.div-container-announce').css('display','block');
			var id = $('.selecao-announce').select2('val');
			Announce.loadAnnounce(id);
		});
	},
	
	cleanAnnounce: function ()
	{
		$('.form-announce').find('[name="id"]').val('');
		$('.form-announce').find('[name="name"]').val('');
		$('.form-announce').find('[name="description"]').val('');
		$('.form-announce').find('[name="flushprompt"]').val('');
		
		$('.form-announce').find('[name="prompt"]').val('');
		$('.form-announce').find('[name="tag"]').val('');
		$('.form-announce').find('[name="versionid"]').val('');
		
		$('input.selecao-announce').select2(Util.search('Announce'));
		$('input.selecao-tag').select2(Util.search('Tag'));
		$('input.selecao-prompt').select2(Util.search('Prompt'));
		$('input.selecao-version').select2(Util.search('Version'));
		trySelectVersion();
	},
	
	loadAnnounce: function (id)
	{
		$.get('Crud?action=loadElement&id='+id+'&type=Announce',function (announce){
			
			$('.form-announce').find('[name="id"]').val(announce.id);
			$('.form-announce').find('[name="name"]').val(announce.name);
			$('.form-announce').find('[name="description"]').val(announce.description);
			$('.form-announce').find('[name="flushprompt"]').val(announce.flushprompt);
			
			
			$.get('Crud?action=loadElement&id='+announce.prompt+'&type=Prompt',function (prompt){
				$('.form-announce input.selecao-prompt').select2('data',{id: prompt.id, text: prompt.name});
			});
			
			$.get('Crud?action=loadElement&id='+announce.tag+'&type=Tag',function (tag){
				$('.form-announce input.selecao-tag').select2('data',{id: tag.id, text: tag.description});
			});
			
			$.get('Crud?action=loadElement&id='+announce.versionid+'&type=Version',function (version){
				$('.form-announce input.selecao-version').select2('data',{id: version.id, text: version.description});
			});
		});
	},
	
	validate: function ()
	{
		var msg = '';
		
		if($('.form-announce').find('[name="name"]').val() == '')
			msg += 'Informe o campo nome <br />';
		if($('.form-announce').find('[name="description"]').val() == '')
			msg += 'Informe o campo descrição <br />';
		if($('.form-announce').find('[name="flushprompt"]').val() == '')
			msg += 'Informe o campo flushprompt <br />';
		if($('.form-announce').find('[name="prompt"]').val() == '')
			msg += 'Informe o campo prompt <br />';
		if($('.form-announce').find('[name="tag"]').val() == '')
			msg += 'Informe o campo tag <br />';
		if($('.form-announce').find('[name="versionid"]').val() == '')
			msg += 'Informe o campo versão <br />';
		
		if(msg != '')
		{
			APP.error(msg);
			return false;
		} else {
			return true;
		}
	},
	
	save: function ()
	{
		$('.btn-save-announce').unbind().click(function (){
			var data = $('.form-announce').serialize();
			if(Announce.validate())
				$.post('Crud?action=saveElement&elementType=Announce&'+data,function (res){
					APP.success("Announce salvo com sucesso");
					$('#announceModal').modal('hide');
				});
		});
	},
	
	loadAnnounces: function ()
	{
		$('input.selecao-announce').select2(Util.search('Announce'));
	},
	
	loadVersions: function ()
	{
		$('input.selecao-version').select2(Util.search('Version'));
	},
	
	loadPrompt: function ()
	{
		$('input.selecao-prompt').select2(Util.search('Prompt'));
	}
};
		
$(document).ready( function (){
	Prompt.init();
	Announce.init();
});