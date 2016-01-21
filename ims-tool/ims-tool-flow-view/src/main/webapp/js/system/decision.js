var Decision = {
		
		init: function ()
		{
			this.loadDecisions();
			this.loadForms();
			this.loadTags();
			this.loadVersions();
			this.saveDecision();
			this.editDecision();
			this.addDecision();
			this.addDecisionGroup();
			this.editDecisionGroup();
		},
		
		show: function ()
		{
			$('#decisionModal').modal();
			Decision.init();
			Decision.cleanDecision();
		},
		
		loadTags: function ()
		{
			$('#decisionModal .selecao-tag').select2(Util.search('Tag'));
		},
		
		loadVersions: function ()
		{
			$('#decisionModal .selecao-version').select2(Util.search('Version'));
		},
		
		addDecisionGroup: function ()
		{
			$('.btn-add-decisiongroup').unbind().click(function (){
				DecisionGroup.init();
				DecisionGroup.show();
			});
		},
		
		removeDecisionGroup: function ()
		{
			$('#decisionModal .btn-excluir-decisiongroup').unbind().click(function (){
				$('.table-decisiongroup .line-selected').remove();
			});
		},
		
		loadDecisions: function ()
		{
			$('input.selecao-decision').select2(Util.search('Decision'));
		},
		
		cleanDecision: function ()
		{
			$('.form-decision').find('[name="id"]').val('');
			$('.form-decision').find('[name="name"]').val('');
			$('.form-decision').find('[name="description"]').val('');
			this.loadTags();
			this.loadVersions();
			$('.table-decisiongroup tbody').html('');
			trySelectVersion();
		},
		
		addDecision: function ()
		{
			$('#decisionModal .btn-add-element').unbind().click(function (){
				Decision.cleanDecision();
			});
		},
		
		editDecision: function ()
		{
			$('#decisionModal .btn-edit-element').unbind().click(function (){
				var id = $('input.selecao-decision').val();
				Decision.loadDecision(id);
			});
		},
		
		editDecisionGroup: function ()
		{
			$('.btn-edit-decisiongroup').unbind().click(function (){
				if($('.table-decisiongroup .line-selected').length > 0)
				{
					var data = $('.table-decisiongroup .line-selected').find('input').val();
					DecisionGroup.init();
					DecisionGroup.show();
					DecisionGroup.loadFromDatabase(data);
				}
			});
		},
		
		popDecisionGroups: function (decisiongroups)
		{
			$('.table-decisiongroup tbody').html('');
			
			$.each(decisiongroups,function(index,obj){
				data = '';
				parameters = '';
				decisiongroup = decisiongroups[index];
				data += 'decisiongroup.description='+decisiongroup.description+'&';
				data += 'decisiongroup.decisionmapid='+decisiongroup.decisionmapid+'&';
				
				if(decisiongroup.decisionparameters.length == 0)
					data += 'decisiongroup.parameters=';
				
				$.each(decisiongroup.decisionparameters,function(index,obj){
					parameters += decisiongroup.decisionparameters[index].paramname+':'+decisiongroup.decisionparameters[index].paramvalue+'|';
				});
				
				if(parameters != ''){
						auxData = parameters.substr(0,parameters.length-1);
						data += 'decisiongroup.parameters='+auxData;
				}
				
				var decisionChances = decisiongroup.decisionchance;
				
				if(decisionChances.length > 0)
				{
					data += '&decisionChance=';
					var info = [];
					var aux = '';
					$.each(decisionChances,function(index,obj){
						//data += '&'+$(obj).find('input').val();
						dev = decisionChances[index];
						values = [
						          dev.value1,dev.value2,dev.value3,dev.value4,dev.value5,
						          dev.value6,dev.value7,dev.value8,dev.value9,dev.value10
						];
						aux = Util.translateOperation(dev.operation)+":"+values.join(',')+":"+dev.tag+":"+dev.decisiongroupchild+":"+dev.nextformid;
						info.push(aux);
					});
					
					
					data += Util.customSerializeArray(info);
				} else
					data += '&decisionChance=';
				
				hiddenData = '<input type="hidden" name="decisionGroupData" value="'+data+'" />';
				//line += '<table>';
				//line += '<tbody>';
				
				var line = '<tr><td>'+decisiongroup.description;
		        /*line += '<span style="margin-left: 10px;" class="popoverElement" html="true" data-container="body" data-toggle="popover" data-placement="right" data-content="';
		        line += '">';
		        line += '<span class="glyphicon glyphicon-search"></span></span>';*/
		        
		        //line += '<span style="margin-left: 10px;" class="popoverElement" data-content="';
				line += '<span style="margin-left: 10px;" class="popoverElement" html="true" data-container="body" data-toggle="popover" data-placement="right" data-content="';
		        line += '<b>Metodo:</b> '+decisiongroup.decisionmapid+' <br />';
		        
		        line += '<b>Parametros:</b> ';
		        
		        
		        for(i in decisiongroup.decisionparameters)
		        	line += decisiongroup.decisionparameters[i].paramname + ":" + decisiongroup.decisionparameters[i].paramvalue+"|";
		        line = line.substr(0,line.length-1)+'<br />';
		        
		        if(decisiongroup.decisionchance.length > 0)
				{
		        	$.each(decisiongroup.decisionchance,function(index,obj){
		        		line += '<b>Operação:</b> '+Util.translateOperation(decisiongroup.decisionchance[index].operation)+' <br />';
		        		line += '<b>Valores:</b> '+Util.cleanArray(Util.getValues(decisiongroup.decisionchance[index]))+' <br />';
		        		line += '<b>Tag:</b> '+decisiongroup.decisionchance[index].tag+' <br />';
		        		line += '<b>Child:</b> '+decisiongroup.decisionchance[index].decisiongroupchild+'<br />';
		        		line += '<b>Nextform:</b> '+decisiongroup.decisionchance[index].nextformid+'<br />';
					});
				}
		        line += '">';
		        line += '<span class="glyphicon glyphicon-search"></span></span>';
		        
		        
		        line += hiddenData;
				line += '</td></tr>';
				
				$('.table-decisiongroup tbody').append(line);
				
				Util.tableSelect('.table-decisiongroup tbody');
				Decision.removeDecisionGroup();
				$('.popoverElement').popover({html: true,trigger: 'hover'});
				//$('.popoverElement').popover({html: true,trigger: "focus"});
				
				/*$('.popoverElement').mouseover(function (e){
					
					$('.teste2').css('margin-left',e.clientX);
					$('.teste2').css('margin-top',e.clientY-400);
					
					$('.teste2').html($(this).attr('data-content'));
					
					$('.teste2').show(function (){
						setTimeout(function(){ $('.teste2').hide('slow'); }, 5000);
					});
				});*/
				
			});
		},
		
		loadDecision: function (id)
		{
			$.get('Crud?action=loadFullElement&id='+id+'&type=Decision',function (decision){
				$('.form-decision').find('[name="id"]').val(decision.id);
				$('.form-decision').find('[name="name"]').val(decision.name);
				$('.form-decision').find('[name="description"]').val(decision.description);
				$('.form-decision').find('[name="tag"]').val(decision.tag);
				$('.form-decision').find('[name="versionid"]').val(decision.versionid);
				
				$.get('Crud?action=loadElement&id='+decision.tag+'&type=Tag',function (tag){
					$('.form-decision input.selecao-tag').select2('data',{id: tag.id, text: tag.description});
				});
				
				$.get('Crud?action=loadElement&id='+decision.versionid+'&type=Version',function (version){
					$('.form-decision input.selecao-version').select2('data',{id: version.id, text: version.description});
				});
				
				Decision.popDecisionGroups(decision.decisiongroup);
				
				//$('.form-decision input.selecao-tag').select2('data', {id:103, text:'ENABLED_FROM_JS'});
			});
		},
		
		loadForms: function ()
		{
			
			$('input.selecao-form').select2(Util.search("Form"));
		},
		
		validate: function ()
		{
			var msg = '';
			var name = $('.form-decision').find('[name="name"]').val();
			var description = $('.form-decision').find('[name="description"]').val();
			var tag = $('.form-decision').find('[name="tag"]').val();
			var decisionGroups = $('.table-decisiongroup tbody tr').length;
			
			if(name == '')
				msg += 'Informe o campo nome <br />';
			if(description == '')
				msg += 'Informe o campo descrição <br />';
			if(tag == '')
				msg += 'Informe o campo tag <br />';
			if(decisionGroups == 0)
				msg += 'Informe ao menos um decision group';
			
			if(msg != ''){
				APP.error(msg);
				return false;
			} else {
				return true;
			}
		},
		
		saveDecision: function ()
		{
			$('.btn-save-decision').unbind().click(function (){
				
				if(Decision.validate())
				{
					var id = $('.form-decision').find('[name="id"]').val();
					var name = $('.form-decision').find('[name="name"]').val();
					var description = $('.form-decision').find('[name="description"]').val();
					var tag = $('.form-decision').find('[name="tag"]').val();
					var versionid = $('.form-decision').find('[name="versionid"]').val();
					
					var data = 'id='+id+'&name='+name+'&description='+description+'&tag='+tag+'&versionid='+versionid;
					
					$.each($('.table-decisiongroup tbody tr'),function (index,obj){
						data += '&'+$(obj).find('input').val();
					});
					
					$.post("Crud?action=saveElement&elementType=Decision&"+data,function (){
						APP.success("Decision salvo com sucesso!");
					});
				}
			});
		}
};

var DecisionGroup = {
		
		data: '',
		
		init: function ()
		{
			this.save();
			this.loadMaps();
			this.addParameter();
			this.removeParameter();
			this.addResultValue();
			this.editResultValue();
			this.removeResultValue();
			this.cleanDecisionGroup();
			Util.tableSelect('.table-decisionParameters tbody');
		},
		
		cleanDecisionGroup: function ()
		{
			$('[name="decisiongroup.description"]').val('');
			$('[name="decisiongroup.decisionmapid"]').val('');
			$('.table-decisionParameters tbody').html('');
			$('.table-decisionChance tbody').html('');
			trySelectVersion();
		},
	
		show: function ()
		{
			$('#decisionGroupModal').modal();
			DecisionGroup.cleanDecisionGroup();
			$('.selecao-decisionmap').css('width','300px');
		},
		
		addResultValue: function ()
		{
			$('.btn-add-decisionChance').unbind().click( function (){
				DecisionChance.showAdd();
			});
		},
		
		editResultValue: function ()
		{
			$('.btn-edit-decisionChance').unbind().click( function (){
				if($('.table-decisionChance tr.line-selected td').length > 0)
					DecisionChance.loadEdit($('.table-decisionChance tr.line-selected td'));
			});
		},
		
		removeResultValue: function ()
		{
			$('.btn-excluir-decisionChance').unbind().click( function (){
				$('.table-decisionChance .line-selected').remove();
			});
		},
		
		addParameter: function ()
		{
			$('.btn-add-decisionparameters').unbind().click(function (){
				DecisionParameters.show();
				DecisionParameters.init();
			});
		},
		
		removeParameter: function ()
		{
			$('#decisionGroupModal .btn-excluir-decisionparameters').unbind().click(function (){
				$('.table-decisionParameters .line-selected').remove();
			});
		},
		
		loadMaps: function ()
		{
			$('input.selecao-decisionmap').select2(Util.search('DecisionMap'));
		},
		
		loadFromDatabase: function (data)
		{
			//decisiongroup.description=decisiongroup-desc&decisiongroup.decisionmapid=1282&decisiongroup.parameters=teste:teste2&decisionChance=Igual:YES,,,,,,,,,:691:1459|Igual:YES,,,,,,,,,:244:1077
			data = Util.urlToArray(data);
			$('[name="decisiongroup.hasId"]').val('true');
			$('[name="decisiongroup.description"]').val(data.decisiongroupdescription);
			
			$.get('Crud?action=loadElement&id='+data.decisiongroupdecisionmapid+'&type=DecisionMap',function (decisionmap){
				$('.form-decisiongroup input.selecao-decisionmap').select2('data',{id: decisionmap.id, text: decisionmap.name});
			});
			var param = [];
			var params = data.decisiongroupparameters.split('|');
			$('.table-decisionParameters tbody').html('');
			for(par in params)
			{
				param = params[par].split(':');
				if(param[0] != '')
					$('.table-decisionParameters tbody').append('<tr><td>'+param[0]+'</td><td>'+param[1]+'</td></tr>');
				Util.tableSelect('.table-decisionParameters tbody');
			}
			
			var params = data.decisionChance.split('|');
			
			var hiddenField = '';
			$('.table-decisionChance tbody').html('');
			for(par in params)
			{
				decisionchance = params[par].split(':');
				hiddenField = '<input type="hidden" value="'+params[par]+'" />';
				decisionchance[1] = Util.cleanArray(decisionchance[1].split(',')).join(',');
				$('.table-decisionChance tbody').append('<tr><td>'+decisionchance[0]+'</td><td>'+decisionchance[1]+'</td><td>'+decisionchance[2]+'</td><td>'+decisionchance[3]+'</td><td>'+decisionchance[4]+hiddenField+'</td></tr>');
				Util.tableSelect('.table-decisionChance tbody');
			}
			//data = '<input type="hidden" value="'+data+'" />';
			
			
			
		},
		
		validate: function ()
		{
			var msg = '';
			var description = $('.form-decisiongroup').find('[name="decisiongroup.description"]').val();
			var decisionmap = $('.form-decisiongroup').find('[name="decisiongroup.decisionmapid"]').val();
			var decisionchances = $('.table-decisionChance tbody tr').length;
			
			if(description == '')
				msg += 'Informe o campo descrição<br />';
			if(decisionmap == '')
				msg += 'Informe o campo metodo<br />';
			
			if(decisionchances < 2)
			{
				msg += 'Informe ao menos duas Decisions Chances<br />';
			}
			
			if(msg == '')
			{
				return true;
			} else {
				APP.error(msg);
				return false;
			}
		},
		
		save: function (){
			$('.btn-save-decisiongroup').unbind().click(function (){
				
				if(!DecisionGroup.validate())
					return false;
				
				var data = '';
				var auxData = '';
				var parameters = '';
				var description = $('.form-decisiongroup').find('[name="decisiongroup.description"]').val();
				var decisionmap = $('.form-decisiongroup').find('[name="decisiongroup.decisionmapid"]').val();
				var decisionmapText = $('.form-decisiongroup').find('div.selecao-decisionmap').find('.select2-chosen').text();
				
				data += 'decisiongroup.description='+description+'&';
				data += 'decisiongroup.decisionmapid='+decisionmap+'&';
				
				if($('.table-decisionParameters tbody tr').length == 0)
					data += 'decisiongroup.parameters=';
				
				$.each($('.table-decisionParameters tbody tr'),function(index,obj){
					parameters += $(obj).find('td').eq(0).text()+':'+$(obj).find('td').eq(1).text()+'|';
				});
				
				if(parameters != ''){
						auxData = parameters.substr(0,parameters.length-1);
						data += 'decisiongroup.parameters='+auxData;
				}
				
				var decisionChances = $('.table-decisionChance tbody tr');
				
				if(decisionChances.length > 0)
				{
					data += '&decisionChance=';
					var info = [];
					$.each($('.table-decisionChance tbody tr'),function(index,obj){
						//data += '&'+$(obj).find('input').val();
						info.push($(obj).find('input').val());
					});
					console.log(info);
					data += Util.customSerializeArray(info);
				} else
					data += '&decisionChance=';
				
				hiddenData = '<input type="hidden" name="decisionGroupData" value="'+data+'" />';
				//line += '<table>';
				//line += '<tbody>';
				
				var line = '<tr><td>'+description;
		        line += '<span style="margin-left: 10px;" class="popoverElement" html="true" data-container="body" data-toggle="popover" data-placement="right" data-content="';
		        line += '<b>Metodo:</b> '+decisionmapText+' <br />';
		        line += '<b>Parametros:</b> '+auxData+' <br />';
		        
		        if(decisionChances.length > 0)
				{
		        	$.each($('.table-decisionChance tbody tr'),function(index,obj){
		        		line += '<b>Operação:</b> '+$(obj).find('td').eq(0).text()+' <br />';
		        		line += '<b>Valores:</b> '+$(obj).find('td').eq(1).text()+' <br />';
		        		line += '<b>Tag:</b> '+$(obj).find('td').eq(2).text()+' <br />';
		        		line += '<b>Child:</b> '+$(obj).find('td').eq(3).text()+'<br />';
		        		line += '<b>Nextform:</b> '+$(obj).find('td').eq(4).text()+'<br />';
					});
				}
		        line += '">';
		        line += '<span class="glyphicon glyphicon-search"></span></span>';
		        line += hiddenData;
				line += '</td></tr>';
				
				if($('.form-decisiongroup').find('[name="decisiongroup.hasId"]').val() == 'true')
					if($('.table-decisiongroup tbody').find('.line-selected').length > 0)
						$('.table-decisiongroup tbody').find('.line-selected').remove();
				
				$('.table-decisiongroup tbody').append(line);
				
				Util.tableSelect('.table-decisiongroup tbody');
				Decision.removeDecisionGroup();
				$('.popoverElement').popover({html: true});
				$('#decisionGroupModal').modal('hide');
			});
		}
};

var DecisionMap = {
	
		init: function ()
		{
			this.addDecisionMap();
		},
		
		showAdd: function ()
		{
			$('#decisionMapModal').modal();
			this.init();
		},
		
		addDecisionMap: function ()
		{
			$('.btn-save-decisionmap').unbind().click(function (){
				var data = $('.form-decisionmap').serialize();
				$.post('Crud?action=save&elementType=DecisionMap&'+data,function (){
					$('#decisionMapModal').modal('hide');
					APP.success("DecisionMap salvo com sucesso");
				});
			});
		}
};

var DecisionParameters = {
		
		cleanDecisionParameters: function ()
		{
			$('.form-decisionparameters').find('[name="paramname"]').val('');
			$('.form-decisionparameters').find('[name="paramvalue"]').val('');
		},
		
		show: function ()
		{
			this.cleanDecisionParameters();
			$('#decisionParametersModal').modal();
		},
		
		init: function ()
		{
			this.saveDecisionParameters();
			this.cleanDecisionParameters();
		},
		
		validate: function ()
		{
			var msg = '';
			var paramname = $('.form-decisionparameters').find('[name="paramname"]').val();
			var paramvalue = $('.form-decisionparameters').find('[name="paramvalue"]').val();
			
			if(paramname == '')
				msg += 'Informe o campo nome parametro<br />';
			if(paramvalue == '')
				msg += 'Informe o campo valor parametro<br />';
			
			if(msg == '')
			{
				return true;
			} else {
				APP.error(msg);
				return false;
			}
		},
		
		saveDecisionParameters: function ()
		{
			$('.btn-save-decisionparameters').unbind().click(function (){
				var paramname = $('.form-decisionparameters').find('[name="paramname"]').val();
				var paramvalue = $('.form-decisionparameters').find('[name="paramvalue"]').val();
				
				if(DecisionParameters.validate())
				{
					$('.table-decisionParameters tbody').append('<tr><td>'+paramname+'</td><td>'+paramvalue+'</td></tr>');
					$('#decisionParametersModal').modal('hide');
					DecisionGroup.removeParameter();
					Util.tableSelect('.table-decisionParameters tbody');
				}
			});
		}
};

var DecisionChance = {
		
		typeOperation: '',
		
		init: function ()
		{
			this.loadTags();
			this.loadDecisiongroupchild();
			this.loadNextForms();
			this.cleanDecisionChance();
			$('[name="values"]').select2({
				  tags: true, tokenSeparators: [',', ' '], maximumSelectionLength: 10
			});
			
			this.saveDecisionChance();
		},
		
		cleanDecisionChance: function ()
		{
			$('.form-decisionChance').find('[name="operation"]').val('');
			$('.form-decisionChance').find('[name="values"]').val('');
			$('.form-decisionChance').find('[name="tag"]').val('');
			$('.form-decisionChance').find('[name="decisiongroupchild"]').val('');
			$('.form-decisionChance').find('[name="nextformid"]').val('');
			
			this.loadTags();
			this.loadDecisiongroupchild();
			this.loadNextForms();
			trySelectVersion();
		},
		
		loadDecisiongroupchild: function ()
		{
			$('#decisionChanceModal .selecao-decisiongroupchild').select2(Util.search('DecisionGroup'));
		},
		
		loadNextForms: function ()
		{
			$('#decisionChanceModal .selecao-nextformid').select2(Util.search('Form'));
		},
		
		loadTags: function ()
		{
			$('#decisionChanceModal .selecao-tag').select2(Util.search('Tag'));
		},
		
		showAdd: function ()
		{
			$('#decisionChanceModal').modal();
			DecisionChance.init();
			DecisionChance.typeOperation = 'add';
		},
		
		loadEdit: function (tds)
		{
			DecisionChance.typeOperation = 'edit';
			$('#decisionChanceModal').modal();
			DecisionChance.init();
			$('.form-decisionChance').find('[name="operation"]').val($(tds[0]).text());
			auxValues = $(tds[1]).text().split(',');
			values = [];
			
			for (key in auxValues)
				if(auxValues[key] != '')
					values.push({id: auxValues[key], text: auxValues[key] });
			
			$('.form-decisionChance').find('[name="values"]').select2('data',values);
			
			$.get('Crud?action=loadElement&id='+$(tds[2]).text()+'&type=Tag',function (tag){
				$('.form-decisionChance input.selecao-tag').select2('data',{id: tag.id, text: tag.description});
			});
			
			$.get('Crud?action=loadElement&id='+$(tds[3]).text()+'&type=DecisionGroup',function (dec){
				$('.form-decisionChance').find('[name="decisiongroupchild"]').select2('data',{id: dec.id, text: dec.description});
			});
			
			$.get('Crud?action=loadElement&id='+$(tds[4]).text()+'&type=Form',function (form){
				$('.form-decisionChance').find('[name="nextformid"]').select2('data',{id: form.id, text: form.name});
			});

		},
		
		validate: function ()
		{
			var msg = '';
			
			var operation = $('.form-decisionChance').find('[name="operation"]').val();
			var values = $('.form-decisionChance').find('[name="values"]').val();			
			var tag = $('.form-decisionChance').find('[name="tag"]').val();
			
			if(operation == '' && values != '')
				msg += 'Informe a operação <br />';
			if(values == '' && operation != '')
				msg += 'Informe o(s) valor(es) de comparação<br />';
			if(tag == '')
				msg += 'Informe o campo tag <br />';
			
			if(msg == '')
				return true;
			else {
				APP.error(msg);
				return false;
			}
		},
		
		saveDecisionChance: function ()
		{
			$('.btn-save-decisionChance').unbind().click(function (){
				
				var data = [];
				
				if(DecisionChance.validate())
				{
					var operation = $('.form-decisionChance').find('[name="operation"]').val();
					var values = $('.form-decisionChance').find('[name="values"]').val();
					var tag = $('.form-decisionChance').find('[name="tag"]').val();
					var decisiongroupchild = $('.form-decisionChance').find('[name="decisiongroupchild"]').val();
					var nextformid = $('.form-decisionChance').find('[name="nextformid"]').val();
					
					if(DecisionChance.validate())
					{
						data.push(operation);
						data.push(values);
						data.push(tag);
						data.push(decisiongroupchild);
						data.push(nextformid);
						data = '<input type="hidden" value="'+data.join(':')+'" />';
						
						$('.table-decisionChance tbody').append('<tr><td>'+operation+'</td><td>'+values+'</td><td>'+tag+'</td><td>'+decisiongroupchild+'</td><td>'+nextformid+'</td>'+data+'</td></tr>');
						$('#decisionChanceModal').modal('hide');
						
						if(DecisionChance.typeOperation == 'edit')
							$('.table-decisionChance tbody tr.line-selected').remove();
						
						Util.tableSelect('.table-decisionChance tbody');
						DecisionGroup.editResultValue();
					}
				}
			});
		}
}


$(document).ready(function (){
	Decision.init();
});