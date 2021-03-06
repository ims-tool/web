
		    		
app.controller('HomeCtrl', function($rootScope, $location) {
	$rootScope.activetab = $location.path();
});

app.controller('HourCtrl',function($rootScope, $location, $scope, $http) {
	
	var typeList;
	$rootScope.activetab = $location.path();
	checkAccess('webhour');
	
	
	$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/servicehour/findType/'+localStorage.getItem('login'))
	.success(function(data1) {
		
		$scope.data = {
			    repeatSelect: null,
			    availableOptions: data1,
		};
	});
	
	 $scope.submit = function() {
	        	$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/servicehour/find/'+$scope.data.singleSelect)
				.success(function(data) {
					$scope.hours = data;
				});
	        
	      };
	      
	      $scope.changeHour = function(hour) {
				var data = hour;
				var box = confirm("Deseja realmente alterar o horário?");
				if (box === true) {
					$
							.ajax({
								type : "POST",
								data : JSON.stringify(data),
								url : 'http://'+window.location.hostname+":8080/ims-tool-server/rest/servicehour/update",
								contentType : "application/json",
								dataType : 'json'
							});
					setLog(1, 'Alterar horário URA', 'HORARIO URA', localStorage.getItem('oldhour'), 2 ,hour.id);
				}
			};
			
			$scope.setOldHour = function(hour) {
				localStorage.setItem("oldhour", hour.starthour +' | '+ hour.stophour);
			};

	
	
});

app.controller('FlagCtrl',function($rootScope, $location, $scope, $http) {
					$rootScope.activetab = $location.path();
					checkAccess('webflag');

					$scope.parameters = []; // declare an empty array

					$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/parameters/findAll')
							.success(function(data) {
								$scope.parameters = data;
							});

					// callback for ng-click 'editUser':
					$scope.editParameters = function(parameterid) {

						window.location.replace("/ims-tool-web-admin/admin/parameter/edit.html?id="+ parameterid);
						
					};
					// callback for ng-click 'deleteUser':
					$scope.deleteUser = function(userId) {
						// UserFactory.delete({ id: userId });
						// $scope.users = UsersFactory.query();
					};

					// callback for ng-click 'editUser':
					$scope.verifyStatus = function(parameter) {
						var data = parameter;
						bootbox.confirm("Deseja realmente alterar parametro?", function(result) {
							if(result === true){
								$.ajax({
									type : "POST",
									data : JSON.stringify(data),
									url : 'http://'+window.location.hostname+":8080/ims-tool-server/rest/parameters/update",
									contentType : "application/json",
									dataType : 'json'
								});
						setLog(1, 'update parametro', 'PARÂMETRO', parameter.value, 1 ,parameter.id);
							}
						});
					};
				});

app.controller('ParameterCtrl', function($rootScope, $location, $scope, $http) {
	$rootScope.activetab = $location.path();
	checkAccess('webparameter');
});

app.controller('LoginController', function($rootScope, $location) {
	$rootScope.activetab = $location.path();
});

app.controller('tabCtrl', function($rootScope, $location) {
	$rootScope.activetab = $location.path();
});

appLogin.controller('LoginCtrl', function($rootScope, $location, $scope, $http){
	
	var user;

	$scope.getLogin = function(user) {
		
		user.system = '1';
		
		jQuery.ajax({
		    headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
		    'type': 'POST',
		    'url': 'http://'+window.location.hostname+":8080/ims-tool-server/rest/access/login",
		    'data': JSON.stringify(user),
		    'dataType': 'json',
		    'success': function(data){
		    	if(data.result !== 'OK'){
		    		alert("Login inválido")
		    	}else{
		    
		    		if(!user.password){
		    			sessionStorage.setItem("newLogin", user.login);
		    			window.location.href = '/ims-tool-web-admin/newLogin.html';
		    		}else{
		    			localStorage.setItem("login", user.login);
		    			localStorage.setItem("artifact", JSON.stringify(data.artifact));
		    			setLog(4, 'login web admin', 'LOGIN-LOGOUT', 'nc', 0, 0)
		    			window.location.href = '../ims-tool-web-admin/#/home';
		    		}
		    		
		    	}
		    }
		    });
	};
});

appLogin.controller('NewLoginCtrl', function($rootScope, $location, $scope, $http){
	
	var user;
	
	$scope.user = {login: sessionStorage.getItem("newLogin"), id:-2};

	$scope.setNewPassword = function(user) {
		
		if ($scope.user.pw1 === $scope.user.pw2){
			var data = $scope.user;
			$.ajax({
				type : "POST",
				data : JSON.stringify(data),
				url : 'http://' + window.location.hostname + ":8080/ims-tool-server/rest/access/update",
				contentType : "application/json",
				dataType : 'json',
				'success': function(data){
					window.location.href = '/ims-tool-web-admin/login.html';
					setLog(1, 'cadastrou nova senha', 'ACESSO', $scope.user.login , 0, 0)
				}
			})
			
		}else{
			alert("Senhas não conferem, por gentileza alterar.");
		}
		
		
	};
});

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
