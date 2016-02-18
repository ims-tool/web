

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
    localStorage.setItem("login", null);
    window.location.href = '/ims-tool-web-admin';
}

