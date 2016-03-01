
app.controller(
				'AccessCtrl',
				function($rootScope, $location, $scope, $http) {

					var typeList;
					
					$rootScope.activetab = $location.path();
					$scope.users = [];
					$scope.showUser = true;
					$scope.showButtonUser = false;

					$http.get('http://localhost:8080/ims-tool-server/rest/access/findAllUser').success(function(data1) {
								$scope.users = data1;
							});

					$scope.submit = function() {
						
						//verificar se campo senha e confirmação de senha são iguais.
						var pw1 = $scope.user.pw1;
						var pw2 = $scope.user.pw2;
						console.log(pw1);
						console.log(pw2);
						if($scope.user.pw1 === $scope.user.pw2 ){
							
								var box = confirm("Deseja realmente salvar o usuário?");
								if (box === true) {
									$scope.user.id = '';
									var data = $scope.user;
									
									$
											.ajax({
												type : "POST",
												data : JSON.stringify(data),
												url : "http://localhost:8080/ims-tool-server/rest/access/update",
												contentType : "application/json",
												dataType : 'json'
											})
								}
								$scope.showButtonUser = false;
						}else{
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
					
					 $scope.removeUser = function(index){
						 
						 var data = $scope.users[index];
						 
							$.ajax({
								type : "POST",
								data : JSON.stringify(data),
								url : "http://localhost:8080/ims-tool-server/rest/access/remove",
								contentType : "application/json",
								dataType : 'json'
							})
						 
						    $scope.users.splice(index, 1);
					 }; 

				});
