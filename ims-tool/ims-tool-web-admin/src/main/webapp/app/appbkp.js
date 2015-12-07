var app = angular.module('app',['ngRoute', 'ngCookies']);

app.config(function($routeProvider, $locationProvider)
		{
		
		$routeProvider.when('/', {
			templateUrl : 'app/views/home.html',
			controller : 'HomeCtrl',
			controllerAs : 'vm'
		})

		.when('/login', {
			templateUrl : 'app/login/login.view.html',
			controller : 'LoginController',
			controllerAs : 'vm'
		})

		.when('/register', {
			templateUrl : 'app/register/register.view.html',
			controller : 'RegisterController',
			controllerAs : 'vm'
		})

		// para a rota '/sobre', carregaremos o template sobre.html e o
		// controller 'SobreCtrl'
		.when('/flag', {
			templateUrl : 'app/views/flag.html',
			controller : 'FlagController',
			controllerAs : 'vm'
		})

		// para a rota '/contato', carregaremos o template contato.html e o
		// controller 'ContatoCtrl'
		.when('/parameter', {
			templateUrl : 'app/views/parameter.html',
			controller : 'FlagCtrl',
			controllerAs : 'vm'
		})

		.otherwise({
			redirectTo : '/login'
		});

	run.$inject = [ '$rootScope', '$location', '$cookieStore', '$http' ];
	function run($rootScope, $location, $cookieStore, $http) {
		// keep user logged in after page refresh
		$rootScope.globals = $cookieStore.get('globals') || {};
		if ($rootScope.globals.currentUser) {
			$http.defaults.headers.common['Authorization'] = 'Basic '
					+ $rootScope.globals.currentUser.authdata; // jshint
																// ignore:line
		}

		$rootScope.$on('$locationChangeStart', function(event, next, current) {
			// redirect to login page if not logged in and trying to access a
			// restricted page
			var restrictedPage = $.inArray($location.path(), [ '/login',
					'/register' ]) === -1;
			var loggedIn = $rootScope.globals.currentUser;
			if (restrictedPage && !loggedIn) {
				$location.path('/login');
			}
		});
	}

})();