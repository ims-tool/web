var Menu = {
		
		choiceData: '',
		
		init: function (){
			this.addMenu();
			this.editMenu();
			this.loadMenus();
			this.loadPrompts();
			this.loadTags();
			this.loadNomatch();
			this.loadNoinput();
			this.loadVersions();
			Util.tableSelect('.table-choice');
			this.removerChoice();
			this.adicionarChoice();
			this.saveChoice();
			this.save();
		},
		
		show: function ()
		{
			$('#menuModal').modal();
			Menu.init();
			Menu.cleanMenu();
		},
		
		cleanMenu: function ()
		{
			this.loadMenus();
			
			$('.form-menu').find('[name="id"]').val('');
			$('.form-menu').find('[name="name"]').val('');
			$('.form-menu').find('[name="description"]').val('');
			this.loadPrompts();
			this.loadTags();
			this.loadNomatch();
			this.loadNoinput();
			this.loadVersions();
			trySelectVersion();
			
			$('.table-choice tbody').html('');
		},
		
		addMenu: function ()
		{
			$('#menuModal .btn-add-element').unbind().click(function (){
				Menu.cleanMenu();
			});
		},
		
		editMenu: function (){
			var self = this;
			$('#menuModal .btn-edit-element').unbind().click(function (){
				var menu = $('input.selecao-menu').val();
				self.loadMenu(menu);
			});
		},
		
		loadMenu: function (menu)
		{
			var self = this;
			$.get('Crud?action=loadFullElement&type=Menu&id='+menu,function (menu){
				$('.form-menu').find('[name="id"]').val(menu.id);
				$('.form-menu').find('[name="name"]').val(menu.name);
				$('.form-menu').find('[name="description"]').val(menu.description);
				
/*				$('.form-menu').find('[name="nomatch"]').val(menu.nomatch);
				$('.form-menu').find('[name="noinput"]').val(menu.noinput);*/
				
				$.get('Crud?action=loadElement&id='+menu.versionid+'&type=Version',function (version){
					$('.form-menu input.selecao-version').select2('data',{id: version.id, text: version.description});
				});
				
				$.get('Crud?action=loadElement&id='+menu.prompt+'&type=Prompt',function (prompt){
					$('.form-menu input.selecao-prompt').select2('data',{id: prompt.id, text: prompt.name});
				});
				
				$.get('Crud?action=loadElement&id='+menu.nomatch+'&type=Nomatchinput',function (nomatchinput){
					$('.form-menu input.selecao-nomatch').select2('data',{id: nomatchinput.id, text: nomatchinput.name});
				});
				
				$.get('Crud?action=loadElement&id='+menu.noinput+'&type=Nomatchinput',function (nomatchinput){
					$('.form-menu input.selecao-noinput').select2('data',{id: nomatchinput.id, text: nomatchinput.name});
				});
				
				$('.table-choice tbody').html('');
				
				for(var x = 0; x < menu.choices.length; x++){
					$('.table-choice tbody').append(Menu._tableLineTemplate(menu.choices[x]));
				}
				
				$(".table-choice tbody").sortable().disableSelection();
				Util.tableSelect('.table-choice');
				self.removerChoice();
			});
		},
		
		loadMenus: function ()
		{
			$('input.selecao-menu').select2(Util.search('Menu'));
		},
		
		loadPrompts: function ()
		{
			$('.form-menu input.selecao-prompt').select2(Util.search('Prompt'));
		},
		
		loadTags: function ()
		{
			$('.form-menu input.selecao-tag').select2(Util.search('Tag'));
		},
		
		loadConditions: function ()
		{
			$('.form-choice input.selecao-condition').select2(Util.search('Condition'));
		},
		
		loadVersions: function ()
		{
			$('.form-menu input.selecao-version').select2(Util.search('Version'));
		},
		
		loadNomatch: function ()
		{
			
			$('.form-menu input.selecao-nomatch').select2({
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
		
		loadNoinput: function ()
		{
			$('.form-menu input.selecao-noinput').select2({
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
		
		removerChoice: function ()
		{
			$('.btn-excluir-choice').unbind().click(function (){
				Util.tableRemove('.table-choice');
			});
		},
		
		cleanChoice: function ()
		{
			$('.form-choice').find('[name="name"]').val('');
			$('.form-choice').find('[name="dtmf"]').val('');
			$('.form-choice').find('[name="tag"]').val('');
			$('.form-choice').find('[name="condition"]').val('');
			$('.form-choice input.selecao-tag').select2(Util.search('Tag'));
		},
		
		adicionarChoice: function ()
		{
			$('.btn-add-choice').unbind().click(function (){
				$('#choiceModal').modal();
				Menu.cleanChoice();
				Menu.loadConditions();
			});
		},
		
		_tableLineTemplate: function (choice)
		{
			var html = '<tr>';
				html += '<td>'+choice.name+'</td>';
				html += '<td>'+choice.dtmf+'</td>';
				html += '<td>'+choice.tag+'</td>';
				html += '<td>'+choice.condition+'</td>';
			html += '</tr>';
			return html;
		},
		
		validateChoice: function (choice)
		{
			msg = '';
			
			if(choice.name == '')
				msg += 'Informe o campo nome <br />';
			if(choice.dtmf == '')
				msg += 'Informe o campo dtmf <br />';
			if(choice.tag == '')
				msg += 'Informe o campo tag <br />';
			
			console.log(msg);
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
				
			} else 
				return true;
		},
		
		saveChoice: function ()
		{
			var self = this;
			
			$('.btn-save-choice').unbind().click(function (){
				var choice = {
						name: $('.form-choice').find('[name="name"]').val(),
						dtmf: $('.form-choice').find('[name="dtmf"]').val(),
						tag:  $('.form-choice').find('[name="tag"]').val(),
						condition:  $('.form-choice').find('[name="condition"]').val(),
						versionid: ''
				};
				
				if(!Menu.validateChoice(choice))
					return false;
				
				$('.table-choice').append(self._tableLineTemplate(choice));
				
				$(".table-choice tbody").sortable().disableSelection();
				Util.tableSelect('.table-choice');
				self.removerChoice();
				$('#choiceModal').modal('hide');
			});
		},
		
		validate: function ()
		{
			var msg = '';
			
			if($('.form-menu').find('[name="name"]').val() == '')
				msg += 'Informe o campo nome <br />';
			if($('.form-menu').find('[name="description"]').val() == '')
				msg += 'Informe o campo descrição <br />';
			if($('.form-menu').find('[name="prompt"]').val() == '')
				msg += 'Informe o campo prompt <br />';
			if($('.form-menu').find('[name="nomatch"]').val() == '')
				msg += 'Informe o campo nomatch <br />';
			if($('.form-menu').find('[name="noinput"]').val() == '')
				msg += 'Informe o campo noinput <br />';
			if($('.form-menu').find('[name="versionid"]').val() == '')
				msg += 'Informe o campo versão <br />';
			
			if($('.table-choice tbody tr').length == 0)
				msg += 'Informe as choices para o menu em edição <br />';
			
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
			$('.btn-save-menu').unbind().click(function (){
				
				if(!Menu.validate())
					return false;
				
				var menu = {};
				menu.id = $('.form-menu').find('[name="id"]').val();
				menu.name = $('.form-menu').find('[name="name"]').val();
				menu.description = $('.form-menu').find('[name="description"]').val();
				menu.prompt = $('.form-menu').find('[name="prompt"]').val();
				menu.noinput = $('.form-menu').find('[name="noinput"]').val();
				
				menu.nomatch = $('.form-menu').find('[name="nomatch"]').val();
				menu.versionid = $('.form-menu').find('[name="versionid"]').val();
				menu.choice = '';
				$.each($('.table-choice tbody tr'),function(index,obj){
					
					if(menu.choice != '')
						menu.choice += '@';
					
					menu.choice += $(obj).find('td:eq(0)').text()+'|';
					menu.choice += $(obj).find('td:eq(1)').text()+'|';
					menu.choice += $(obj).find('td:eq(2)').text()+'|';
					menu.choice += $(obj).find('td:eq(3)').text();
					
				});
				
				var params = "";
				for(attribute in menu)
					params += attribute + '='+menu[attribute]+'&';
				params = params.substr(0,params.length-1);
				
				$.post('Crud?action=saveElement&elementType=Menu&'+params,function(){
					APP.success('Menu salvo com sucesso');
				});
			});
		}
};

$(document).ready(function (){
	Menu.init();
});