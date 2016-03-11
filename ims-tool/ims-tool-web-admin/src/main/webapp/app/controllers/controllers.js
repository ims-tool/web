
		    		
app.controller('HomeCtrl', function($rootScope, $location) {
	$rootScope.activetab = $location.path();
});

app.controller('HourCtrl',function($rootScope, $location, $scope, $http) {
	
	var typeList;
	$rootScope.activetab = $location.path();
	
	
	$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/servicehour/findType')
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
				}
			};   

	
	
});

app.controller(
				'FlagCtrl',
				function($rootScope, $location, $scope, $http) {
					$rootScope.activetab = $location.path();

					$scope.parameters = []; // declare an empty array

					$http.get('http://'+window.location.hostname+':8080/ims-tool-server/rest/parameters/findAll')
							.success(function(data) {
								$scope.parameters = data;
							});

					// callback for ng-click 'editUser':
					$scope.editParameters = function(parameterid) {

						window.location
								.replace("/ims-tool-web-admin/admin/parameter/edit.html?id="
										+ parameterid);
					};
					// callback for ng-click 'deleteUser':
					$scope.deleteUser = function(userId) {
						// UserFactory.delete({ id: userId });
						// $scope.users = UsersFactory.query();
					};

					// callback for ng-click 'editUser':
					$scope.verifyStatus = function(parameter) {
						var data = parameter;
						var box = confirm("Deseja realmente alterar a flag?");
						if (box === true) {
							$.ajax({
										type : "POST",
										data : JSON.stringify(data),
										url : 'http://'+window.location.hostname+":8080/ims-tool-server/rest/parameters/update",
										contentType : "application/json",
										dataType : 'json'
									});
						}
					};
				});

app.controller('ParameterCtrl', function($rootScope, $location, $scope, $http) {
	$rootScope.activetab = $location.path();
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
		    		alert("Login inválido");
		    	}else{
		    		localStorage.setItem("login", user.login);
		    		localStorage.setItem("artifact", JSON.stringify(data.artifact));
		    		var logaudit = '';
		    		logaudit.userLogin = user.login;
		    		logaudit.typeid = 4;
		    		logaudit.description = 'login web admin';
		    		logaudit.artifact = 'ims-tool-web-admin';
		    		
		    		
		    		$.ajax({
						type : "POST",
						data : JSON.stringify(data),
						url : 'http://'+window.location.hostname+":8080/ims-tool-server/rest/logaudit/set",
						contentType : "application/json",
						dataType : 'json',
						'success': function(data){
							window.location.href = '../ims-tool-web-admin/';
						}
					});
		    		
		    	}
		    }
		    });
		
		
		
	};
});
