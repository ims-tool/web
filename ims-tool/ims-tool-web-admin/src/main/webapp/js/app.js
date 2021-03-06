var app = angular.module('angularTable', []);



app.controller('listdata',function($scope, $http){
	
	$scope.parameters = []; //declare an empty array
	
	
	    $http.get('http://localhost:8080/ims-tool-server/rest/parameters/findAll').success(function(data) {
	    		console.log(data);
	    		$scope.parameters = data;
	    });
	
	    // callback for ng-click 'editUser':
        $scope.editParameters = function (parameterid) {
        	
        	window.location.replace("/ims-tool-web-admin/admin/parameter/edit.html?id="+parameterid);
        };
        // callback for ng-click 'deleteUser':
        $scope.deleteUser = function (userId) {
//            UserFactory.delete({ id: userId });
//            $scope.users = UsersFactory.query();
        };
        
	    // callback for ng-click 'editUser':
        $scope.verifyStatus = function (parameter) {
        		   var data = parameter;
        	 	   var box= confirm("Deseja realmente alterar a flag?");
        		    if (box===true){
        		    	    $.ajax({
        		    	        type: "POST",
        		    	        data :JSON.stringify(data),
        		    	        url: "http://localhost:8080/ims-tool-server/rest/parameters/update",
        		    	        contentType: "application/json",
        		    	        dataType: 'json'
        		    	    });
        		    	    window.location.reload();
        		    }else{   
        		    	// cancel
        		       window.location.reload();
        		    }
        };
	
});