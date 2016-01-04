

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
		//console.log("---> profiles" + artifact.web-admin-hour.userprofiles);
		
	}	
}

function releaseUser() {
    localStorage.setItem("login", null);
    window.location.href = '/ims-tool-web-admin';
}

