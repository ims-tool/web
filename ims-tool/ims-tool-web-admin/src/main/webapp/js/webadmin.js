

window.onload = function(){
	//Verificar se existe algum usuário cadastro no histórico da página.
	var login, password, system, artifact;
	
	system = 1;
	
	login = localStorage.getItem('login');
	
	if(login === "null" || login === "" || login === null){
		window.location.href = '/ims-tool-web-admin/login.html';
	}else{
		//verificar quais artefatos ficarão disponíveis.
		document.getElementById('webflag').className = 'hidden';
		document.getElementById('webparameter').className = 'hidden';
		document.getElementById('webhour').className = 'hidden';
		document.getElementById('webaccess').className = 'hidden';
		document.getElementById('webmensagem').className = 'hidden';
		document.getElementById('webreport').className = 'hidden';
		
		
		artifact = localStorage.getItem('artifact');
		var obj = JSON.parse(artifact);
		for (var key in obj) {
		    var access = obj[key];
		   if(access.description === 'webflag'){
			   document.getElementById('webflag').className = '';
		    }
		   if(access.description === 'webparameter'){
			   document.getElementById('webparameter').className = '';
		    }
		   if(access.description === 'webhour'){
			   document.getElementById('webhour').className = '';
		    }
		   if(access.description === 'webaccess'){
			   document.getElementById('webaccess').className = '';
		    }
		   if(access.description === 'webmensagem'){
			   document.getElementById('webmensagem').className = '';
		    }
		   if(access.description === 'webreport'){
			   document.getElementById('webreport').className = '';
		    }
		}
		
	}	
}

function releaseUser() {
	setLog(5, 'logout web admin', 'ims-tool-web-admin', 'nc', 0, 0)
    localStorage.setItem("login", null);
    window.location.href = '/ims-tool-web-admin';
}

function setLog(ptypeid, pdescription, partifact, poriginalvalue, partifactid, pvalueid){
	var logaudit = {userLogin: localStorage.getItem('login'), typeid: ptypeid, description : pdescription, artifact: partifact, originalvalue: poriginalvalue, valueid : pvalueid, artifactid : partifactid};
	$.ajax({
		type : "POST",
		data : JSON.stringify(logaudit),
		url : 'http://'+window.location.hostname+":8080/ims-tool-server/rest/logaudit/set",
		contentType : "application/json",
		dataType : 'json'
	});
}