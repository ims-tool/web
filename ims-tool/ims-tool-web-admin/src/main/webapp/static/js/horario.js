jQuery(function(){
	var urlHorarioInicioUpdate = ctxPath + '/horario/salvarHorarioInicio';
	var urlHorarioFimUpdate = ctxPath + '/horario/salvarHorarioFim';
	$.fn.editable.defaults.mode = 'inline';
	$.fn.editable.defaults.emptytext = 'Vazio';

	$(".dropdown-menu li a").click(function(){
		  var selText = $(this).text();
		  $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
		  var url = ctxPath + "/horario/pesquisar?tipoServico=" + $('.btn-select2').text();
			
		  $(location).attr('href', url);
		});

		$("#btnSearch").click(function(){
			var url = ctxPath + "/horario/pesquisar?tipoServico=" + $('.btn-select2').text();
			
			$(location).attr('href', url);
		});
		
	$(".param-inicio").editable({
		url: urlHorarioInicioUpdate,
		combodate: {
            minuteStep: 1
        },
		ajaxOptions: {
			type: 'POST',
			dataType: 'json'
		},
	});
	
	
	$(".param-fim").editable({
		url: urlHorarioFimUpdate,
		combodate: {
            minuteStep: 1
        },
		ajaxOptions: {
			type: 'POST',
			dataType: 'json'
		}
	});
	
	$("#horaInicio").mask("99:99");
	$("#horaFim").mask("99:99");
	
});

function Mascara_Hora(Hora, campo){  
	   var hora01 = '';  
	   hora01 = hora01 + Hora;  
	   if (isNumber(hora01) && hora01.length == 2){  
	      hora01 = hora01 + ':';  
	      campo.value = hora01;  
	   }  
	   if (hora01.length == 5){  
	      Verifica_Hora(campo);  
	   }  
	}  
	  
	function Verifica_Hora(campo){  
	   hrs = (campo.value.substring(0,2));  
	   min = (campo.value.substring(3,5));  
	   estado = "";  
	   if ((hrs < 00 ) || (hrs > 23) || ( min < 00) ||( min > 59)){  
	      estado = "errada";  
	   }  
	  
	   if (campo.value == "") {  
	      estado = "errada";  
	   }  
	   if (estado == "errada") {  
	      alert("Hora invalida!");  
	      campo.focus();  
	      campo.value = "";
	   }  
	} 
	
	function isNumber(n) {
	    return !isNaN(parseFloat(n)) && isFinite(n);
	}


