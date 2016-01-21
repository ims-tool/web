var Condition = {
		
		init: function ()
		{
			this.loadConditions();
			this.loadForms();
			this.loadConditions();
			this.saveCondition();
			this.editCondition();
			this.addCondition();
			this.addConditionGroup();
		},
		
		addConditionGroup: function ()
		{
			$('.btn-add-conditiongroup').unbind().click(function (){
				ConditionGroup.init();
				ConditionGroup.edit = false;
				ConditionGroup.show();
			});
		},
		
		editConditionGroup: function ()
		{
			$('.btn-edit-conditiongroup').unbind().click(function (){
				ConditionGroup.init();
				ConditionGroup.show();
				params = $('.table-conditiongroup .line-selected').find('input').val();
				ConditionGroup.loadConditionGroup(params);
			});
		},
		
		removeConditionGroup: function ()
		{
			$('#conditionModal .btn-excluir-conditiongroup').unbind().click(function (){
				$('.table-conditiongroup .line-selected').remove();
			});
		},
		
		loadConditions: function ()
		{
			$('input.selecao-condition').select2(Util.search('Condition'));
		},
		
		cleanCondition: function ()
		{
			$('.form-condition').find('[name="id"]').val('');
			$('.form-condition').find('[name="conditiontypeid"]').val('');
			$('.form-condition').find('[name="description"]').val('');
			trySelectVersion();
		},
		
		addCondition: function ()
		{
			$('#conditionModal .btn-add-element').unbind().click(function (){
				Condition.cleanCondition();
			});
		},
		
		editCondition: function ()
		{
			$('#conditionModal .btn-edit-element').unbind().click(function (){
				var id = $('#conditionModal input.selecao-condition').val();
				Condition.loadCondition(id);
			});
		},
		
		show: function ()
		{
			$('#conditionModal').modal();
			this.cleanCondition();
		},
		
		showAdd: function ()
		{
			$('#conditionModal').modal();
			Condition.init();
		},
		
		loadConditionParameters: function (parameters){
			
			$('.table-conditionParameters tbody').html('');
			for(key in parameters)
			{
				var param = parameters[key];
				$('.table-conditionParameters tbody').append('<tr><td>'+param.paramname+'</td><td>'+param.paramvalue+'</td></tr>');
			}
			
			ConditionGroup.removeParameter();
			Util.tableSelect('.table-conditionParameters tbody');
			
		},
		
		loadUrlData: function (group)
		{
			var parameters = ''; 
			for(key in group.conditionparameters)
			{
				var param = group.conditionparameters[key];
				parameters  += param.paramname+':'+param.paramvalue+'|';
			}
			parameters = parameters.substr(0,parameters.length-1);
			
			var values = '';
			
			for(key in group.conditionvalue)
			{
				var value = group.conditionvalue[key];
				values += 'conditionvalue.operation='+Util.translateOperation(value.operation)+'&';
				values += 'conditionvalue.values='+Util.getValues(value)+'&';
				values += 'conditionvalue.tagtrue='+value.tagtrue+'&';
				values += 'conditionvalue.tagfalse='+value.tagfalse+'&';
			}
			values = (values != '') ? values.substr(0,values.length-1) : '';
				
			var url = '';
			url = 'conditiongroup.description='+group.description+'&';
			url += 'conditiongroup.conditionmapid='+group.conditionmapid+'&';
			url += 'conditiongroup.tag='+group.tag+'&';
			url += 'conditiongroup.parameters='+parameters+'&';
			url += 'conditionvalue=';
			
			if(values != '')
			{
				url += 'true&'+values;
			}
			
			return url;
		},
		
		loadConditionGroups: function (conditiongroup)
		{
			$('.table-conditiongroup tbody').html('');
			
			for( key in conditiongroup )
			{
				
				var group = conditiongroup[key];
				var line = '<tr><td>'+group.description;
				
		        line += '<span style="margin-left: 10px;" class="popoverElement" html="true" data-container="body" data-toggle="popover" data-placement="right" data-content="';
		        line += '<b>Metodo:</b> '+group.conditionmapid+' <br />';
		        line += '<b>Parametros:</b> ';
		        
		        for(i in group.conditionparameters)
		        	line += group.conditionparameters[i].paramname + ":" + group.conditionparameters[i].paramvalue+"|";
		        line = line.substr(0,line.length-1)+'<br />';
		        
		        if(group.conditionvalue.length > 0)
				{
		        	for(x in group.conditionvalue)
		        	{
			        		line += '<b>Operação:</b> '+Util.translateOperation(group.conditionvalue[x].operation)+' <br />';
			        		line += '<b>Valores:</b> '+Util.cleanArray(Util.getValues(group.conditionvalue[x]).split(','))+' <br />';
			        		line += '<b>Tagtrue:</b> '+group.conditionvalue[x].tagtrue+' <br />';
			        		line += '<b>Tagfalse:</b> '+group.conditionvalue[x].tagfalse+'<br />';
		        	}
				}
		        
		        line += ' <br />';
		        line += '">';
		        line += '<span class="glyphicon glyphicon-search"></span></span>';
		        line += '<input type="hidden" value="'+Condition.loadUrlData(group)+'" />';
				line += '</td></tr>';
				
				line += '</td></tr>';
				$('.table-conditiongroup tbody').append(line);
				$('.popoverElement').popover({html: true,trigger: 'hover'});
				Util.tableSelect('.table-conditiongroup tbody');
			}
		},
		
		loadCondition: function (id)
		{
			$.get('Crud?action=loadFullElement&id='+id+'&type=Condition',function (condition){
				
				$('.form-condition').find('[name="id"]').val(condition.id);
				$('.form-condition').find('[name="name"]').val(condition.name);
				$('.form-condition').find('[name="description"]').val(condition.description);
				
				$.get('Crud?action=loadElement&id='+condition.tag+'&type=Tag',function (tag){
					$('.form-condition input.selecao-tag').select2('data',{id: tag.id, text: tag.description});
				});
				
				Condition.loadConditionGroups(condition.conditiongroup);
				Condition.editConditionGroup();
			});
		},
		
		loadForms: function ()
		{
			$('input.selecao-form').select2(Util.search("Form"));
		},
		
		validate: function ()
		{
			var msg = '';
			var name = $('.form-condition').find('[name="name"]').val();
			var description = $('.form-condition').find('[name="description"]').val();
			var tag = $('.form-condition').find('[name="tag"]').val();
			var versionid = $('.form-condition').find('[name="versionid"]').val();
			var conditionGroups = $('.table-conditiongroup tbody tr').length;
			
			if(name == '')
				msg += 'Informe o campo nome <br />';
			if(description == '')
				msg += 'Informe o campo descrição <br />';
			if(tag == '')
				msg += 'Informe o campo tag <br />';
			if(conditionGroups == 0)
				msg += 'Informe ao menos um condition group<br />';
			if(versionid == '')
				msg += 'Informe o campo versão';
			if(msg != ''){
				APP.error(msg);
				return false;
			} else {
				return true;
			}
		},
		
		saveCondition: function ()
		{
			$('.btn-save-condition').unbind().click(function (){
				
				if(Condition.validate())
				{
					var id = $('.form-condition').find('[name="id"]').val();
					var name = $('.form-condition').find('[name="name"]').val();
					var description = $('.form-condition').find('[name="description"]').val();
					var tag = $('.form-condition').find('[name="tag"]').val();
					
					var data = 'id='+id+'&name='+name+'&description='+description+'&tag='+tag;
					
					$.each($('.table-conditiongroup tbody tr'),function (index,obj){
						data += '&'+$(obj).find('input').val();
					});
					
					$.post("Crud?action=saveElement&elementType=Condition&"+data,function (){
						APP.success("Condition salvo com sucesso!");
					});
				}
			});
		}
};

var ConditionGroup = {
		
		data: '',
		edit: false,
		init: function ()
		{
			this.save();
			this.loadMaps();
			this.addParameter();
			this.addResultValue();
			this.removeParameter();
			Util.tableSelect('.table-conditionParameters tbody');
		},
	
		show: function ()
		{
			$('#conditionGroupModal').modal();
			
		},
		
		addResultValue: function ()
		{
			$('.btn-add-conditionvalue').unbind().click( function (){
				ConditionValue.showAdd();
			});
		},
		
		addParameter: function ()
		{
			$('.btn-add-conditionparameters').unbind().click(function (){
				$('#conditionParametersModal').modal();
				ConditionParameters.init();
			});
		},
		
		removeParameter: function ()
		{
			$('#conditionGroupModal .btn-excluir-conditionparameters').unbind().click(function (){
				$('.table-conditionParameters .line-selected').remove();
			});
		},
		
		loadConditionGroup: function (params)
		{
			var conditionvalue = '';
			var conditionvalues = '';
			var data = Util.urlToArray(params);
			this.edit = true;
			$('.form-conditiongroup').find('[name="conditiongroup.description"]').val(data.conditiongroupdescription);
			
			$.get('Crud?action=loadElement&id='+data.conditiongroupconditionmapid+'&type=ConditionMap',function (conditionmap){
				$('.form-conditiongroup input.selecao-conditionmap').select2('data',{id: conditionmap.id, text: conditionmap.name});
			});
			
			var parameters = data.conditiongroupparameters.split('|');
			
			$('.table-conditionParameters tbody').html('');
			
			for( key in parameters){
				parameters[key] = parameters[key].split(':');
				if(parameters[key][1] != undefined)
					$('.table-conditionParameters tbody').append('<tr><td>'+parameters[key][0]+'</td><td>'+parameters[key][1]+'</td></tr>');
			}
			ConditionGroup.removeParameter();
			Util.tableSelect('.table-conditionParameters tbody');
			 
			$('.table-conditionvalue tbody').html('');
			
			if(Util.isArray(data.conditionvalueoperation)){
				
				for ( key in data.conditionvalueoperation)
				{
					data.conditionvaluevalues[key] = Util.cleanArray(data.conditionvaluevalues[key].split(',')).join(',');
					
					conditionvalue = '';
					conditionvalue += 'conditionvalue.operation='+data.conditionvalueoperation[key]+'&';
					conditionvalue += 'conditionvalue.values='+data.conditionvalues[key]+'&';
					conditionvalue += 'conditionvalue.tagtrue='+data.conditionvaluetagtrue[key]+'&';
					conditionvalue += 'conditionvalue.tagfalse='+data.conditionvaluetagfalse[key];
					conditionvalue = '<input type="hidden" value="'+conditionvalue+'" />';
					
					$('.table-conditionvalue tbody').append(
							'<tr><td>'+data.conditionvalueoperation[key]+'</td><td>'+
							data.conditionvalues[key]+'</td><td>'+
							data.conditionvaluetagtrue[key]+
							'</td><td>'+data.conditionvaluetagfalse[key]+conditionvalue+'</tr>'
					);
				}
			} else {
				data.conditionvaluevalues = Util.cleanArray(data.conditionvaluevalues.split(',')).join(',');
				
				conditionvalue = '';
				conditionvalue += 'conditionvalue.operation='+data.conditionvalueoperation+'&';
				conditionvalue += 'conditionvalue.values='+data.conditionvaluevalues+'&';
				conditionvalue += 'conditionvalue.tagtrue='+data.conditionvaluetagtrue+'&';
				conditionvalue += 'conditionvalue.tagfalse='+data.conditionvaluetagfalse;
				conditionvalue = '<input type="hidden" value="'+conditionvalue+'" />';
				
				$('.table-conditionvalue tbody').append(
						'<tr><td>'+data.conditionvalueoperation+'</td><td>'+
						data.conditionvaluevalues+'</td><td>'+
						data.conditionvaluetagtrue+
						'</td><td>'+data.conditionvaluetagfalse+conditionvalue+'</tr>'
				);
			}
			
			Util.tableSelect('.table-conditionvalue tbody');
			Util.tableSelect('.table-conditionParameters tbody');
			ConditionGroup.removeParameter();
		},
		
		loadMaps: function ()
		{
			$('input.selecao-conditionmap').select2(Util.search('ConditionMap'));
		},
		
		validate: function ()
		{
			var msg = '';
			var description = $('.form-conditiongroup').find('[name="conditiongroup.description"]').val();
			var conditionmap = $('.form-conditiongroup').find('[name="conditiongroup.conditionmapid"]').val();
			
			if(description == '')
				msg += 'Informe o campo descrição<br />';
			if(conditionmap == '')
				msg += 'Informe o campo metodo<br />';
			
			if(msg == '')
			{
				return true;
			} else {
				APP.error(msg);
				return false;
			}
		},
		
		save: function (){
			$('.btn-save-conditiongroup').unbind().click(function (){
				
				if(!ConditionGroup.validate())
					return false;
				
				var data = '';
				var auxData = '';
				var parameters = '';
				var description = $('.form-conditiongroup').find('[name="conditiongroup.description"]').val();
				var conditionmap = $('.form-conditiongroup').find('[name="conditiongroup.conditionmapid"]').val();
				var conditionmapText = $('.form-conditiongroup').find('div.selecao-conditionmap').find('.select2-chosen').text();
				
				data += 'conditiongroup.description='+description+'&';
				data += 'conditiongroup.conditionmapid='+conditionmap+'&';
				
				if($('.table-conditionParameters tbody tr').length == 0)
					data += 'conditiongroup.parameters=';
				
				$.each($('.table-conditionParameters tbody tr'),function(index,obj){
					parameters += $(obj).find('td').eq(0).text()+':'+$(obj).find('td').eq(1).text()+',';
				});
				
				if(parameters != ''){
						auxData = parameters.substr(0,parameters.length-1);
						data += 'conditiongroup.parameters='+auxData;
				}
				
				var conditionvalues = $('.table-conditionvalue tbody tr');
				
				if(conditionvalues.length > 0)
				{
					data += '&conditionvalue=true';

					$.each($('.table-conditionvalue tbody tr'),function(index,obj){
						data += '&'+$(obj).find('input').val();
					});
				} else
					data += '&conditionvalue=';
				
				hiddenData = '<input type="hidden" name="conditionGroupData" value="'+data+'" />';
				//line += '<table>';
				//line += '<tbody>';
				
				var line = '<tr><td>'+description;
		        line += '<span style="margin-left: 10px;" class="popoverElement" html="true" data-container="body" data-toggle="popover" data-placement="right" data-content="';
		        line += '<b>Metodo:</b> '+conditionmapText+' <br />';
		        line += '<b>Parametros:</b> '+auxData+' <br />';
		        
		        if(conditionvalues.length > 0)
				{
		        	$.each($('.table-conditionvalue tbody tr'),function(index,obj){
		        		line += '<b>Operação:</b> '+$(obj).find('td').eq(0).text()+' <br />';
		        		line += '<b>Valores:</b> '+$(obj).find('td').eq(1).text()+' <br />';
		        		line += '<b>Tagtrue:</b> '+$(obj).find('td').eq(2).text()+' <br />';
		        		line += '<b>Tagfalse:</b> '+$(obj).find('td').eq(3).text();
					});
				}
		        line += '">';
		        line += '<span class="glyphicon glyphicon-search"></span></span>';
		        line += hiddenData;
				line += '</td></tr>';
				//var line = '<tr><td colspan="2">Descrição: '+description+'</td><td>Metodo: '+conditionmapText+'</td><td colspan="2">Parametros: '+auxData+hiddenData+'</td></tr>';
				/*var line = '<tr><td colspan="2">Descrição: '+description+'</td><td>Metodo: '+conditionmapText+'</td><td colspan="2">Parametros: '+auxData+hiddenData+'</td></tr>';
				*/
				/*
				if(conditionvalues.length > 0)
				{
					$.each($('.table-conditionvalue tbody tr'),function(index,obj){
						line += '<tr><td>( '+conditionmapText+'('+auxData+') '+$(obj).find('td').eq(0).text()+' '+$(obj).find('td').eq(1).text()+' ) ? tag = '+$(obj).find('td').eq(2).text()+' : '+$(obj).find('td').eq(3).text()+'; //description </td></tr>';
					});

					$.each($('.table-conditionvalue tbody tr'),function(index,obj){
						line += '<tr>';
						line +='<td></td>';
						line += '<td>Operação: '+$(obj).find('td').eq(0).text()+'</td>';
						line += '<td>Valores: '+$(obj).find('td').eq(1).text()+'</td>';
						line += '<td>Tagtrue: '+$(obj).find('td').eq(2).text()+'</td>';
						line += '<td>Tagfalse: '+$(obj).find('td').eq(3).text()+'</td>';
						line +='</tr>';
					});
				}*/
				
				//line += '</tbody></table>';
				
				line += '</td></tr>';
				if(ConditionGroup.edit)
					$('.table-conditiongroup tbody .line-selected').remove();
				
				$('.table-conditiongroup tbody').append(line);
				
				Util.tableSelect('.table-conditiongroup tbody');
				Condition.removeConditionGroup();
				$('.popoverElement').popover({html: true,trigger: 'hover'});
				$('#conditionGroupModal').modal('hide');
			});
		}
};

var ConditionMap = {
	
		init: function ()
		{
			this.addConditionMap();
			this.loadVersion();
			this.cleanConditionMap();
		},
		
		loadVersion: function ()
		{
			$('.form-conditionmap input.selecao-version').select2(Util.search('Version'));
		},
		
		cleanConditionMap: function ()
		{
			$('.form-conditionmap').find('[name="name"]').val('');
			$('.form-conditionmap').find('[name="type"]').val('');
			$('.form-conditionmap').find('[name="description"]').val('');
			$('.form-conditionmap').find('[name="methodreference"]').val('');
			$('.form-conditionmap').find('[name="versionid"]').val('');
			trySelectVersion();
		},
		
		showAdd: function ()
		{
			$('#conditionMapModal').modal();
			this.init();
		},
		
		validate: function ()
		{
			var msg = '';
			var name = $('.form-conditionmap').find('[name="name"]').val();
			var type = $('.form-conditionmap').find('[name="type"]').val();
			var description = $('.form-conditionmap').find('[name="description"]').val();
			var methodreference = $('.form-conditionmap').find('[name="methodreference"]').val();
			var versionid = $('.form-conditionmap').find('[name="versionid"]').val();
			
			if(name == '')
				msg += 'Informe o campo nome<br />';
			if(type == '')
				msg += 'Informe o campo tipo<br />';
			if(description == '')
				msg += 'Informe o campo descrição<br />';
			if(methodreference == '')
				msg += 'Informe o campo metodo<br />';
			if(versionid == '')
				msg += 'Informe o campo versão<br />';
			
			if(msg == '')
			{
				return true;
			} else {
				APP.error(msg);
				return false;
			}
		},
		
		addConditionMap: function ()
		{
			$('.btn-save-conditionmap').unbind().click(function (){
				var data = $('.form-conditionmap').serialize();
				
				if(ConditionMap.validate())
					$.post('Crud?action=save&elementType=ConditionMap&'+data,function (){
						$('#conditionMapModal').modal('hide');
						APP.success("ConditionMap salvo com sucesso");
					});
			});
		}
};

var ConditionParameters = {
	
		init: function ()
		{
			this.saveConditionParameters();
		},
		
		validate: function ()
		{
			var msg = '';
			var paramname = $('.form-conditionparameters').find('[name="paramname"]').val();
			var paramvalue = $('.form-conditionparameters').find('[name="paramvalue"]').val();
			
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
		
		saveConditionParameters: function ()
		{
			$('.btn-save-conditionparameters').unbind().click(function (){
				var paramname = $('.form-conditionparameters').find('[name="paramname"]').val();
				var paramvalue = $('.form-conditionparameters').find('[name="paramvalue"]').val();
				
				if(ConditionParameters.validate())
				{
					$('.table-conditionParameters tbody').append('<tr><td>'+paramname+'</td><td>'+paramvalue+'</td></tr>');
					$('#conditionParametersModal').modal('hide');
					ConditionGroup.removeParameter();
					Util.tableSelect('.table-conditionParameters tbody');
				}
			});
		}
};

var ConditionValue = {
		
		init: function ()
		{
			$('[name="values"]').select2({
				  tags: true, tokenSeparators: [',', ' '], maximumSelectionLength: 10
			});
			
			this.saveConditionValue();
		},
		
		showAdd: function ()
		{
			$('#tratamentoResultadoModal').modal();
			ConditionValue.init();
		},
		
		validate: function ()
		{
			var msg = '';
			
			var operation = $('.form-tratamentoResultado').find('[name="operation"]').val();
			var values = $('.form-tratamentoResultado').find('[name="values"]').val();
			var tagtrue = $('.form-tratamentoResultado').find('[name="tagtrue"]').val();
			var tagfalse = $('.form-tratamentoResultado').find('[name="tagfalse"]').val();
			
			if(operation == '')
				msg += 'Informe a operação <br />';
			if(values == '')
				msg += 'Informe o(s) valor(es) de comparação<br />';
			if(tagtrue == '')
				msg += 'Informe a tag para resultado verdadeiro <br />';
			if(tagfalse == '')
				msg += 'Informe a tag para resultado falso <br />';
			
			if(msg == '')
				return true;
			else {
				APP.error(msg);
				return false;
			}
		},
		
		saveConditionValue: function ()
		{
			$('.btn-save-tratamentoResultado').unbind().click(function (){
				
				var data = '';
				
				if(ConditionValue.validate())
				{
					var operation = $('.form-tratamentoResultado').find('[name="operation"]').val();
					var values = $('.form-tratamentoResultado').find('[name="values"]').val();
					var tagtrue = $('.form-tratamentoResultado').find('[name="tagtrue"]').val();
					var tagfalse = $('.form-tratamentoResultado').find('[name="tagfalse"]').val();
					
					if(ConditionValue.validate())
					{
						data += 'conditionvalue.operation='+operation+'&';
						data += 'conditionvalue.values='+values+'&';
						data += 'conditionvalue.tagtrue='+tagtrue+'&';
						data += 'conditionvalue.tagfalse='+tagfalse;
						data = '<input type="hidden" value="'+data+'" />';

						$('.table-conditionvalue tbody').append('<tr><td>'+operation+'</td><td>'+values+'</td><td>'+tagtrue+'</td><td>'+tagfalse+data+'</tr>');
						$('#tratamentoResultadoModal').modal('hide');
						Util.tableSelect('.table-conditionvalue tbody');
					}
				}
			});
		}
}


$(document).ready(function (){
	Condition.init();
});