
google.load("visualization", "1", {packages : [ "corechart" ]});
google.load('visualization', '1', {packages:['table']});


google.setOnLoadCallback(drawChartWebServices);
google.setOnLoadCallback(drawChartLineRetencao);
google.setOnLoadCallback(drawChartLineVolumeLigacaoMinuto);
google.setOnLoadCallback(drawChartLineVolumeLigacaoUra);

setInterval(drawChartWebServices, 600000);
setInterval(drawChartLineRetencao, 600000);
setInterval(drawChartLineVolumeLigacaoMinuto, 600000);
setInterval(drawChartLineVolumeLigacaoUra, 600000);


//Rentencao
function drawChartLineRetencao() {
   
 	var tabela;
 	var div = document.getElementById('chart_line_Retencao');
	if(div == null){
		return;
	}
	var chart = new google.visualization.LineChart(div);
	
	
	function selectHandler() {
		
		var selectedItem = chart.getSelection()[0];
         
         if (selectedItem) {
           var dataHoraI = tabela.getValue(selectedItem.row, 0)+":00";
           var dataHoraF = tabela.getValue(selectedItem.row, 0)+":59";
           var url = "/ims-dashboard/Report?menu=report&submenu=logura&action=consultar&datahoraI="+dataHoraI+
                     "&datahoraF="+dataHoraF+"&finalizacao=R"
           window.open(url);
         }
		
   }
	
	
   google.visualization.events.addListener(chart, 'select', selectHandler); 
	
 	$.get("/ims-dashboard/Dashboard?action=buscaRetencao", function(data){

 		tabela = google.visualization.arrayToDataTable(JSON.parse(data));
 		var options = {
 			height: 260,
 	        width: 480,
 			curveType: 'function',
 			legend: { position: 'bottom' },
 			lineWidth: 4		
 		};
 		
 		chart.draw(tabela, options);
 	} );
 
}

function drawChartWebServices() {
	var tabela;
	
	var chart = new google.visualization.PieChart(document.getElementById('chart_donut_webService'));
    
	
    
    function selectHandler() {
		
		var selectedItem = chart.getSelection()[0];
         
         if (selectedItem) {
           
           var status = tabela.getValue(selectedItem.row, 0);
           
           var url = "/ims-dashboard/Report?menu=report&submenu=wsvolume&action=consultar&status="+status
           window.open(url);
         }
		
   }
	
	
   google.visualization.events.addListener(chart, 'select', selectHandler); 
    
 	$.get("/ims-dashboard/Dashboard?action=buscaWebService", function(data){
 		tabela = google.visualization.arrayToDataTable(JSON.parse(data));
	
	    var options = {
		  height: 260,
          width: 480,
	      pieHole: 0.5,
	      pieSliceText: 'value',
	      pieSliceTextStyle: {
	          color: 'white',
	          fontSize:18
	      },
	      legend: 'none',
	      chartArea: { 
	            left: 10, 
	            top: 10, 
	            width: '100%', 
	            height: '100%'
	        },
	    };
	    chart.draw(tabela, options);
	    
 	} );
}


//Volume de ligação por minuto
function drawChartLineVolumeLigacaoMinuto() {
 	var tabelaLine;
 	
 	var div = document.getElementById('chart_line_volume_ligacao_minuto');
	if(div == null){
		return;
	}
	var chartLine = new google.visualization.LineChart(div);
	
	function selectHandler() {
		
		 var selectedItem = chartLine.getSelection()[0];
         
         if (selectedItem) {
        	 
        	 var dataHoraI = tabelaLine.getValue(selectedItem.row, 0)+":00";
        	 var dataHoraF = tabelaLine.getValue(selectedItem.row, 0)+":59";
        	 var url = "/ims-dashboard/Report?menu=report&submenu=logura&action=consultar&datahoraI="+dataHoraI+"&datahoraF="+dataHoraF;
        	 
        	 window.open(url);
        	 
         }
		
   }
	
	
   google.visualization.events.addListener(chartLine, 'select', selectHandler); 
	
	$.get("/ims-dashboard/Dashboard?action=buscaVolumeLigacaoMinuto", function(data){
 		tabelaLine = google.visualization.arrayToDataTable(JSON.parse(data));
 		var optionsLine = {
 			height: 260,
 	        width: 480,
 			curveType: 'function',
 			legend: { position: 'bottom' },
 			lineWidth: 4		
 		};
 		
 		
 		chartLine.draw(tabelaLine, optionsLine);
 	} );
 
}


//Volume de ligação por minuto
function drawChartLineVolumeLigacaoUra() {
 
	var tabela;
	var div = document.getElementById('chart_bar_volume_ligacao_ura');
	if(div == null){
		return;
	}
	var chart = new google.visualization.ColumnChart(div);
	
	function selectHandler() {
		
		var selectedItem = chart.getSelection()[0];
         
         if (selectedItem) {
           
           var dnis = tabela.getValue(selectedItem.row, 0);
           
           alert(dnis);
           
           var today = new Date();
           var dd = today.getDate();
           var mm = today.getMonth()+1; //January is 0!
           var yyyy = today.getFullYear();

           if(dd<10) {
               dd='0'+dd
           } 

           if(mm<10) {
               mm='0'+mm
           } 

           today = mm+'/'+dd+'/'+yyyy;
           alert(today);
           var url = "/ims-dashboard/Report?menu=report&submenu=logura&action=consultar&datahoraI="+dataHoraI+
                     "&datahoraF="+dataHoraF+"&finalizacao=R";
           window.open(url);
         }
		
   }
	
	
   google.visualization.events.addListener(chart, 'select', selectHandler);
	
	$.get("/ims-dashboard/Dashboard?action=buscaVolumeLigacaoURA", function(data){
		tabela = google.visualization.arrayToDataTable(JSON.parse(data));
		var options = {
			height: 260,
	        width: 480,
			bar: {groupWidth: "95%"},
			legend: { position: 'bottom' }
					
		};
		chart.draw(tabela, options);
	} );

}


