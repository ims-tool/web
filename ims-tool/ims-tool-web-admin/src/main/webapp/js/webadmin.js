

window.onload = function(){
	//Verificar se existe algum usuário cadastro no histórico da página.
	var login, password, system, artifact;
	
	system = 1;
	
	login = localStorage.getItem('login');
	
	if(login === "null" || login === "" || login === null){
		window.location.href = '/ims-tool-web-admin/login.html';
	}else{
		artifact = localStorage.getItem('artifact');
		var obj = JSON.parse(artifact);
		console.log(obj.webflag);
		console.log(obj.webhour);
		console.log(obj.webparameter);
		//verificar quais artefatos ficarão disponíveis.
		if(obj.webflag === null){
			document.getElementById('webflag').className = 'hidden';
		}
		if(obj.webparameter === null){
			document.getElementById('webparameter').className = 'hidden';
		}
		if(obj.webhour === null){
			document.getElementById('webhour').className = 'hidden';
		}
		
		//console.log("---> profiles" + artifact.web-admin-hour.userprofiles);
		
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