function getPath(){
	return 'vmpwin1037:8080'
}
function checkAccess(access){
	
	var obj = JSON.parse(localStorage.getItem('artifact'));
	var result = false;
	
	for (var key in obj) {
	    var accesslist = obj[key];
	   if(accesslist.description === access){
		   result = true;
	    }
	}
	if(!result){
		//bootbox.alert("Voc\u00ea n\u00e3o possui acesso a esta funcionalidade. Por gentileza, entrar em contato com o administrador.");
		alert("Voc\u00ea n\u00e3o possui acesso a esta funcionalidade. Por gentileza, entrar em contato com o administrador.");
		window.location.href = '/ims-tool-web-admin/';
	}
}

function listAccess(access){
	
	var obj = JSON.parse(localStorage.getItem('artifact'));
	var result = false;
	
	for (var key in obj) {
	    var accesslist = obj[key];
	   if(accesslist.description === access){
		   result = true;
	    }
	}
	if(!result){
		//bootbox.alert("Voc\u00ea n\u00e3o possui acesso a esta funcionalidade. Por gentileza, entrar em contato com o administrador.");
		alert("Voc\u00ea n\u00e3o possui acesso a esta funcionalidade. Por gentileza, entrar em contato com o administrador.");
		window.location.href = '/ims-tool-web-admin/';
	}
}



function setLog(ptypeid, pdescription, partifact, poriginalvalue, partifactid, pvalueid){
	var logaudit = {userLogin: localStorage.getItem('login'), typeid: ptypeid, description : pdescription, artifact: partifact, originalvalue: poriginalvalue, valueid : pvalueid, artifactid : partifactid};
	$.ajax({
		type : "POST",
		data : JSON.stringify(logaudit),
		url : 'http://'+getPath()+'/ims-tool-server/rest/logaudit/set',
		contentType : "application/json",
		dataType : 'json'
	});
}
