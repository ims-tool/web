app.controller('MensagemCtrl', function($rootScope, $location, $scope, $http, $mdDialog, $mdMedia) {
					
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
					
					$scope.example1model = []; 
					$scope.example1data = [ {id: 1, label: "David"}, {id: 2, label: "Jhon"}, {id: 3, label: "Danny"}];
					
					$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/message/findAll').success(function(data1) {
								$scope.messages = data1;
							});
					
					
					$scope.editMessage = function(index) {

						var data = $scope.messages[index];
						$scope.message = {};
						$scope.message = data;
						console.log($scope.message); 
						$scope.showMessage = false;
						$scope.showNewButton = true;
						$scope.showCancelButton = false;
						
					};
					$scope.showForm = function() {
						$scope.message = {};
						$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/message/nextidMessage').success(function(data1) {
							$scope.message.id = data1;
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
					
					$scope.submit = function() {
						
				            var file = document.getElementById('upload').files[0];
				            var reader = new FileReader();
				            var rawData = new ArrayBuffer();            
				            saveMessage($scope.message);
				            $scope.message = {};
							$scope.showCancelButton = true;
							$scope.showNewButton = false;
							$scope.showMessage = true;
				            alert('Mensagem gravada com sucesso!');

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
	
	console.log(message);
	
	$.ajax({
		type : "POST",
		data : JSON.stringify(message),
		url : 'http://'+window.location.hostname+":8080/ims-tool-server/rest/message/update",
		contentType : "application/json",
		dataType : 'json'
	});
	
}

function setFileName(){
	
	var nome = document.getElementById("id").value;
	
	document.getElementById("fileName").value =  nome;
	
	document.getElementById("fileName").setAttribute("hidden", "true");
}

