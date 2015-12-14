

window.onload = function(){
	//Verificar se existe algum usuário cadastro no histórico da página.
	var login, password, system;
	
	system = 1;
	
	login = localStorage.getItem('login');
	
	if(login === "null" || login === "" || login === null){
		window.location.href = '/ims-tool-web-admin/login.html';
	}else{
		
	}	
}

function releaseUser() {
    localStorage.setItem("login", null);
    window.location.href = '/ims-tool-web-admin';
}

