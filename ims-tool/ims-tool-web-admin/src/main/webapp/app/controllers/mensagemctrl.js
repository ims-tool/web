app.controller(
				'MensagemCtrl',
				function($rootScope, $location, $scope, $http, $mdDialog,
						$mdMedia) {
					
					checkAccess('webmensagem');

					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
					$rootScope.activetab = $location.path();
					$scope.messages = [];
					$scope.message = [];
					$scope.showCancelButton = true;
					$scope.showNewButton = false;
					$scope.showMessage = true;
					
					$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/message/findAll').success(function(data1) {
								$scope.messages = data1;
							});
					
					
					$scope.editMessage = function(index) {

						var data = $scope.messages[index];
						$scope.message = data;
						$scope.showMessage = false;
						$scope.showNewButton = true;
						$scope.showCancelButton = false;
						setLog(1, 'add login web admin', 'ims-tool-web-admin', data.login, 0, data.id);

					};
					
					$scope.cancelForm = function() {
					
						$scope.message = [];
						$scope.showMessage = true;
						$scope.showNewButton = false;
						$scope.showCancelButton = true;

					};
					
					$scope.submit = function() {
						
				            var file = document.getElementById('upload').files[0];
				            var reader = new FileReader();
				            var rawData = new ArrayBuffer();            
				            
				            if(file != null){
				            	console.log("Implementar um arquivo");
				            	saveMessage($scope.message);
				            }else{
				            	saveMessage($scope.message);
				            }

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