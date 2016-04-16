app
		.controller(
				'AccessCtrl',
				function($rootScope, $location, $scope, $http, $mdDialog,
						$mdMedia) {

					checkAccess('webaccess');

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

								$.ajax({	
											type : "POST",
											data : JSON.stringify(data),
											url : 'http://'+ window.location.hostname+ ":8080/ims-tool-server/rest/access/update",
											contentType : "application/json",
											dataType : 'json'
										})
							}
							$scope.showUser = true;
							$scope.showButtonUser = false;
							$scope.user = '';
							setLog(3, 'add login web admin',
									'ims-tool-web-admin', user.login, 0, 0);
							$http.get('http://'+ window.location.hostname+ ':8080/ims-tool-server/rest/access/findAllUser').success(function(data1) {
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
							setLog(2, 'add login web admin',
									'ims-tool-web-admin', data.login, 0,
									data.id);
							$scope.users.splice(index, 1);
						}
					};

					$scope.editAccess = function(index, ev) {

						var data = $scope.users[index];
						sessionStorage.setItem('user', JSON.stringify(data));
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))
								&& $scope.customFullscreen;
						$mdDialog.show({
							controller : DialogController,
							templateUrl : 'app/views/access_system.html?id='
									+ data.id,
							parent : angular.element(document.body),
							targetEvent : ev,
							clickOutsideToClose : true,
							fullscreen : useFullScreen
						});

						$scope
								.$watch(
										function() {
											return $mdMedia('xs')
													|| $mdMedia('sm');
										},
										function(wantsFullScreen) {
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
						setLog(1, 'add login web admin', 'ims-tool-web-admin', data.login, 0, data.id);

					};
					$scope.showConfirm = function(ev) {

					};

				})

app
		.controller(
				'EditAccessCtrl',
				function($rootScope, $location, $scope, $http, $mdDialog,
						$mdMedia) {

					var obj = JSON.parse(sessionStorage.getItem('user'));
					$scope.user = obj;
					$scope.userid = obj.id;
					$scope.accesses = [];
					$scope.showUser = true;
					$scope.showButtonUser = false;
					$http
							.get(
									'http://'
											+ window.location.hostname
											+ ':8080/ims-tool-server/rest/access/findAccessByUser/'
											+ $scope.user.id).success(
									function(data2) {
										console.log(data2);
										$scope.accesses = data2;
									});

					$scope.showForm = function() {
						$scope.showUser = false;
						$scope.showButtonUser = true;
						$scope.systems = [];
						$scope.access = {};
						$scope.access.system = '';
						$scope.access.artifact = '';
						$scope.access.areaList = [];
						$scope.access.accessType = '';
						$scope.access.userid = 0;
						$http
								.get(
										'http://'
												+ window.location.hostname
												+ ':8080/ims-tool-server/rest/access/findSystem')
								.success(function(data1) {
									$scope.systems = data1.system;
								});
					};

					$scope.selectedSystem = function() {
						$scope.artifacts = [];
						$http
								.get(
										'http://'
												+ window.location.hostname
												+ ':8080/ims-tool-server/rest/access/findArtifact/'
												+ $scope.system)
								.success(function(artifacts) {
									$scope.artifacts = artifacts.artifact;
								});
					}

					$scope.selectedArtifact = function() {
						$scope.accessTypes = [];
						$http
								.get(
										'http://'
												+ window.location.hostname
												+ ':8080/ims-tool-server/rest/access/findAccessType')
								.success(
										function(accessTypes) {
											$scope.accessTypes = accessTypes.accessType;
										});
					}

					$scope.selectedAccessType = function() {
						$scope.areas = [];
						$http
								.get(
										'http://'
												+ window.location.hostname
												+ ':8080/ims-tool-server/rest/access/findArea')
								.success(function(areas) {
									$scope.areas = areas.area;
								});
					}

					$scope.cancelForm = function() {
						$scope.showUser = true;
						$scope.showButtonUser = false;

					};

					$scope.removeAccess = function(index) {

						var data = $scope.accesses[index];
						data.userid = $scope.userid;

						var box = confirm("Deseja realmente remover o acesso?");
						if (box === true) {

							$
									.ajax({
										type : "POST",
										data : JSON.stringify(data),
										url : 'http://'
												+ window.location.hostname
												+ ":8080/ims-tool-server/rest/access/removeAccess",
										contentType : "application/json",
										dataType : 'json'
									})

							$scope.accesses.splice(index, 1);

							setLog(
									2,
									'remove login access',
									'ims-tool-web-admin',
									data.system + '|' + data.artifact + '|'
											+ data.accessType + '|' + data.area,
									0, 0);
						}
					};

					$scope.saveAccess = function(ev) {

						if ($scope.areaList != null) {
							$scope.access.system = $scope.system;
							$scope.access.artifact = $scope.artifact;
							$scope.access.accessType = $scope.accessType;
							$scope.access.areaList = $scope.areaList;
							$scope.access.userid = $scope.userid;
							$
									.ajax({
										type : "POST",
										data : JSON.stringify($scope.access),
										url : 'http://'
												+ window.location.hostname
												+ ":8080/ims-tool-server/rest/access/addAccess",
										contentType : "application/json",
										dataType : 'json'
									})
							$scope.system = '';
							$scope.artifact = '';
							$scope.accessType = '';
							$scope.areaList = '';
							$scope.userid = '';
							$scope.showUser = true;
							$scope.showButtonUser = false;
							$http
									.get(
											'http://'
													+ window.location.hostname
													+ ':8080/ims-tool-server/rest/access/findAccessByUser/'
													+ $scope.user.id).success(
											function(data2) {
												$scope.accesses = data2;
											});
							setLog(
									3,
									'add access',
									'ims-tool-web-admin',
									$scope.access.system + '|'
											+ $scope.access.artifact + '|'
											+ $scope.access.accessType + '|'
											+ $scope.access.areaList.toString(),
									0, 0);
						} else {
							$mdDialog
									.show($mdDialog
											.alert()
											.parent(
													angular
															.element(document
																	.querySelector('#popupContainer')))
											.clickOutsideToClose(true)
											.title(
													'Impossível realizar cadastro')
											.textContent(
													'Não foi possível completar o cadastro, pois não foi selecionado a área correta')
											.ariaLabel('Alerta acesso')
											.ok('OK').targetEvent(ev));
						}
					};
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

function setLog(ptypeid, pdescription, partifact, poriginalvalue, partifactid,
		pvalueid) {
	var logaudit = {
		userLogin : localStorage.getItem('login'),
		typeid : ptypeid,
		description : pdescription,
		artifact : partifact,
		originalvalue : poriginalvalue,
		valueid : pvalueid,
		artifactid : partifactid
	};
	$.ajax({
		type : "POST",
		data : JSON.stringify(logaudit),
		url : 'http://' + window.location.hostname
				+ ":8080/ims-tool-server/rest/logaudit/set",
		contentType : "application/json",
		dataType : 'json'
	});
}