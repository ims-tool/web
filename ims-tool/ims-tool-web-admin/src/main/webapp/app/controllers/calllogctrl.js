app.controller('CallLogCrtl', function($rootScope, $location, $scope, $http, $mdDialog, $mdMedia, $sce, $window) {
					
					checkAccess('webreport');

					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
					$rootScope.activetab = $location.path();
					$scope.callLog = {};
					$scope.callLogs = {};
					$scope.call = {};
					$scope.call.ani = "";
					$scope.call.dnis = "";
					
					$scope.searchCallLog = function(){
						
						if ($scope.call.datai > $scope.call.dataf){
							bootbox.alert("A data final deve ser maior que data inicial.");
							return;
						}
						
						if (!$scope.call.datai){
							bootbox.alert("Data inicio deve ser preenchida");
							document.getElementById("datai").focus();
							return;
						}
						if (!$scope.call.dataf){
							bootbox.alert("Data final deve ser preenchida");
							document.getElementById("dataf").focus();
							return;
						}
						if(!$scope.call.ani){
							$scope.call.ani = "0";
						}
						if(!$scope.call.dnis){
							$scope.call.dnis = "0";
						}
						
						$http.get('http://'+getPath()+'/ims-tool-server/rest/report/getCallLogList/'+moment($scope.call.datai).format("YYYY-MM-DD HH:mm:00")+"/"+moment($scope.call.dataf).format("YYYY-MM-DD HH:mm:00")+"/"+$scope.call.ani+"/"+$scope.call.dnis).success(function(data1) {
							$scope.callLogs = data1;
							console.log(data1);
						});
					}
					$scope.showLogDetail = function(index) {
						var data = $scope.callLogs[index];
						console.log(data);
						$window.open('http://'+getPath()+'/ims-report/logview.jsp?logid='+data.id, '_blank');
						
					};
					$scope.showContext = function(index) {
						var data = $scope.callLogs[index];
						bootbox.dialog({
							  message: JSON.stringify(JSON.parse(data.context), null, 4),
							  title: "Contexto",
							  buttons: {
							    success: {
							      label: "OK",
							      className: "btn-success",
							    }
							  }
							});
						
						
					};
})					
					
function setLog(ptypeid, pdescription, partifact, poriginalvalue, partifactid, pvalueid){
	var logaudit = {userLogin: localStorage.getItem('login'), typeid: ptypeid, description : pdescription, artifact: partifact, originalvalue: poriginalvalue, valueid : pvalueid, artifactid : partifactid};
	$.ajax({
		type : "POST",
		data : JSON.stringify(logaudit),
		url : 'http://'+getPath()+'/ims-tool-server/rest/logaudit/set',
		contentType : "application/json",
		dataType : 'json'
	});
}

function saveControlPanel(controlPanel){
	
	$.ajax({
		type : "POST",
		data : JSON.stringify(controlPanel),
		url : 'http://'+getPath()+'/ims-tool-server/rest/report/updateControlPanel',
		contentType : "application/json",
		dataType : 'json'
	});
}
