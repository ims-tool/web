app.controller('ReportCtrl', function($rootScope, $location, $scope, $http, $mdDialog, $mdMedia, $sce) {
					
					checkAccess('webreport');

					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
					$rootScope.activetab = $location.path();
					$scope.report = {};
					$scope.artifacts ={};
					$scope.log = {};
					$scope.logs = {};
					$scope.callLog = {};
					$scope.callLogs = {};
					$scope.call = {};
					$scope.call.ani = "";
					$scope.call.dnis = "";
					$scope.log.artifact = "";
					 $scope.myDate = new Date();
					$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/report/getTypeControlPanel').success(function(data1) {
						$scope.data = {
							    repeatSelect: null,
							    availableOptions: data1,
						};
					});
					
					$scope.getControlPanelList = function(){
						
						$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/report/getControlPanelList/'+$scope.report.type).success(function(data1) {
							$scope.controlPanelList = data1;
						});
					}
					
					$scope.changeFlag = function(index){
						var data = $scope.controlPanelList[index];
						var statusOld = "";
						data.loginid = localStorage.getItem("login");
						bootbox.confirm("Deseja alterar o status?", function(result) {
							if(result === true){
								saveControlPanel(data);
								console.log(data);
								if(data.status === 'true'){
									statusOld = 'false'
								}else{
									statusOld = 'true';
								}
								setLog(1, "update flag controlPanel ims-tool-web-admin", "PAINEL CONTROLE", statusOld, 0, data.id);
							}else{
								$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/report/getControlPanelList/'+$scope.report.type).success(function(data1) {
									$scope.controlPanelList = data1;
								});
							}
						});
					}
					$scope.changeTimeout = function(index){
						var data = $scope.controlPanelList[index];
						data.loginid = localStorage.getItem("login");
						bootbox.confirm("Deseja alterar timeout?", function(result) {
							if(result === true){
								saveControlPanel(data);
								console.log(data);
								setLog(1, "update timeout controlPanel ims-tool-web-admin", "PAINEL CONTROLE", data.timeout.toString(), 0, data.id);
							}else{
								$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/report/getControlPanelList/'+$scope.report.type).success(function(data1) {
									$scope.controlPanelList = data1;
								});
							}
						});
					}
					$scope.getArtifactList = function(){
								$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/report/getArtifactList/'+$scope.log.data).success(function(data1) {
									$scope.artifacts = data1;
								});

					}
					$scope.searchLog = function(){
							if(!$scope.log.artifact.trim()){
								bootbox.alert("Por favor selecione os parametros para a pesquisa!");
							}else{
								$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/report/getLogList/'+$scope.log.data+"/"+$scope.log.artifact).success(function(data1) {
									$scope.logs = data1;
								});
							}
					}
					$scope.searchCallLog = function(){
						
						if(!$scope.call.ani){
							$scope.call.ani = "0";
						}
						if(!$scope.call.dnis){
							$scope.call.dnis = "0";
						}
							console.log("pesquisando");
							$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/report/getCallLogList/'+$scope.call.datai+"/"+$scope.call.dataf+"/"+$scope.call.ani+"/"+$scope.call.dnis).success(function(data1) {
								$scope.callLogs = data1;
								console.log(data1);
							});
						}
					
					
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

function saveControlPanel(controlPanel){
	
	$.ajax({
		type : "POST",
		data : JSON.stringify(controlPanel),
		url : 'http://'+window.location.hostname+":8080/ims-tool-server/rest/report/updateControlPanel",
		contentType : "application/json",
		dataType : 'json'
	});
}
