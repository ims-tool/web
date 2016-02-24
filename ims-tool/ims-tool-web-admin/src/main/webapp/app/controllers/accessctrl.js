

app.controller('AccessCtrl',function($rootScope, $location, $scope, $http) {
	
	var typeList;
	$rootScope.activetab = $location.path();
	
	$http.get('http://localhost:8080/ims-tool-server/rest/servicehour/findType')
	.success(function(data1) {
		$scope.data = {
			    repeatSelect: null,
			    availableOptions: data1,
		};
	});
	
	 $scope.submit = function() {
	        	$http.get('http://localhost:8080/ims-tool-server/rest/servicehour/find/'+$scope.data.singleSelect)
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
								url : "http://localhost:8080/ims-tool-server/rest/servicehour/update",
								contentType : "application/json",
								dataType : 'json'
							});
				}
			};   

	
	
});


