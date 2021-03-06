 app.config(function($stateProvider)
		{
			
	 
			 $stateProvider
			    
		   .state('welcome', {
			   url : '/welcome',
			   templateUrl: 'app/views/home.html',
			   data: {
			        requireLogin: false
			      }
		     
		     
		   })
		 
		   // para a rota '/sobre', carregaremos o template sobre.html e o controller 'SobreCtrl'
		   .state('flag', {
			   url: "/flag",
			   templateUrl : 'app/views/flag.html',
			   data: {
			        requireLogin: false
			      }
		      
		   })
		 
		   // para a rota '/contato', carregaremos o template contato.html e o controller 'ContatoCtrl'
		   .state('parameter', {
			   url : '/parameter',
			   templateUrl: 'app/views/parameter.html',
			   data: {
			        requireLogin: true
			      }
		      
		   })
		   
		    .state('hour', {
			   url : '/hour',
			   templateUrl: 'app/views/hour.html',
			   data: {
			        requireLogin: true
			      }
		      
		   })
		   
		   .state('/login', {
			   url: 'app/views/login.html',
			   data: {
			        requireLogin: false
			      }
		   })
		   
		  
		    .state('access', {
			   url : '/access',
			   templateUrl: 'app/views/access.html',
			   data: {
			        requireLogin: true
			      }
		      
		   })
		   
		   .state('access_system', {
			   url : '/access_system',
			   templateUrl: 'app/views/access_system.html',
			   data: {
			        requireLogin: true
			      }
		      
		   })
		   
		   .state('mensagem', {
			   url : '/mensagem',
			   templateUrl: 'app/views/mensagem.html',
			   data: {
			        requireLogin: true
			      }
		      
		   })
		   .state('report', {
			   url : '/report',
			   templateUrl: 'app/views/report.html',
			   data: {
			        requireLogin: true
			      }
		      
		   })
		   
		   .state('controlpanel', {
			   url : '/controlpanel',
			   templateUrl: 'app/views/controlpanel.html',
			   data: {
			        requireLogin: true
			      }
		   })
		   
		  .state('logaudit', {
			   url : '/logaudit',
			   templateUrl: 'app/views/logaudit.html',
			   data: {
			        requireLogin: true
			      }
		   })
		   .state('calllog', {
			   url : '/calllog',
			   templateUrl: 'app/views/calllog.html',
			   data: {
			        requireLogin: true
			      }
		   })
		   ;
			 
		   		    
		   
		});


