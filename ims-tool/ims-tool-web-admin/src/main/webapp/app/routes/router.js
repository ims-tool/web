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
		   });
		   		    
		   
		});


