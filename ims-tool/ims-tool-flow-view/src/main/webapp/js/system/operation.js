
var Operation = {
		
		init: function ()
		{
			this.loadOperations();
			this.loadForms();
			this.loadOperations();
			this.saveOperation();
			this.editOperation();
			this.addOperation();
			this.addOperationGroup();
			this.removeOperationGroup();
			this.editOperationGroup();
		},
		
		addOperationGroup: function ()
		{
			$('.btn-add-operationgroup').unbind().click(function (){
				OperationGroup.init();
				OperationGroup.show();
			});
		},
		
		removeOperationGroup: function ()
		{
			$('.btn-excluir-operation').unbind().click(function (){
				if($('.table-operationgroup .line-selected').length > 0)
					$('.table-operationgroup .line-selected').remove();
			});
		},
		
		editOperationGroup: function ()
		{
			$('.btn-edit-operationgroup').unbind().click(function (){
				
				if($('.table-operationgroup .line-selected').length > 0)
				{
					var data = $('.table-operationgroup .line-selected').find('input').val();
					OperationGroup.init();
					OperationGroup.show();
					OperationGroup.load(data);
				}
			});
		},
		
		loadTags: function ()
		{
			$('.form-operation input.selecao-tag').select2(Util.search('Tag'));
		},
		
		loadVersions: function ()
		{
			$('.form-operation input.selecao-version').select2(Util.search('Version'));
		},
		
		loadOperations: function ()
		{
			$('input.selecao-operation').select2(Util.search('Operation'));
		},
		
		cleanOperation: function ()
		{
			$('.form-operation').find('[name="id"]').val('');
			$('.form-operation').find('[name="description"]').val('');
			$('.form-operation').find('[name="tag"]').val('');
			$('.form-operation').find('[name="versionid"]').val('');
			$('.table-operationgroup tbody').html('');
			this.loadTags();
			this.loadVersions();
			trySelectVersion();
		},
		
		addOperation: function ()
		{
			$('#operationModal .btn-add-element').unbind().click(function (){
				Operation.cleanOperation();
			});
		},
		
		editOperation: function ()
		{
			$('#operationModal .btn-edit-element').unbind().click(function (){
				var id = $('input.selecao-operation').val();
				Operation.loadOperation(id);
			});
		},
		
		show: function ()
		{
			$('#operationModal').modal();
			this.init();
			this.cleanOperation();
		},
		
		loadOperation: function (id)
		{
			$.get('Crud?action=loadFullElement&id='+id+'&type=Operation',function (operation){
				
				$('.form-operation').find('[name="id"]').val(operation.id);
				$('.form-operation').find('[name="name"]').val(operation.name);
				$('.form-operation').find('[name="description"]').val(operation.description);
				
				$.get('Crud?action=loadElement&id='+operation.tag+'&type=Tag',function (tag){
					$('.form-operation input.selecao-tag').select2('data',{id: tag.id, text: tag.description});
				});
				
				$.get('Crud?action=loadElement&id='+operation.versionid+'&type=Version',function (version){
					$('.form-operation input.selecao-version').select2('data',{id: version.id, text: version.description});
				});
				
				var data = '';
				var auxData = '';
				var parameters = '';
				var description = $('.form-operationgroup').find('[name="operationgroup.description"]').val();
				var operationmap = $('.form-operationgroup').find('[name="operationgroup.operationmapid"]').val();
				var operationmapText = $('.form-operationgroup').find('div.selecao-operationmap').find('.select2-chosen').text();
				
				$('.table-operationgroup tbody').html('');
				
				for(key in operation.operationgroup)
				{
					var data = '';
					var parameters = '';
					var group = operation.operationgroup[key];
					
					data += 'operationgroup.id='+group.id+'&';
					data += 'operationgroup.description='+group.description+'&';
					data += 'operationgroup.operationmapid='+group.operationmapid+'&';
					//data += 'operationgroup.parameters='+operation.operation;
					
					
					for(key in group.conditionparameters)
						parameters += group.conditionparameters[key].paramname+':'+group.conditionparameters[key].paramvalue+'|';
					parameters = parameters.substr(0,parameters.length-1);
					
					data += 'operationgroup.parameters='+parameters;
					
					hiddenData = '<input type="hidden" name="operationGroupData" value="'+data+'" />';
					
					$('.table-operationgroup tbody').append('<tr><td>'+group.description+'</td><td>'+group.conditionmap.name+'</td><td>'+parameters+hiddenData+'</td></tr>');
				}
				
				Operation.removeOperationGroup();
				Util.tableSelect('.table-operationgroup tbody');
			});
		},
		
		loadForms: function ()
		{
			$('input.selecao-form').select2(Util.search("Form"));
		},
		
		
		validate: function ()
		{
			var name = $('.form-operation').find('[name="name"]').val();
			var description = $('.form-operation').find('[name="description"]').val();
			var tag = $('.form-operation').find('[name="tag"]').val();
			var version = $('.form-operation').find('[name="versionid"]').val();
			var operationgroups = $('.table-operationgroup tbody tr').length;

			var msg = '';
			
			if(name == '')
				msg += 'Informe o campo nome <br />';
			if(description == '')
				msg += 'Informe o campo descrição <br />';
			if(tag == '')
				msg += 'Informe o campo tag <br />';
			if(version == '')
				msg += 'Informe o campo versão <br />';
			if(operationgroups.length == 0)
				msg += 'Informe ao menos um operationgroup <br />';
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else 
				return true;
		},
		
		saveOperation: function ()
		{
			$('.btn-save-operation').unbind().click(function (){
				
				if(!Operation.validate())
					return false;
				
				var id = $('.form-operation').find('[name="id"]').val();
				var name = $('.form-operation').find('[name="name"]').val();
				var description = $('.form-operation').find('[name="description"]').val();
				var tag = $('.form-operation').find('[name="tag"]').val();
				var versionid = $('.form-operation').find('[name="versionid"]').val();
				
				var data = 'id='+id+'&name='+name+'&description='+description+'&tag='+tag+'&versionid='+versionid;
				
				$.each($('.table-operationgroup tbody tr'),function (index,obj){
					data += '&'+$(obj).find('input').val();
				});
				
				$.post("Crud?action=saveElement&elementType=Operation&"+data,function (){
					APP.success("Operation salvo com sucesso!");
				});
			});
		}
};

var OperationGroup = {
		
		data: '',
		
		init: function ()
		{
			this.save();
			this.loadMaps();
			this.addParameter();
			this.removeParameter();
			Util.tableSelect('.table-operationParameters tbody');
		},
	
		show: function ()
		{
			$('#operationGroupModal').modal();
			this.cleanOperationGroup();
			
		},
		
		cleanOperationGroup: function ()
		{
			$('.form-operationgroup').find('[name="id"]').val('');
			$('.form-operationgroup').find('[name="operationgroup.description"]').val('');
			$('.form-operationgroup input.selecao-operationmap').val('');
			$('.table-operationParameters tbody').html('');
		},
		
		addParameter: function ()
		{
			$('.btn-add-operationparameters').unbind().click(function (){
				$('#operationParametersModal').modal();
				OperationParameters.init();
			});
		},
		
		load: function (params)
		{
			params = Util.urlToArray(params);
			
			$('.form-operationgroup').find('[name="id"]').val(params.operationgroupid);
			$('.form-operationgroup').find('[name="operationgroup.description"]').val(params.operationgroupdescription);
			
			$.get('Crud?action=loadElement&id='+params.operationgroupoperationmapid+'&type=OperationMap',function (operationmap){
				$('.form-operationgroup input.selecao-operationmap').select2('data',{id: operationmap.id, text: operationmap.name});
			});
			
			var  parameters = params.operationgroupparameters.split('|');
			for(key in parameters)
				parameters[key] = parameters[key].split(':');
			
			$('.table-operationParameters tbody').html('');
			
			for(key in parameters)
				$('.table-operationParameters tbody').append('<tr><td>'+parameters[key][0]+'</td><td>'+parameters[key][1]+'</td></tr>');
			
			this.removeParameter();
			Util.tableSelect('.table-operationParameters tbody');
		},
		
		removeParameter: function ()
		{
			$('.btn-excluir-operationparameters').unbind().click(function (){
				if($('.table-operationParameters tbody tr.line-selected').length > 0)
					$('.table-operationParameters tbody tr.line-selected').remove();
			});
		},
		
		loadMaps: function ()
		{
			
			$('input.selecao-operationmap').select2(Util.search('OperationMap'));
		},
		
		save: function (){
			$('.btn-save-operationgroup').unbind().click(function (){
				var data = '';
				var auxData = '';
				var parameters = '';
				var description = $('.form-operationgroup').find('[name="operationgroup.description"]').val();
				var operationmap = $('.form-operationgroup').find('[name="operationgroup.operationmapid"]').val();
				var operationmapText = $('.form-operationgroup').find('div.selecao-operationmap').find('.select2-chosen').text();
				
				data += 'operationgroup.id=&';
				data += 'operationgroup.description='+description+'&';
				data += 'operationgroup.operationmapid='+operationmap+'&';
				
				if($('.table-operationParameters tbody tr').length == 0)
					data += 'operationgroup.parameters=';
				
				$.each($('.table-operationParameters tbody tr'),function(index,obj){
					parameters += $(obj).find('td').eq(0).text()+':'+$(obj).find('td').eq(1).text()+'|';
				});
				
				if(parameters != ''){
						auxData = parameters.substr(0,parameters.length-1);
						data += 'operationgroup.parameters='+auxData;
				}
				
				hiddenData = '<input type="hidden" name="operationGroupData" value="'+data+'" />';
				
				if($('.table-operationgroup tbody tr.line-selected').length > 0)
					$('.table-operationgroup tbody tr.line-selected').remove();
				
				$('.table-operationgroup tbody').append('<tr><td>'+description+'</td><td>'+operationmapText+'</td><td>'+auxData+hiddenData+'</td></tr>');
				
				Util.tableSelect('.table-operationgroup tbody');
				Operation.removeOperationGroup();
				$('#operationGroupModal').modal('hide');
			});
		}
};

var OperationMap = {
	
		init: function ()
		{
			this.addOperationMap();
		},
		
		showAdd: function ()
		{
			$('#operationMapModal').modal();
			this.init();
		},
		
		validate: function ()
		{
			var name = $('.form-operationmap').find('[name="name"]').val();
			var description = $('.form-operationmap').find('[name="description"]').val();
			var method = $('.form-operationmap').find('[name="methodreference"]').val();
			var logactive = $('.form-operationmap').find('[name="log_active"]').val();
			var version = $('.form-operationmap').find('[name="versionid"]').val();

			var msg = '';
			
			if(name == '')
				msg += 'Informe o campo nome <br />';
			if(description == '')
				msg += 'Informe o campo descrição <br />';
			if(method == '')
				msg += 'Informe o campo metodo <br />';
			if(logactive == '')
				msg += 'Informe o campo log <br />';
			if(version =='')
				msg += 'Informe o campo versão <br />';
			
			if(msg != '')
			{
				APP.error(msg);
				return false;
			} else 
				return true;
		},
		
		addOperationMap: function ()
		{
			$('.btn-save-operationmap').unbind().click(function (){
				var data = $('.form-operationmap').serialize();
				
				if(OperationMap.validate())
					$.post('Crud?action=save&elementType=OperationMap&'+data,function (){
						$('#operationMapModal').modal('hide');
						APP.success("OperationMap salvo com sucesso");
					});
			});
		}
};

var OperationParameters = {
	
		init: function ()
		{
			this.saveOperationParameters();
			this.cleanParameters();
		},
		
		cleanParameters: function (){
			var paramname = $('.form-operationparameters').find('[name="paramname"]').val('');
			var paramvalue = $('.form-operationparameters').find('[name="paramvalue"]').val('');
		},
		
		saveOperationParameters: function ()
		{
			$('.btn-save-operationparameters').unbind().click(function (){
				var paramname = $('.form-operationparameters').find('[name="paramname"]').val();
				var paramvalue = $('.form-operationparameters').find('[name="paramvalue"]').val();
				
				$('.table-operationParameters tbody').append('<tr><td>'+paramname+'</td><td>'+paramvalue+'</td></tr>');
				$('#operationParametersModal').modal('hide');
				Util.tableSelect('.table-operationParameters tbody');
			});
		}
};


$(document).ready(function (){
	Operation.init();
});