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
		alert("Voc\u00ea n\u00e3o possui acesso a esta funcionalidade. Por gentileza, entrar em contato com o administrador.");
		window.location.href = '/ims-tool-web-admin/';
	}
}