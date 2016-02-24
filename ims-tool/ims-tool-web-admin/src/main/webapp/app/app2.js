
"use strict";
var app = angular.module('app',['ngRoute']);
 
app.config(function($routeProvider, $locationProvider)
{
	
	
 
   $routeProvider
 
   // para a rota '/', carregaremos o template home.html e o controller 'HomeCtrl'
   .when('/', {
      templateUrl : 'app/views/home.html',
      controller     : 'HomeCtrl',
      requireLogin: false
   })
 
   // para a rota '/sobre', carregaremos o template sobre.html e o controller 'SobreCtrl'
   .when('/flag', {
      templateUrl : 'app/views/flag.html',
      controller  : 'FlagCtrl',
      requireLogin: true
   })
 
   // para a rota '/contato', carregaremos o template contato.html e o controller 'ContatoCtrl'
   .when('/parameter', {
      templateUrl : 'app/views/parameter.html',
      controller  : 'FlagCtrl',
      requireLogin: true
   })
   
   .when('/login', {
                templateUrl: 'app/views/login.html',
                controller: 'LoginCtrl',
                controllerAs: 'vm'
   })
   
   .when('/access', {
                templateUrl: 'app/views/access.html',
                controller: 'AccessCtrl',
                controllerAs: 'vm'
   })
 
   // caso não seja nenhum desses, redirecione para a rota '/'
   .otherwise ({ redirectTo: '/' });
   
   
});


