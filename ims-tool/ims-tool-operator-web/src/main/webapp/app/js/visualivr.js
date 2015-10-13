function startNewContext(){
	var valueTimeStamp = new Date().getTime();
	var ani = localStorage.getItem('cellphone');
	if(ani == null) {
		ani = "4100000000";
	}
	var instance = localStorage.getItem('instance');
	var context ='{"startDate":"'+ valueTimeStamp + '","call":{"dnis":"010101","ani":"' + ani + '","log":"","protocol":"","protocolId":"","ura":"412626"},"customer":{"document":"","aging":"0"},"instance":{"numberPhone":"' + ani + '","perfil":"0"}}'; 
	localStorage.setItem('context', context);
	//console.log('Contexto carregado com sucesso');
}

function startFlowIvr(nextFormId){
	
//	if(localStorage.getItem('instance') === null){
//		$('#modalLogin').modal('show');
//	}else{
		startNewContext();
		getNextForm(nextFormId);
//	}
	
}

function finishFlow(){
	removeElement('managerDiv');
	history.go(0);
}

function registerClient(){
	
	var valueTimeStamp = new Date().getTime();
	var valuePrompt = document.getElementById('loginNumber').value;
	var valuePromptCellphone = document.getElementById('celular').value;
	
	//limpar Campos
	document.getElementById('loginNumber').value = '';
	document.getElementById('celula').value = '';
	
	
	if(valuePrompt===''){ 
			alert('Favor preencher o numero de telefone residencial.');
	}else{
		localStorage.setItem('instance', valuePrompt);
		if(valuePromptCellphone != null  && valuePromptCellphone != ""){
			localStorage.setItem('cellphone', valuePromptCellphone);
		}
		startNewContext();
	}
	
}

function promptToNextForm(nextFormId){

	var contextJson;
	var valorPrompt = document.getElementById('promptTxt').value;
	if(valorPrompt===''){
			alert('Favor preencher o campo.');
	}else{
		//inserir o valor do input no contexto
		try{
			contextJson = JSON.parse(localStorage.getItem('context'));
			contextJson._PromptCollect = valorPrompt;
			localStorage.setItem('context',JSON.stringify(contextJson));
			//console.log(localStorage.getItem('context'));
			getNextForm(nextFormId);
		}catch(err){}
		
	}
}

function transferCall(typeTransfer){
	
	var contextJson;
	try {
		if(document.getElementById('numberTxt').value != null && document.getElementById('numberTxt').value != ""){
			contextJson = JSON.parse(localStorage.getItem('context'));
			contextJson._TransferVDN = localStorage.getItem('vdn');
			localStorage.setItem('instance', document.getElementById('numberTxt').value);
			if(typeTransfer === 1){
				contextJson._TransferDestino = document.getElementById('numberTxt').value;
			}else if(typeTransfer === 2){
				contextJson._TransferDestino = contextJson.call.ani;
			}else{
				contextJson._TransferDestino = 'nc';
			}
			
			localStorage.setItem('context',JSON.stringify(contextJson));
			//console.log(localStorage.getItem('context'));
			getNextForm('-2');
		}else{
			alert("Favor preencher seu Telefone.");
		}
	} catch (e) {
		contextJson._TransferDestino = 'nc';
	}
	
}

function startChat(){
	location.href="http://intranet"
}

function createButtonFinish(top){
	
	var nodeTop = document.getElementById(top);
	
	nodeTop.appendChild(document.createElement('br'));
	nodeTop.appendChild(document.createElement('br'));
	//criar botao para finalizar atendimento
	var buttonAnnounceNext = document.createElement('BUTTON');
	var buttonDivAnnounce = document.createElement('div');
	var buttonDivAnnounceRow = document.createElement('div');
	buttonAnnounceNext.className = 'btn btn-danger btnEncerrar btn-lg sizeButton';
	buttonAnnounceNext.appendChild(document.createTextNode('Encerrar atendimento'));
	buttonAnnounceNext.setAttribute('id', 'btnFinish');
	buttonAnnounceNext.setAttribute('onclick', 'finishFlow()');
	buttonAnnounceNext.setAttribute('href', '#page-top');
	buttonDivAnnounce.className = 'col-lg-12 text-center';
	buttonDivAnnounce.appendChild(buttonAnnounceNext);
	buttonDivAnnounceRow.className = 'row';
	buttonDivAnnounceRow.appendChild(buttonDivAnnounce);
	nodeTop.appendChild(buttonDivAnnounceRow);
	
}

function removeElement(top){

	var nodeTop = document.getElementById(top);
	while (nodeTop.hasChildNodes()) {
		nodeTop.removeChild(nodeTop.lastChild);
	}
}

function createMenu(top, obj){
	var nodeTop = document.getElementById(top);
	var arrayMenu = obj.menu.listaChoiceDto;
	
	var index;
	//Adicionar o t�tulo referente ao menu
	//console.log(obj.name);
	//console.log(top);
	
	var textTitleMenu = obj.name;
	
	var lastIdAnnounce = localStorage.getItem('lastIdAnnounce');
	//validar qual ser� o componente a ser executado.
	for (var i in arrayMenu){
		
		//Verifica se o announce chama ele mesmo para n�o montar o bot�o.
		//Verifica se o nextform � disconnect para n�o montar o bot�o.
		if(lastIdAnnounce == arrayMenu[i].nextForm || arrayMenu[i].nextForm == '999'){
		
			continue;
		}
		var txtMenu = arrayMenu[i].name;
		var btnChild = document.createElement("BUTTON");
		var txtBtnChild = document.createTextNode(txtMenu);
		btnChild.appendChild(txtBtnChild);
		btnChild.className = "btn btn-success btnNormal btn-lg sizeButton";
		btnChild.setAttribute('onclick', 'getNextForm('+arrayMenu[i].nextForm+')');
		var divChild = document.createElement("div");
		divChild.appendChild(btnChild);
		divChild.className = "form-group col-xs-12 text-center";
		var divRow = document.createElement("div");
		divRow.appendChild(divChild);
		divRow.className = "row";
		nodeTop.appendChild(divRow);
	}
	createButtonFinish(top);
}

function createAnnounce(top, obj){
	var nodeTop = document.getElementById(top);
	var textAnnounce = '';
	var arrayAnnounce = obj.announce.prompt.listaPromptAudio;
	for(var i in arrayAnnounce){
		
		//Verifica se � variavel, e seta o value no announce
		if(arrayAnnounce[i].audio.type === 'VAR'){
			if(textAnnounce.indexOf(arrayAnnounce[i].audio.value) == -1){
				textAnnounce = textAnnounce + arrayAnnounce[i].audio.value;	
			}
		//Se na descri��o do announce conter Remove_ entao n�o mostra o announce
		}else if(arrayAnnounce[i].audio.description.indexOf("Remove_") == -1){
			textAnnounce = textAnnounce + ' ' + arrayAnnounce[i].audio.description;
		}else{
			textAnnounce = textAnnounce;
		}
	}
		
	//criar o anuncio
	var paragraphAnnounce = document.createElement("p");
	var divAnnounce = document.createElement("div");
	var divAnnounceRow = document.createElement("div");
	divAnnounce.className = "col-lg-12 text-center";
	divAnnounceRow.className = "row";
	paragraphAnnounce.appendChild(document.createTextNode(textAnnounce));
	divAnnounce.appendChild(paragraphAnnounce);
	divAnnounceRow.appendChild(divAnnounce);
	nodeTop.appendChild(divAnnounceRow);
	
	//Verifica se � o ultimo announce e vai desconectar para montar o botao Encerrar Liga��o
	if(obj.announce.nextForm != null && obj.announce.nextForm != '999'){
		getNextFormAnnonce(obj.announce.nextForm);
	}else{
		createButtonFinish(top);
	}
	
}

function nextFormEnterKey(event, nextform){
	
	 if( event.which == 13 || event.keyCode == 13 ){
		 promptToNextForm(nextform);
	 }	 
}



function createPrompt(top, obj){
	
	var nodeTop = document.getElementById(top);
	var textAnnounce = '';
	var arrayAnnounce = obj.promptCollect.prompt.listaPromptAudio;
	for(var i in arrayAnnounce){
		
		if(arrayAnnounce[i].audio.type === 'VAR'){
			textAnnounce = textAnnounce + arrayAnnounce[i].audio.value;  
		}else{
			textAnnounce = textAnnounce + ' ' + arrayAnnounce[i].audio.description;
		}
	}
	
	//criar descri��o do prompt
	var paragraphAnnounce = document.createElement("p");
	var divAnnounce = document.createElement("div");
	var divAnnounceRow = document.createElement("div");
	divAnnounce.className = "col-lg-12 text-center";
	divAnnounceRow.className = "row";
	paragraphAnnounce.appendChild(document.createTextNode(textAnnounce));
	divAnnounce.appendChild(paragraphAnnounce);
	divAnnounceRow.appendChild(divAnnounce);
	nodeTop.appendChild(divAnnounceRow);
	
	//criar textbox para o prompt
	var inputPrompt = document.createElement('input');
	inputPrompt.setAttribute('type', 'text');
	inputPrompt.setAttribute('class', 'form-control');
	inputPrompt.setAttribute('placeholder', 'Digite os Dados');
	inputPrompt.setAttribute('id', 'promptTxt');
	inputPrompt.setAttribute('onkeypress', 'nextFormEnterKey(event, '+obj.promptCollect.nextForm+')');
	if(textAnnounce.indexOf('login de rede') != -1){
		inputPrompt.setAttribute('maxlength', '9');
	}
	if(textAnnounce.indexOf('RG') != -1){
		inputPrompt.setAttribute('maxlength', '9');
	}
	if(textAnnounce.indexOf('nascimento') != -1){
		inputPrompt.setAttribute('maxlength', '6');
	}
	var divInputPrompt = document.createElement('div');
	divInputPrompt.className = 'form-group col-xs-12 floating-label-form-group controls brdInput';
	divInputPrompt.appendChild(inputPrompt);
	var divControlGroup = document.createElement('div');
	divControlGroup.className = 'row control-group';
	divControlGroup.appendChild(divInputPrompt);
	formPrompt = document.createElement('form');
	formPrompt.setAttribute('name','sentMessage');
	formPrompt.setAttribute('id','promptForm');
	formPrompt.appendChild(divControlGroup);
	formPrompt.appendChild(document.createElement('br'));
	
	//criar botao de enviar
	console.log(obj.promptCollect.nextForm);
	var buttonSendPrompt = document.createElement("button");
	buttonSendPrompt.setAttribute('onclick', 'promptToNextForm('+obj.promptCollect.nextForm+')');
	buttonSendPrompt.setAttribute('class', 'btn btn-success btnNormal btn-lg sizeButton text-center');
	buttonSendPrompt.appendChild(document.createTextNode('Enviar'));
	var buttonDivPrompt = document.createElement('div');
	buttonDivPrompt.className = 'col-lg-12 text-center';
	buttonDivPrompt.appendChild(buttonSendPrompt);
	var buttonDivRow = document.createElement('div');
	buttonDivRow.className = 'row';
	buttonDivRow.appendChild(buttonDivPrompt);
	
	var divForm = document.createElement('div');
	divForm.className = 'col-lg-8 col-lg-offset-2';
	divForm.appendChild(formPrompt);
	
	var divRow = document.createElement('div');
	divRow.className = 'row';
	
	divRow.appendChild(divForm);
	
	nodeTop.appendChild(divRow);
	
	nodeTop.appendChild(buttonDivRow);
	
	createButtonFinish(top);
	document.getElementById("promptTxt").focus();

}

function createDisconnect(top, obj){
	var nodeTop = document.getElementById(top);
	var paragraphAnnounce = document.createElement("p");
	var divAnnounce = document.createElement("div");
	var divAnnounceRow = document.createElement("div");
	divAnnounce.className = "col-lg-12 text-center";
	divAnnounceRow.className = "row";
	paragraphAnnounce.appendChild(document.createTextNode('Obrigado pelo contato, até logo.'));
	divAnnounce.appendChild(paragraphAnnounce);
	divAnnounceRow.appendChild(divAnnounce);
	nodeTop.appendChild(divAnnounceRow);
	createButtonFinish(top);
	
	setTimeout(function(){finishFlow()}, 5000);
	
	
}



function createMenuTransfer(top, obj){
	
	var nodeTop = document.getElementById(top);
	var paragraphAnnounce = document.createElement("p");
//	var divAnnounce = document.createElement("div");
//	var divAnnounceRow = document.createElement("div");
//	divAnnounce.className = "col-lg-12 text-center";
//	divAnnounceRow.className = "row";
//	paragraphAnnounce.appendChild(document.createTextNode('Escolha a forma como melhor posso atende-lo.'));
//	divAnnounce.appendChild(paragraphAnnounce);
//	divAnnounceRow.appendChild(divAnnounce);
//	nodeTop.appendChild(divAnnounceRow);
	
	//Botão para ligar no telefone cadastrado.
//	nodeTop.appendChild(document.createElement('br'));
//	nodeTop.appendChild(document.createElement('br'));
	//criar botao para finalizar atendimento
	var buttonAnnounceNext = document.createElement('BUTTON');
	var buttonDivAnnounce = document.createElement('div');
	var buttonDivAnnounceRow = document.createElement('div');
	buttonAnnounceNext.className = 'btn btn-default btn-lg sizeButton';
	buttonAnnounceNext.appendChild(document.createTextNode('Ligar'));
	buttonAnnounceNext.setAttribute('onclick', 'transferCall(1)');
	buttonAnnounceNext.setAttribute('href', '#page-top');
	buttonDivAnnounce.className = 'col-lg-12 text-center';
	buttonDivAnnounce.appendChild(buttonAnnounceNext);
	buttonDivAnnounceRow.className = 'row';
	buttonDivAnnounceRow.appendChild(buttonDivAnnounce);
	buttonDivAnnounceRow.appendChild(document.createElement('br'));
	buttonDivAnnounceRow.appendChild(document.createElement('br'));
	
	
	//criar textbox para o numero do telefone
	var inputNumber = document.createElement('input');
	inputNumber.setAttribute('type', 'text');
	inputNumber.setAttribute('class', 'form-control');
	inputNumber.setAttribute('placeholder', 'Digite os Dados');
	inputNumber.setAttribute('id', 'numberTxt');
	var divInputNumber= document.createElement('div');
	divInputNumber.className = 'form-group col-xs-12 floating-label-form-group controls brdInput';
	divInputNumber.appendChild(inputNumber);
	var divControlGroup = document.createElement('div');
	divControlGroup.className = 'row control-group';
	divControlGroup.appendChild(divInputNumber);
	formNumber = document.createElement('form');
	formNumber.setAttribute('name','sentMessage');
	formNumber.setAttribute('id','numberForm');
	formNumber.appendChild(divControlGroup);
	formNumber.appendChild(document.createElement('br'));

	var paragraphAnnounce = document.createElement("p");
	var divAnnounce = document.createElement("div");
	var divAnnounceRow = document.createElement("div");
	divAnnounce.className = "col-lg-12 text-center";
	divAnnounceRow.className = "row";
	paragraphAnnounce.appendChild(document.createTextNode("Digite o seu Telefone. Ex: 4125250000"));
	divAnnounce.appendChild(paragraphAnnounce);
	divAnnounceRow.appendChild(divAnnounce);

	nodeTop.appendChild(divAnnounceRow);
	nodeTop.appendChild(formNumber);
	nodeTop.appendChild(buttonDivAnnounceRow);
	
  	//Botão para ligar no celular cadastrado
//	if(localStorage.getItem('cellphone') != null && localStorage.getItem('cellphone') != ""){
//		nodeTop.appendChild(document.createElement('br'));
//		var buttonAnnounceNext = document.createElement('BUTTON');
//		var buttonDivAnnounce = document.createElement('div');
//		var buttonDivAnnounceRow = document.createElement('div');
//		buttonAnnounceNext.className = 'btn btn-default btn-lg';
//		buttonAnnounceNext.appendChild(document.createTextNode('Ligar para o contato ' + localStorage.getItem('cellphone')));
//		buttonAnnounceNext.setAttribute('onclick', 'transferCall(2)');
//		buttonAnnounceNext.setAttribute('href', '#page-top');
//		buttonDivAnnounce.className = 'col-lg-12 text-center';
//		buttonDivAnnounce.appendChild(buttonAnnounceNext);
//		buttonDivAnnounceRow.className = 'row';
//		buttonDivAnnounceRow.appendChild(buttonDivAnnounce);
//		nodeTop.appendChild(buttonDivAnnounceRow);
//	}
	//Botão para contato via e-mail.
//	nodeTop.appendChild(document.createElement('br'));
//	var buttonAnnounceNext = document.createElement('BUTTON');
//	var buttonDivAnnounce = document.createElement('div');
//	var buttonDivAnnounceRow = document.createElement('div');
//	buttonAnnounceNext.className = 'btn btn-default btn-lg';
//	buttonAnnounceNext.appendChild(document.createTextNode('Enviar por e-mail'));
//	buttonAnnounceNext.setAttribute('onclick', 'startChat()');
//	buttonAnnounceNext.setAttribute('href', '#page-top');
//	buttonDivAnnounce.className = 'col-lg-12 text-center';
//	buttonDivAnnounce.appendChild(buttonAnnounceNext);
//	buttonDivAnnounceRow.className = 'row';
//	buttonDivAnnounceRow.appendChild(buttonDivAnnounce);
//	nodeTop.appendChild(buttonDivAnnounceRow);
  	//botão para iniciar o chat.
//	nodeTop.appendChild(document.createElement('br'));
//	var buttonAnnounceNext = document.createElement('BUTTON');
//	var buttonDivAnnounce = document.createElement('div');
//	var buttonDivAnnounceRow = document.createElement('div');
//	buttonAnnounceNext.className = 'btn btn-default btn-lg';
//	buttonAnnounceNext.appendChild(document.createTextNode('Para iniciar atendimento via Chat'));
//	buttonAnnounceNext.setAttribute('onclick', 'startChat()');
//	buttonAnnounceNext.setAttribute('href', '#page-top');
//	buttonDivAnnounce.className = 'col-lg-12 text-center';
//	buttonDivAnnounce.appendChild(buttonAnnounceNext);
//	buttonDivAnnounceRow.className = 'row';
//	buttonDivAnnounceRow.appendChild(buttonDivAnnounce);
//	nodeTop.appendChild(buttonDivAnnounceRow);
	
	
	//cancelar o atendimento.
	createButtonFinish(top);
}

function createElementManager(top){
	
	if(localStorage.getItem('context') === null){
		localStorage.setItem('context', '{"call":{"channel":"web","ani":"4100000000"}}');
	}
	
	var url;
	
	//LOCAL
	var urlJson = 'http://localhost:7001/ims-tool-operator-web/rest/interaction/get/'+localStorage.getItem('context')+'/'+localStorage.getItem('formId');

	//QA3
//	var urlJson = 'http://<TBD>/ims-tool-operator-web/rest/interaction/get/'+localStorage.getItem('context')+'/'+localStorage.getItem('formId');
	
	//PRODUÇÃO
	//var urlJson = 'http://<TBD>/ims-tool-operator-web/rest/interaction/get//'+localStorage.getItem('context')+'/'+localStorage.getItem('formId');
	
	
	url = urlJson.replace(/;/g, ' ');
	//console.log(url);

	 var interaction;
	
	$.ajax({
                    type: "GET",
                    url: url,
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (data) {
                    	//console.log(data);
						interaction = data;
						localStorage.setItem('context', JSON.stringify(interaction.context));
						

						var obj = interaction.form;
						var typeElement = 'nc';
						try {
							 typeElement = interaction.form.formtype;
						} catch (e) {
							typeElement = 'nc';
						}
						console.log(JSON.stringify(typeElement));
						
						switch(typeElement){
						case '1':
							localStorage.setItem('lastIdAnnounce', interaction.form.id);
							createAnnounce(top, obj);
							break;
						case '2':
							createPrompt(top, obj);
							console.log(JSON.stringify(obj));
							break;
						case '3':							
							createMenu(top, obj);
							break;
						case '7':
							localStorage.setItem('vdn', obj.transfer.vdn );
							createMenuTransfer(top, obj);
							break;		
						
						case '8':
							createDisconnect(top, obj);
							break;
						
						default:
							createDisconnect(top, obj);
							break;
						}
                    },
                    error: function (msg, url, line) {
    
                    }
	});
	
	
}



function getNextForm(nextFormId){
	
  	//console.log(localStorage.getItem('instance'));
	
	
//	if(localStorage.getItem('instance') === null){
//		$('#modalLogin').modal('show');
//	}else{
	localStorage.setItem('formId', nextFormId);
	removeElement('managerDiv');	
	createElementManager('managerDiv');
//	}
}

function getNextFormAnnonce(nextFormId){
	
  	//console.log(localStorage.getItem('instance'));
//	
//	if(localStorage.getItem('instance') === null){
//		$('#modalLogin').modal('show');
//	}else{
		localStorage.setItem('formId', nextFormId);
		createElementManager('managerDiv');
//	}
}





// jQuery for page scrolling feature - requires jQuery Easing plugin
$(function() {
    $('.page-scroll a').bind('click', function(event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: $($anchor.attr('href')).offset().top
        }, 1500, 'easeInOutExpo');
        event.preventDefault();
    });
});

// Floating label headings for the contact form
$(function() {
    $("body").on("input propertychange", ".floating-label-form-group", function(e) {
        $(this).toggleClass("floating-label-form-group-with-value", !! $(e.target).val());
    }).on("focus", ".floating-label-form-group", function() {
        $(this).addClass("floating-label-form-group-with-focus");
    }).on("blur", ".floating-label-form-group", function() {
        $(this).removeClass("floating-label-form-group-with-focus");
    });
});

// Highlight the top nav as scrolling occurs
$('body').scrollspy({
    target: '.navbar-fixed-top'
})

// Closes the Responsive Menu on Menu Item Click
$('.navbar-collapse ul li a').click(function() {
    $('.navbar-toggle:visible').click();
});