app.controller(
				'AccessCtrl',
				function($rootScope, $location, $scope, $http, $mdDialog,
						$mdMedia) {

					var typeList;
					$scope.status = '  ';
					$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
					$rootScope.activetab = $location.path();
					$scope.users = [];
					$scope.accesses = [];
					$scope.showUser = true;
					$scope.showButtonUser = false;

					$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/access/findAllUser').success(function(data1) {
								$scope.users = data1;
							});

					$scope.submit = function() {

						// verificar se campo senha e confirmação de senha são
						// iguais.
						var pw1 = $scope.user.pw1;
						var pw2 = $scope.user.pw2;
						if ($scope.user.pw1 === $scope.user.pw2) {

							var box = confirm("Deseja realmente salvar o usuário?");
							if (box === true) {
								if ($scope.user.id != null) {
									console.log($scope.user.id);
								} else {
									$scope.user.id = -1;
								}
								var data = $scope.user;

								$
										.ajax({
											type : "POST",
											data : JSON.stringify(data),
											url : 'http://'
													+ window.location.hostname
													+ ":8080/ims-tool-server/rest/access/update",
											contentType : "application/json",
											dataType : 'json'
										})
							}
							$scope.showUser = true;
							$scope.showButtonUser = false;
							$scope.user = '';
							$http
									.get(
											'http://'
													+ window.location.hostname
													+ ':8080/ims-tool-server/rest/access/findAllUser')
									.success(function(data1) {
										$scope.users = data1;
									});
							$scope.refresh();
						} else {
							alert("Senhas não conferem, por gentileza alterar.");
						}

					};

					$scope.showForm = function() {
						$scope.showUser = false;
						$scope.showButtonUser = true;
					};

					$scope.cancelForm = function() {
						$scope.showUser = true;
						$scope.showButtonUser = false;
						$scope.user = '';

					};

					$scope.removeUser = function(index) {

						var data = $scope.users[index];

						var box = confirm("Deseja realmente apagar todos os dados do usuário?");
						if (box === true) {

							$
									.ajax({
										type : "POST",
										data : JSON.stringify(data),
										url : 'http://'
												+ window.location.hostname
												+ ":8080/ims-tool-server/rest/access/remove",
										contentType : "application/json",
										dataType : 'json'
									})

							$scope.users.splice(index, 1);
						}
					};

					$scope.editAccess = function(index, ev) {

						var data = $scope.users[index];
						sessionStorage.setItem('user', JSON.stringify(data));
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && $scope.customFullscreen;
					    $mdDialog.show({
					      controller: DialogController,
					      templateUrl: 'app/views/access_system.html?id='+data.id,
					      parent: angular.element(document.body),
					      targetEvent: ev,
					      clickOutsideToClose:true,
					      fullscreen: useFullScreen
					    });
					    
					    $scope.$watch(function() {
					      return $mdMedia('xs') || $mdMedia('sm');
					    }, function(wantsFullScreen) {
					      $scope.customFullscreen = (wantsFullScreen === true);
					    });
					};

					$scope.editUser = function(index) {

						var data = $scope.users[index];
						$scope.user = data;
						$scope.user.pw1 = data.password;
						$scope.user.pw2 = data.password;
						$scope.showUser = false;
						$scope.showButtonUser = true;

					};
					$scope.showConfirm = function(ev) {
						
					};

				})

app.controller('EditAccessCtrl', function($rootScope, $location, $scope, $http, $mdDialog,$mdMedia) {
	
	var obj = JSON.parse(sessionStorage.getItem('user'));
	$scope.user = obj;
	$scope.accesses = [];
	$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/access/findAccessByUser/'+$scope.user.id).success(function(data2) {
		$scope.accesses = data2;
	});
	
	})				
				
function DialogController($scope, $mdDialog) {
	$scope.hide = function() {
		$mdDialog.hide();
	};
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	};
}