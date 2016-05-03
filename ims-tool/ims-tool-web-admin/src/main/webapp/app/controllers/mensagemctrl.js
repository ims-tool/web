app.controller('MensagemCtrl', function($rootScope, $location, $scope, $http, $mdDialog, $mdMedia, $sce) {
					
					checkAccess('webmensagem');

					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
					$rootScope.activetab = $location.path();
					$scope.messages = [];
					$scope.message = {};
					$scope.showCancelButton = true;
					$scope.showNewButton = false;
					$scope.showMessage = true;
					
					$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/message/findSpotList').success(function(data1) {
						
						$scope.data = {
							    repeatSelect: null,
							    availableOptions: data1,
						};
					});
					
					$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/message/findAll').success(function(data1) {
								
								$scope.messages = data1;
							});
					
					
					  $scope.trustSrc = function(src) {
						    return $sce.trustAsResourceUrl(src);
						  }
					
					$scope.editMessage = function(index) {

						var data = $scope.messages[index];
						$scope.message = {};
						$scope.message = data;
						console.log($scope.message); 
						$scope.showMessage = false;
						$scope.showNewButton = true;
						$scope.showCancelButton = false;
						
					};
					
					$scope.changeFlag = function(index) {

						var data = $scope.messages[index];
						bootbox.confirm("Deseja alterar?", function(result) {
							if(result === true){
								saveMessage(data);
								setLog(1, "update flag message ims-tool-web-admin", "MENSAGEM", data.flag, 0, data.id);
							}else{
								$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/message/findAll').success(function(data1) {
									
									$scope.messages = data1;
								});
							}
						}); 
						
					};

					
					$scope.showForm = function() {
						$scope.message = {};
						var id;
						$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/message/nextidMessage').success(function(data1) {
							if(data1 !== null){
								$scope.message.id = data1;
								$scope.message.name = data1;
							}else{
								$scope.message.id = 1;
								$scope.message.name = 1;
							}
						});
						$scope.showMessage = false;
						$scope.showNewButton = true;
						$scope.showCancelButton = false;
					}
					
					$scope.cancelForm = function() {
					
						$scope.message = {};
						$scope.showMessage = true;
						$scope.showNewButton = false;
						$scope.showCancelButton = true;

					};
					
					$scope.removeMessage = function(index) {
						
						var data = $scope.messages[index];
						bootbox.confirm("Deseja remover mensagem?", function(result) {
							if(result === true){
								removeMessage(data);
								$scope.messages.splice(index, 1);
								setLog(2, "remove message ims-tool-web-admin", "MENSAGEM", data.name, 0, data.id);
							}else{
							}
						});
						
						
						$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/message/findAll').success(function(data1) {
							
							$scope.messages = data1;
						});

					};
					
					
					
					$scope.submit = function() {
						
				            var file = document.getElementById('upload').files[0];
				            var reader = new FileReader();
				            var rawData = new ArrayBuffer();            
				            saveMessage($scope.message);
				            console.log($scope.message.id)
				            setLog(1, "update message ims-tool-web-admin", "MENSAGEM", $scope.message.name, 0, $scope.message.id);
				            $scope.message = {};
							$scope.showCancelButton = true;
							$scope.showNewButton = false;
							$scope.showMessage = true;
							$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/message/findAll').success(function(data1) {
								
								$scope.messages = data1;
							});
							bootbox.alert("Mensagem gravada com sucesso!");

					};


				})

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

function saveMessage(message){
	
	$.ajax({
		type : "POST",
		data : JSON.stringify(message),
		url : 'http://'+window.location.hostname+":8080/ims-tool-server/rest/message/update",
		contentType : "application/json",
		dataType : 'json'
	});
	
}

function removeMessage(message){
	
	
	$.ajax({
		type : "POST",
		data : JSON.stringify(message),
		url : 'http://'+window.location.hostname+":8080/ims-tool-server/rest/message/remove",
		contentType : "application/json",
		dataType : 'json'
	});
	
}




function setFileName(){
	
	var nome = document.getElementById("id").value;
	
	document.getElementById("fileName").value =  nome;
	
	document.getElementById("fileName").setAttribute("hidden", "true");
}


