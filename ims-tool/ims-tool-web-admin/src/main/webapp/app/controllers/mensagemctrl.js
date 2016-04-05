app.controller(
				'MensagemCtrl',
				function($rootScope, $location, $scope, $http, $mdDialog,
						$mdMedia) {
					
					checkAccess('webmensagem');

					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
					$rootScope.activetab = $location.path();
					$scope.messages = [];

					$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/message/findAll').success(function(data1) {
								$scope.messages = data1;
								console.log($scope.messages);
							});

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