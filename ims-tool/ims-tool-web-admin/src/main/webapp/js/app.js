var app = angular.module('angularTable', []);

app.controller('listdata',function($scope, $http){
	$scope.users = []; //declare an empty array
        $scope.users = '[{\"id\":1,\"first_name\":\"Heather\",\"last_name\":\"Bell\",\"hobby\":\"Eating\"}, {\"id\":2,\"first_name\":\"Andrea\",\"last_name\":\"Dean\",\"hobby\":\"Gaming\"}]';
});