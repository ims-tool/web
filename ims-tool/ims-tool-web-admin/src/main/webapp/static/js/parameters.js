var parameters = {
		
		
		btnEditar: '.btn-editar-parameter',
		btnExcluir: '.btn-excluir-parameter',
		valValue: '',
		
		init: function ()
		{
			this.editarClick();
			this.excluirClick();
			this.editarSelect();
			this.selectTypeChange();
			this.valueBlur();
			
			this.valValue = $('#value').val();
			this.fieldType($('#type').val());
		},
		
		editarSelect: function ()
		{
			var val = $('#type').attr('selectedValue');
			if(val != undefined)
				$('#type').val(val);
		},
		
		_fieldTextTemplate: function ()
		{
		 	return 	'<label>Valor</label>'+
		 			'<input type="text" class="form-control" id="value" name="value" required="required">';
		},
		
		_fieldSelectTemplate: function ()
		{
		 	return 	'<label>Valor</label>'+
		 			'<select class="form-control" id="value" name="value" required="required">'+
		 			'<option value="true">Verdadeiro</option>'+
		 			'<option value="false">Falso</option>';
		},
		
		fieldType: function (val)
		{
			var self = this;
			if(val == 'int'){
				$('.div-value').html(self._fieldTextTemplate());
				self.valueInt(true);
				self.valueBlur();
				var i = parseInt(self.valValue);
				if (!isNaN(i))
					$('#value').val(i);
				else 
					$('#value').val(0);
			} else if (val == 'string') {
				$('.div-value').html(self._fieldTextTemplate());
				self.valueInt(false);
				self.valueBlur();
				var i = $('#value').val();
				if (i.length > 0 && (i != "true" || i != "false"))
					$('#value').val(i);
				else
					$('#value').val("");
			} else{
				$('.div-value').html(self._fieldSelectTemplate());
				self.valueInt(false);
				self.valueBlur();
				$('#value').val(self.valValue);
			}
			
//			try{
//				$('#value').val(self.valValue); 
//			} catch(e){}
		},
		
		valueBlur: function (){
			/*var self = this;
			
			$('#value').unbind().blur(function (){
				var validation = $('#validation').val();
				var value = $('#value');
				value.css("border","1px solid #cccccc");
				
				if(validation){
					
					regex = new RegExp(validation);
					
					if(!regex.test(value.val())){
						value.val("");
						value.css("border","1px solid red");
					} 
				} 
			});*/
		},
		
		selectTypeChange: function ()
		{
			var self = this;
			
			$('#type').change(function (){
				self.valValue = $('#value').val();
				self.fieldType($(this).val());
			});
		},
		
		valueInt: function (flag)
		{
			if(flag)
				$('#value').unbind().keypress(function (e){
					return !(e.keyCode<48 || e.keyCode>57);
				});
			else
				$('#value').unbind().keypress(function(){
					return true;
				});
		},
		
		editarClick: function ()
		{
			var self = this;
			
			$(this.btnEditar).click(function (){
				var id = $(this).attr('id');
				$('#editarParameter .parameterId').val(id);
				$('#editarParameter').submit();
			});
		},
		
		excluirClick: function ()
		{
			var self = this;
			
			$(this.btnExcluir).click(function (){
				var id = $(this).attr('id');
				$('#excluirParameter .parameterId').val(id);
				$('#excluirParameter').submit();
			});
		}
		
};


$(document).ready(function (){
	parameters.init();
});