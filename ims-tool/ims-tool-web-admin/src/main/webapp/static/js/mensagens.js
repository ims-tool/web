jQuery(function(){
	$('.btn-toggle').click(function() {
		if(confirm("Você tem certeza que deseja alterar o status desse serviço?")) {
			var id = $(this).data("id");
		    $(this).find('.btn').toggleClass('active');
	
		    if ($(this).find('.on.btn-success').size()>0) {
		    	toggleStatus(id, 'false');
		    	$(this).find('.on.btn-success').toggleClass('btn-success');
		    	$(this).find('.off').toggleClass('btn-danger');
		    	$(this).find('.btn').toggleClass('btn-default');
		    	var btn = $(this).closest("tr").children[0];
		    	$.ajax({
		    		url: "/togglestatus",
		    		method: "post",
		    		data: {"id":btn.textContent, "status":"A"}
		    	});
		    	return;
		    }
		    if ($(this).find('.off.btn-danger').size()>0) {
		    	toggleStatus(id, 'true');
		    	$(this).find('.off.btn-danger').toggleClass('btn-danger');
		    	$(this).find('.on').toggleClass('btn-success');
		    	$(this).find('.btn').toggleClass('btn-default');
		    	var btn = $(this).closest("tr").children[0];
		    	$.ajax({
		    		url: "/togglestatus",
		    		method: "post",
		    		data: {"id":btn.textContent, "status":"I"}
		    	});
		    	return;
		    }
		}
	});
	
	$(".spinner").TouchSpin({
		min: 0, // Minimum value.
		max: 99, // Maximum value.
		boostat: 5, // Boost at every nth step.
		maxboostedstep: 10, // Maximum step when boosted.
		step: 1, // Incremental/decremental step on up/down change.
		stepinterval: 100, // Refresh rate of the spinner in milliseconds.
		stepintervaldelay: 500 // Time in milliseconds before the spinner starts to spin.
	});
	
	$(".spinner").change(function(){
		if(confirm("Você tem certeza que deseja alterar o timeout desse serviço?")) {
			var id = $(this).data("id");
			var timeout = $(this).val();
			changeTimeout(id, timeout);
		}
	});
	
	$(".datahora").mask("00/00/0000 00:00");
	
});

function toggleStatus(id, status) {
	$.post( ctxPath + "/mensagens/togglestatus", { id: id, status: status });
}

function changeTimeout(id, timeout){
	$.post( ctxPath + "/mensagens/changetimeout", { id: id, timeout: timeout });
}